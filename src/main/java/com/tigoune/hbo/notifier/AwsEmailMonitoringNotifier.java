/**
 * AwsEmailMonitoringNotifier.java
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
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

/**
 * Notifies an email when the job has failed. 
 * @author IVAN HIDALGO.
 */
public class AwsEmailMonitoringNotifier implements BatchMonitoringNotifier {
	
	/**
	* Logger instance.
	*/
	private final Logger logger = LoggerFactory.getLogger(AwsEmailMonitoringNotifier.class);
	
	@Autowired
	private AmazonSimpleEmailService amazonSimpleEmailService;
	
	/**
	 * "From" address.
	 * This address must be verified with Amazon SES.
	 */
	private String from;
	
	/**
	 * "To" address.
	 * This address must be verified with Amazon SES.
	 */
	private String to;
	
	/**
	 * The subject line for the email.
	 */
	private String subject;
	  
	/**
	 * The email body for recipients with non-HTML email clients.
	 */
	private String textBody;
	  
	/**
	 * The HTML body for the email.
	 */
	private String htmlBody;
	  
	/**
	 * Separator to split the addresses.
	 */
	private String SplitSeparator = ",";

	/**
	 * Notifies an exception by e-mail.
	 */
	public void notify(JobExecution jobExecution) {
		try {
			List<String> senderList = Arrays.asList(to.split(SplitSeparator));
			textBody = createmessageContent(jobExecution);
			htmlBody = createHtmlMessageContent(jobExecution);
		    SendEmailRequest request = new SendEmailRequest()
		    		.withDestination(
		    				new Destination().withToAddresses(senderList))
		    		.withMessage(new Message()
		    				.withBody(new Body()
		    						.withHtml(new Content()
		    								.withCharset("UTF-8").withData(htmlBody))
		    						.withText(new Content()
		    								.withCharset("UTF-8").withData(textBody)))
		    				.withSubject(new Content()
		    						.withCharset("UTF-8").withData(subject)))
		    		.withSource(from);
		    amazonSimpleEmailService.sendEmail(request);
		    logger.info("Email sent!");
		    } catch (Exception ex) {
		    	logger.error("The email was not sent. Error message: " + ex.getMessage());
		    }
	}
	
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
	 * Creates an email message's content.
	 * @param jobExecution
	 * @return message's content.
	 */
	private String createHtmlMessageContent(JobExecution jobExecution) {
	    List<Throwable> exceptions = jobExecution.getAllFailureExceptions();
	    String error="";
	    for(Throwable exception: exceptions) {
	    	error += formatExceptionMessage(exception);
	    }
	    String result = MessageFormat.format(mailTemplate(), "",
	    		jobExecution.getJobInstance().getJobName(), 
	    		jobExecution.getId(), jobExecution.getJobInstance().getId(),
	    		error);

	    return result;
	}

	/**
	 * @return the from.
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * @param from the from to set.
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * @return the to.
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * @param to the to to set.
	 */
	public void setTo(String to) {
		this.to = to;
	}
	
	/**
	 * @return the subject.
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject the subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Gets mail template.
	 * @return
	 */
	public String mailTemplate() {
		return "<h1>The HBO Batch Process failed. </h1>"
				  + "<p> <b>Job Name: <p style='color:blue;'> {1} </b> </p>"
				  + "<p> <b>Job Execution #: <p style='color:blue;'> {2} </b> </p>"
				  + "<p> <b>Job Instance #: <p style='color:blue;'> {3} </b> </p>"
				  + "<p> <b>Failed with following exceptions: </b> </p>"
				  + "<p> {4} </p>";
	}

	/**
	 * @return the splitSeparator.
	 */
	public String getSplitSeparator() {
		return SplitSeparator;
	}

	/**
	 * @param splitSeparator the splitSeparator to set.
	 */
	public void setSplitSeparator(String splitSeparator) {
		SplitSeparator = splitSeparator;
	}
}
