/**
 * EmailMonitoringNotifier.java
 *
 * @description: notifies an email when the job has failed.  
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/
package com.tigoune.hbo.notifier;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


/**
 * Notifies an email when the job has failed. 
 * @author IVAN HIDALGO.
 */
public class EmailMonitoringNotifier implements BatchMonitoringNotifier {
    
	/**
	 * Spring Mail sender instance.
	 */
	private MailSender mailSender;
    
	/**
	 * Spring template message instance.
	 */
	private SimpleMailMessage templateMessage;
    
    /**
    * Traces different levels of logging.
    */
    private final Logger logger = LoggerFactory.getLogger(EmailMonitoringNotifier.class);
    
    /**
	 * Formats the exception description.
	 * @param exception
	 * @return Exception.
	 */
    private String formatExceptionMessage(Throwable exception) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(baos));
        return baos.toString();
    } 

    /**
     * Creates an email message's content.
     * @param jobExecution
     * @return message's content.
     */
    private String createmessageContent(JobExecution jobExecution) {
        List<Throwable> exceptions = jobExecution.getAllFailureExceptions();
        StringBuilder content = new StringBuilder();
        content.append("Job Name #");
        content.append(jobExecution.getJobInstance().getJobName());
        content.append("");
        content.append("Job execution #");
        content.append(jobExecution.getId());
        content.append("");
        content.append(" of job instance #");
        content.append(jobExecution.getJobInstance().getId());
        content.append("");
        content.append(" failed with following exceptions:");
        exceptions.forEach((exception) -> {
            content.append("");
            content.append(formatExceptionMessage(exception));
        });
        return content.toString();
    }
    
    /**
     * Notifies a message.
     * @param jobExecution 
     */
    @Override
    public void notify(JobExecution jobExecution) {
        SimpleMailMessage msg = new SimpleMailMessage(
                this.getTemplateMessage());        
        String content = createmessageContent(jobExecution);
        msg.setText(content);
        try {
            getMailSender().send(msg);
        } catch(MailException ex) {
            logger.error("MailException: " + ex.getMessage());
        }
    }

    /**
     * @return the mailSender.
     */
    public MailSender getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender the mailSender to set.
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @return the templateMessage.
     */
    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    /**
     * @param templateMessage the templateMessage to set.
     */
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
}
