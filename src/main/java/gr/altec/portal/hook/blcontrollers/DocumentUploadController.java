/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.blcontrollers;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import gr.altec.portal.hook.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;
import services.properties.PropertiesManager;

/**
 *
 * @author bats-pc
 */
public class DocumentUploadController {

    private PropertiesManager props = new PropertiesManager("config.properties");

    public String upladDocument(Map<String, Object> fileProps, Long folderId) throws PortalException, SystemException, MalformedURLException, URISyntaxException, IOException {
        ServiceContext st = new ServiceContext();
        st.setUserId(new Long(props.getPropertyValue(Utils.USER_ID)));
        st.setCompanyId(new Long(props.getPropertyValue(Utils.COMPANY_ID)));
        st.setScopeGroupId(new Long(props.getPropertyValue(Utils.SCOPE_GROUP_ID)));
        return uploadDocument(st, fileProps, folderId);
    }

    public String uploadDocument(ServiceContext st, Map<String, Object> fileProps, Long folderId) throws PortalException, SystemException, FileNotFoundException, MalformedURLException, URISyntaxException, IOException {

        InputStream is = new ByteArrayInputStream((byte[]) fileProps.get("inputStreamBytes"));
        try {
            //ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
            //repositoryID for guest is 20182
            //folderId 0 in order to upload into root folder

            String description = "";

            //upload file to document repository
            DLAppServiceUtil.addFileEntry(
                    new Long(props.getPropertyValue(Utils.REPO_ID)),
                    folderId,
                    fileProps.get("fileName").toString(),
                    fileProps.get("contentType").toString(),
                    fileProps.get("fileName").toString(),
                    description,
                    null,
                    is,
                    (long) fileProps.get("contentLength"),
                    st);

            //get uploaded file in order to provide the required info
            FileEntry entry = DLAppServiceUtil.getFileEntry(new Long(props.getPropertyValue(Utils.REPO_ID)), folderId, fileProps.get("fileName").toString());

            return "/liferay/documents/" + entry.getRepositoryId() + "/" + folderId + "/" + fileProps.get("fileName").toString() + "/" + entry.getUuid();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}
