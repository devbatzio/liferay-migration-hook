/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.altec.portal.hook.blcontrollers;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import java.io.File;

/**
 *
 * @author bats-pc
 */
public class ArticleImageUploadController {
    
    public Image uploadArticleImage() throws SystemException, PortalException {
        ClassLoader classLoader = getClass().getClassLoader();
        File f = new File(classLoader.getResource("download.jpg").getFile());
        Image image = addImage(f);
        return image;
    }
    
    private Image addImage(File file) throws SystemException, PortalException {
        
        Image i = ImageLocalServiceUtil.createImage(CounterLocalServiceUtil.increment(Image.class.getName()));
        ImageLocalServiceUtil.updateImage(ImageLocalServiceUtil.addImage(i).getImageId(), file);
        return i;
    }
    
}
