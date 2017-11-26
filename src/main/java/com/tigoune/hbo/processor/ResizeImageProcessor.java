/**
 * ResizeImageProcessor.java
 *
 * @description: resizes an image and saves it into AWS storage (S3).
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.processor;

import com.tigoune.hbo.helper.ResizeImageHelper;
import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.Link;
import com.tigoune.hbo.storage.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Resizes an image and saves it into AWS storage (S3).
 * @author IVAN HIDALGO.
 */
public class ResizeImageProcessor implements ItemProcessor<Content, Content> {
    
    /**
     * cloud front URI.
     */
    private String cloudFrontUri;
    
    /**
     * Suffix attribute.
     */
    private static final String SUFFIX = "/";
    
    /**
     * Default extension to save images.
     */
    private final String EXT = "jpg";
    
    /**
     * Storage attribute.
     */
    private Storage storage;
    
    protected static String HERO_L = "520x293";
    protected static String HERO_M = "300x169";
    protected static String HERO_S = "260x146";
    protected static String HERO_XL = "1920x1080";
    protected static String POSTER_L = "640x960";
    protected static String POSTER_M = "200x300";
    protected static String POSTER_S = "110x165";
    
    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(ResizeImageProcessor.class);   
    
    /**
     * Resizes an image and saves it into AWS storage.
     * @param content
     * @return Content updated.
     * @throws Exception
     */
    @Override
    public Content process(Content content) throws Exception {
        logger.info("The content's images will be resized.");
        
        if(content.getContentChilds() != null) {
        	ReziseImage(content);
        	for(Content contentChild : content.getContentChilds()) {
        		ReziseImage(contentChild);
        	}
        }
        else
        	ReziseImage(content);
        
        logger.info("The content's images has been resized.");
        return content;
    }

	/**
	 * Resizes an image.
	 * @param content
	 */
	private void ReziseImage(Content content) {
		String imageName;
		String folderName;
		File outputfile;
		if(content.getLinks()!=null) {
			for(Link link : content.getLinks()) {
			    try {
			    	logger.debug("Resolution URI: " + link.getLinkId().getResolution());
			    	imageName = "";
			    	folderName = content.getContentId().getContentId() + SUFFIX;
			    	
					if (link.getLinkId().getResolution().equals(POSTER_L)) {
						imageName = buildImageName(content, POSTER_L);
			    		outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,640,960));
					} else if (link.getLinkId().getResolution().equals(POSTER_M)) {
						imageName = buildImageName(content, POSTER_M);
						outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,200,300));
					} else if (link.getLinkId().getResolution().equals(POSTER_S)) {
			    		imageName = buildImageName(content, POSTER_S);
			    		outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,110,165));
					} else if (link.getLinkId().getResolution().equals(HERO_XL)) {
						imageName = buildImageName(content, HERO_XL);
			    		outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,1920,1080));
					} else if (link.getLinkId().getResolution().equals(HERO_S)) {
						imageName = buildImageName(content, HERO_S);
			    		outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,260,146));
					} else if (link.getLinkId().getResolution().equals(HERO_M)) {
						imageName = buildImageName(content, HERO_M);
			    		outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,300,169));
					} else if (link.getLinkId().getResolution().equals(HERO_L)) {
						imageName = buildImageName(content, HERO_L);
			    		outputfile = new File(imageName);
			    		link.setLinkUrlS3(updateImage(link.getLinkUrlOriginal(),folderName+imageName,outputfile,520,293));
					}
			    	if(!imageName.isEmpty())
			    		link.setLinkUrlCdn(cloudFrontUri + folderName+imageName);
			    	
			    } catch(java.io.IOException ex) {
			        logger.warn("There was an error resizing the image: (IOException) " + ex.getMessage());
			    } catch(Exception ex) {
			    	logger.warn("There was an error resizing the image: (Exception) " + ex.getMessage());
			    }
			}
		}
	}
    
    /**
     * Updates image size .
     * @param uri
     * @param imageName
     * @param outputfile
     * @param imgWidth
     * @param imgHeigth
     * @return s3 image URI.
     * @throws Exception
     */
    public String updateImage(String uri, String imageName, File outputfile, int imgWidth, int imgHeigth) throws Exception {
        BufferedImage resizeImageJpg = ResizeImageHelper.resize(uri, imgWidth, imgHeigth);
        ImageIO.write(resizeImageJpg, EXT, outputfile);
        return storage.saveObject(outputfile, imageName);
    }
    
    /**
     * Makes an image name.
     * @param content
     * @param resolution
     * @return New image name.
     */
    private String buildImageName(Content content, String resolution) {
    	return  content.getContetType() + "_" + content.getContentId().getContentId() + "_" + resolution + "." + EXT;
    }

	/**
	 * @return the cloudFrontUri
	 */
	public String getCloudFrontUri() {
		return cloudFrontUri;
	}

	/**
	 * @param cloudFrontUri the cloudFrontUri to set
	 */
	public void setCloudFrontUri(String cloudFrontUri) {
		this.cloudFrontUri = cloudFrontUri;
	}
	
	/**
     * Gets the storage.
     * @return the storage.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Sets the storage.
     * @param storage the storage to set.
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}
