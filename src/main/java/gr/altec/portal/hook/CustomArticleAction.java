/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook;

import bl.BLControllerFactory;
import bl.IBLController;
import gr.altec.portal.hook.blcontrollers.DocumentUploadController;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import gr.altec.portal.hook.persistence.WpPostmeta;
import gr.altec.portal.hook.persistence.WpPosts;
import gr.altec.portal.hook.persistence.WpTermTaxonomy;
import gr.altec.portal.hook.persistence.WpTerms;
import gr.altec.portal.hook.structures.InviteContentStructure;
import gr.altec.portal.hook.structures.LibraryContentStructure;
import gr.altec.portal.hook.structures.NewsContentStructure;
import gr.altec.portal.hook.utils.Utils;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import services.properties.PropertiesManager;

/**
 *
 * @author Spiros Batzio
 */
public class CustomArticleAction extends Action {

    private PropertiesManager props = new PropertiesManager("config.properties");
    private IBLController blController;

    @Override
    public void run(HttpServletRequest hsr, HttpServletResponse hsr1) throws ActionException {
        blController = BLControllerFactory.getBLController(BLControllerFactory.BL_CONTROLLER_TYPE.DEFAULT_JPA_CONTROLLER);
        int maxResults = Integer.parseInt(props.getPropertyValue(Utils.MAX_RESULTS));
        int firstResult = Integer.parseInt(props.getPropertyValue(Utils.FIRST_RESULT));
        Collection<WpPosts> posts = (Collection<WpPosts>) blController.findEntities(WpPosts.class, maxResults, firstResult);

        for (WpPosts post : posts) {
            if (!post.getPostName().contains("revision") && !post.getPostType().equals("attachment")) {

                Collection<WpTermTaxonomy> termTaxonomies = post.getWpTermTaxonomyCollection();
                String termsString = findTermsString(termTaxonomies, "");

                if (termsString.contains("Περιοδικά")
                        || termsString.contains("Ενημέρωση")
                        || termsString.contains("Τετράδια")
                        || termsString.contains("Έρευνες / Μελέτες")
                        || termsString.contains("Έκθεση")
                        || termsString.contains("Εργασιακές Σχέσεις")
                        || termsString.contains("Μελέτες")
                        || termsString.contains("Εκπαιδευτικό Υλικό")
                        || termsString.contains("Συνδικαλιστικό")
                        || termsString.contains("Εργαζομένων")
                        || termsString.contains("Παρατηρητήριο")
                        || termsString.contains("Μελέτες")
                        || termsString.contains("Κείμενα Πολιτικής")
                        || termsString.contains("Επιστημονικές Εκθέσεις")) {
                    try {
                        addLibraryArticle(post, termsString);
                    } catch (PortalException | SystemException | URISyntaxException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (termsString.contains("Προσκλήσεις")
                        || (termsString.contains("Διά Βίου Εκπαίδευση"))) {
                    try {
                        addInviteArticle(post, termsString);
                    } catch (PortalException | SystemException | URISyntaxException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (termsString.contains("Νέα")
                        || termsString.contains("Επαγγελματική Κατάρτιση")
                        || termsString.contains("Συμβουλευτική")
                        //|| termsString.contains("ekpaidefsi-ergazomenwn")
                        || termsString.contains("Εκπαίδευση Εργαζομένων")) {
                    try {
                        addNewsArticle(post, termsString);
                    } catch (PortalException | SystemException | URISyntaxException | IOException ex) {
                        Logger.getLogger(CustomArticleAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println(">>>>>>>>>>>START>>>>>>>>>>>>");
                    System.out.println("DID NOT FIND");
                    System.out.println("Post id is: " + post.getId());
                    System.out.println("Terms Detected in post: " + termsString);
                    System.out.println(">>>>>>>>>>>END>>>>>>>>>>>>");
                }

            }
        }

    }

    public JournalArticle addInviteArticle(WpPosts post, String termsString) throws PortalException, SystemException, FileNotFoundException, MalformedURLException, URISyntaxException, IOException {
        String info = "";
        String dates = "";
        String doc_info = "";
        String inst = "";
        String docPath = "";

        String preinfo = extractUrls(post.getPostContent(), new Long(props.getPropertyValue(Utils.DOC_INVITES_FOLDER_ID)));
        String[] title = {"en_US"}, titleValue = {post.getPostTitle()};
        String[] descritpion = {"en_US"}, descriptionValue = {""};

        Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(title, titleValue);
        Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descritpion, descriptionValue);

        //category id for invites 107311
        long[] category = {};
        String[] tags = termsString.split(",");

        ServiceContext st = new ServiceContext();
        st.setUserId(new Long(props.getPropertyValue(Utils.USER_ID)));
        st.setCompanyId(new Long(props.getPropertyValue(Utils.COMPANY_ID)));
        st.setScopeGroupId(new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)));
        st.setCreateDate(post.getPostDate());

        st.setAssetCategoryIds(category);
        st.setAssetTagNames(tags);

        for (WpPostmeta metaData : post.getWpPostMetaCollection()) {
            if ("wpcf-plirofories".equals(metaData.getMetaKey())) {
                info = metaData.getMetaValue();
            } else if ("wpcf-imerominies".equals(metaData.getMetaKey())) {
                dates = metaData.getMetaValue();
            } else if ("wpcf-dikaiologitika".equals(metaData.getMetaKey())) {
                doc_info = metaData.getMetaValue();
            } else if ("wpcf-odigies".equals(metaData.getMetaKey())) {
                inst = metaData.getMetaValue();
            } else if ("wpcf-prokirixi".equals(metaData.getMetaKey())) //used only for testing 
            {
                docPath = metaData.getMetaValue();
            }

        }

        //String uploadPath = docPath.substring(40);
        DocumentUploadController uploadController = new DocumentUploadController();
        docPath = uploadController.uploadDocument(st, createImageInputStream(docPath), new Long(props.getPropertyValue(Utils.DOC_INVITES_FOLDER_ID)));

        String inviteContent = new InviteContentStructure().generateContentStructure(st, preinfo, info, dates, doc_info, inst, docPath);

        JournalArticle inviteArticle = JournalArticleLocalServiceUtil.addArticle(
                new Long(props.getPropertyValue(Utils.USER_ID)),
                new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)),
                new Long(props.getPropertyValue(Utils.INVITES_FOLDER_ID)),
                titleMap,
                descriptionMap,
                inviteContent,
                props.getPropertyValue(Utils.INVITES_STRUCTURE),
                props.getPropertyValue(Utils.INVITES_TEMPLATE),
                st);

        return inviteArticle;
    }

    public JournalArticle addLibraryArticle(WpPosts post, String termsString) throws PortalException, SystemException, FileNotFoundException, MalformedURLException, URISyntaxException, IOException {

        String perilipsi = post.getPostContent();

        String postImageId = "";
        String periexomena = "";
        String ekdotis = "";
        String epitropi = "";
        String ekdosi_manager = "";
        String gram_ekdosi = "";
        String docPath = "";

        String[] title = {"en_US"}, titleMapValues = {post.getPostTitle()};
        String[] description = {"en_US"}, descriptionMapValues = {""};

        Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(title, titleMapValues);
        Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(description, descriptionMapValues);

        String[] tags = termsString.split(",");

        long[] category = {};

        ServiceContext st = new ServiceContext();
        st.setUserId(new Long(props.getPropertyValue(Utils.USER_ID)));
        st.setCompanyId(new Long(props.getPropertyValue(Utils.COMPANY_ID)));
        st.setScopeGroupId(new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)));

        st.setCreateDate(post.getPostDate());
        st.setAssetCategoryIds(category);
        st.setAssetTagNames(tags);

        for (WpPostmeta metaData : post.getWpPostMetaCollection()) {
            if ("wpcf-periexomena".equals(metaData.getMetaKey())) {
                periexomena = metaData.getMetaValue();
            } else if ("wpcf-sintaktiki-epitropi".equals(metaData.getMetaKey())) {
                epitropi = metaData.getMetaValue();
            } else if ("wpcf-ekdotis".equals(metaData.getMetaKey())) {
                ekdotis = metaData.getMetaValue();
            } else if ("wpcf-ypeuthinos-ekdotis".equals(metaData.getMetaKey())) {
                ekdosi_manager = metaData.getMetaValue();
            } else if ("wpcf-grammateia-ekdosis".equals(metaData.getMetaKey())) {
                gram_ekdosi = metaData.getMetaValue();
            } else if ("wpcf-file".equals(metaData.getMetaKey())) {
                docPath = metaData.getMetaValue();
            } else if ("_thumbnail_id".equals(metaData.getMetaKey())) {
                postImageId = metaData.getMetaValue();
            }
        }

        Collection<WpPosts> imagePost;
        String imagepath = "";
        if (!postImageId.isEmpty()) {
            imagePost = BLControllerFactory.getBLController(BLControllerFactory.BL_CONTROLLER_TYPE.DEFAULT_JPA_CONTROLLER).findEntitiesByNamedQuery(
                    WpPosts.class,
                    "WpPosts.findById",
                    new Object[]{"id"},
                    new Object[]{Long.valueOf(postImageId)});

            for (WpPosts wpPost : imagePost) {
                imagepath = wpPost.getGuid();

            }
        }

        DocumentUploadController dc = new DocumentUploadController();
        String finalImagePath = dc.uploadDocument(st, createImageInputStream(imagepath), new Long(props.getPropertyValue(Utils.DOC_LIBRARY_FOLDER_ID)));
        String finalDocPath = dc.uploadDocument(st, createImageInputStream(docPath), new Long(props.getPropertyValue(Utils.DOC_LIBRARY_FOLDER_ID)));

        String libraryContent = new LibraryContentStructure().generateContentStructure(st, finalImagePath, perilipsi, periexomena, ekdotis, epitropi, ekdosi_manager, gram_ekdosi, finalDocPath);

        JournalArticle article = JournalArticleLocalServiceUtil.addArticle(
                new Long(props.getPropertyValue(Utils.USER_ID)),
                new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)),
                new Long(props.getPropertyValue(Utils.LIBRARY_FOLDER_ID)),
                titleMap,
                descriptionMap,
                libraryContent,
                props.getPropertyValue(Utils.LIBRARY_STRUCTURE),
                props.getPropertyValue(Utils.LIBRARY_TEMPLATE),
                st);
        return article;
    }

    public JournalArticle addNewsArticle(WpPosts post, String termsString) throws PortalException, SystemException, MalformedURLException, URISyntaxException, IOException {

        String content = post.getPostContent();
        String postImageId = "";
        String imagePath = "";

        String[] title = {"en_US"}, titleMapValues = {post.getPostTitle()};
        String[] description = {"en_US"}, descriptionMapValues = {""};

        Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(title, titleMapValues);
        Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(description, descriptionMapValues);

        String[] tags = termsString.split(",");

        ServiceContext st = new ServiceContext();
        st.setUserId(new Long(props.getPropertyValue(Utils.USER_ID)));
        st.setCompanyId(new Long(props.getPropertyValue(Utils.COMPANY_ID)));
        st.setScopeGroupId(new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)));
        st.setCreateDate(post.getPostDate());
        st.setAssetTagNames(tags);

        content = extractUrls(content, new Long(props.getPropertyValue(Utils.DOC_NEWS_FOLDER_ID)));

        for (WpPostmeta postMeta : post.getWpPostMetaCollection()) {
            if ("_thumbnail_id".equals(postMeta.getMetaKey())) {
                postImageId = postMeta.getMetaValue();
            }
        }
        DocumentUploadController dc = new DocumentUploadController();
        Collection<WpPosts> imagePost;

        if (!postImageId.isEmpty()) {
            imagePost = BLControllerFactory.getBLController(BLControllerFactory.BL_CONTROLLER_TYPE.DEFAULT_JPA_CONTROLLER).findEntitiesByNamedQuery(
                    WpPosts.class,
                    "WpPosts.findById",
                    new Object[]{"id"},
                    new Object[]{Long.valueOf(postImageId)});

            for (WpPosts wpPosts : imagePost) {
                imagePath = dc.uploadDocument(st, createImageInputStream(wpPosts.getGuid()), new Long(props.getPropertyValue(Utils.DOC_NEWS_FOLDER_ID)));
            }
        }

        String newsContent = new NewsContentStructure().generateContentStructure(content, imagePath);

        JournalArticle article = JournalArticleLocalServiceUtil.addArticle(
                new Long(props.getPropertyValue(Utils.USER_ID)),
                new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)),
                new Long(props.getPropertyValue(Utils.NEWS_FOLDER_ID)),
                titleMap,
                descriptionMap,
                newsContent,
                props.getPropertyValue(Utils.NEWS_STRUCTURE),
                props.getPropertyValue(Utils.NEWS_TEMPLATE),
                st);

        return article;
    }

    private String findTermsString(Collection<WpTermTaxonomy> termTaxonomies, String termsString) {
        StringBuilder termsBuilder = new StringBuilder(termsString);
        StringBuilder termsNameBuilder = new StringBuilder(termsString);
        for (WpTermTaxonomy termTaxonomy : termTaxonomies) {
            if (termsBuilder.length() != 0) {
                termsBuilder.append(",");
            }
            WpTerms term = termTaxonomy.getTermId();
            termsBuilder.append(term.getName());
            long parentTermId = termTaxonomy.getParent();
            if (parentTermId != 0) {
                WpTerms parentTerm = (WpTerms) blController.findEntity(WpTerms.class, parentTermId);
                findTermsString(parentTerm.getWpTermTaxonomyCollection(), termsBuilder.toString());
            }
        }

        return termsBuilder.toString();
    }

    private String extractUrls(String postContent, Long folderId) throws PortalException, SystemException, URISyntaxException, IOException {

        String urlRegex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(postContent);

        DocumentUploadController dc = new DocumentUploadController();
        while (urlMatcher.find()) {
            String wpUrl = urlMatcher.group();
            if (wpUrl.contains("wp-content")) {
                String dcUrl = dc.upladDocument(createImageInputStream(wpUrl), folderId);

                postContent = postContent.replaceAll(wpUrl, dcUrl);
            }

        }

        return postContent;

    }

    private Map<String, Object> createImageInputStream(String imageUrl) throws IOException {

        Map<String, Object> fileProps = new HashMap();

        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", "ioli.altec");
        systemProperties.setProperty("http.proxyPort", "8080");

        //byte[] bytes = (byte[]) services.http.HttpManager.sendHTTPRequest(HttpManager.HTTP_REQUEST_METHOD.GET, imageUrl, null, null, true, null, null, true);
        InputStream content = null;
        BufferedInputStream bufferedInputStream = null;
        List byteArrayList = null;
        byte[] byteArray = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            content = connection.getInputStream();

            bufferedInputStream = new BufferedInputStream(content);
            byteArrayList = new ArrayList();
            int nextByte = bufferedInputStream.read();
            while (nextByte != -1) {
                byteArrayList.add(nextByte);
                nextByte = bufferedInputStream.read();
            }

            byteArray = new byte[byteArrayList.size()];
            for (int i = 0; i < byteArrayList.size(); i++) {
                byteArray[i] = ((Integer) byteArrayList.get(i)).byteValue();
            }
            fileProps.put("contentType", connection.getContentType());
            fileProps.put("contentLength", connection.getContentLengthLong());
            fileProps.put("fileName", connection.getURL().toString().split("/")[connection.getURL().toString().split("/").length - 1]);
            fileProps.put("inputStream", content);
            fileProps.put("inputStreamBytes", byteArray);

        } finally {
//            if(content!=null){
//                content.close();
//            }
//            if(bufferedInputStream!=null){
//                bufferedInputStream.close();
//            }
        }

//        byte[] bytes = (byte[]) services.http.HttpManager.sendHTTPRequest(HttpManager.HTTP_REQUEST_METHOD.GET, imageUrl, null, null, true, null, null, true);
        return fileProps;
    }

}
