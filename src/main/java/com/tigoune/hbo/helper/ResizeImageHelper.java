/**
 * ResizeImageHelper.java
 *
 * @description: resizes an image.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * resizes an image.
 * @author IVAN HIDALGO.
 */
public class ResizeImageHelper {
    
    /**
     * Logger instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(ResizeImageHelper.class);
    
    /**
     * resizes an image.
     * @param uri
     * @param imgWidth
     * @param imgHeight
     * @return Image URI resized.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static BufferedImage resize(String uri, int imgWidth, int imgHeight) throws MalformedURLException, IOException{
        try {
            URL url = new URL(uri);
            BufferedImage originalImage  = ImageIO.read(url);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            return resize(originalImage, type, imgWidth, imgHeight);
        } catch (IOException e) {
            logger.error("ResizedImageHelper Error" + e.getMessage());
            throw e;
        }
    }
    
    /**
     * resizes an image.
     * @param originalImage
     * @param type
     * @param imgWidth
     * @param imgHeight
     * @return Image URI resized.
     */
    public static BufferedImage resize(BufferedImage originalImage, int type, int imgWidth, int imgHeight){
        try {
            BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
            g.dispose();
            return resizedImage;
        }
        catch (Exception e) {
            logger.error("ResizedImageHelper Error" + e.getMessage());
            throw e;
        }
    }
}
