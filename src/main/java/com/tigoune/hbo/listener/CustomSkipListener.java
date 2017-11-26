/**
 * CustomSkipListener.java
 *
 * @description: Handlers for skipping exceptions.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/
package com.tigoune.hbo.listener;


import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

/**
 * Handlers for skipping exceptions.
 * @author IVAN HIDALGO.
 */
public class CustomSkipListener implements SkipListener {
    
    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(CustomSkipListener.class);

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(Object s, Throwable thrwbl) {
        if (s instanceof Content) {
        	if(((Content)s).getContentChilds().isEmpty())
        		logger.warn(">> Skkyping " + ((Content)s).getContentId().getContentId() + " because writing it caused the error: " + thrwbl.getMessage());
        	else
        		logger.warn(">> Skkyping " + ((Content)s).getContentChilds().iterator().next().getContentId().getContentId() + " because writing it caused the error: " + thrwbl.getMessage());
        } else {
            logger.warn(">> Skkyping " + ((Metadata)s).getFeature().getUniqueId() + " because writing it caused the error: " + thrwbl.getMessage());
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
        if (t instanceof Content) {
        	if(((Content)t).getContentChilds().isEmpty())
        		logger.warn(">> Skkyping " + ((Content)t).getContentId().getContentId() + " because processing it caused the error: " + thrwbl.getMessage());
        	else
        		logger.warn(">> Skkyping " + ((Content)t).getContentChilds().iterator().next().getContentId().getContentId() + " because processing it caused the error: " + thrwbl.getMessage());
        } else {
            logger.warn(">> Skkyping " + ((Metadata)t).getFeature().getUniqueId() + " because writing it caused the error: " + thrwbl.getMessage());
        }
    }
    
}
