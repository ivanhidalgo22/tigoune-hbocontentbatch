/**
 * RunScheduler.java
 *
 * @description: Scheduler for running the Job.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 20-03-2017.
 *
 **/
package com.tigoune.hbo.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

/**
 * Scheduler for running the Job.
 * @author IVAN HIDALGO.
 */
@Component
public class RunScheduler {
	
    /**
     * JobLauncher instance.
     */
    private JobLauncher jobLauncher;

    /**
     * Job instance.
     */
    private Job job;

    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(RunScheduler.class);

    /**
     * Configures and runs the job.
     */
    public void run() {

        try {
            String dateParam = new Date().toString();
            logger.info("Job started at: " + dateParam);
            JobParameters param = new JobParametersBuilder().
                            addString("date", dateParam).toJobParameters();
            if (verifyJobIsNull()) {
                    logger.info("El Job es null");
                    throw new Exception("The Job can't be null");
            }
            JobExecution execution = getJobLauncher().run(getJob(), param);
            if((execution.getStatus() == BatchStatus.FAILED) ||
            		(execution.getStatus() == BatchStatus.UNKNOWN)) {
            	logger.info("Job " + execution.getJobInstance().getJobName() +
        			    " with Id: " + execution.getId() +
        			    " and Instance Id: " + execution.getJobInstance().getId() +
        			    " has finished with errors.");
            }
            else {
            	logger.info("Job " + execution.getJobInstance().getJobName() +
        			    " with Id: " + execution.getId() +
        			    " and Instance Id: " + execution.getJobInstance().getId() +
        			    " has finished successfully.");
            }
            logger.info("Exit Status: " + execution.getStatus());
            logger.info("Job finished at: " + dateParam);

        } catch (Exception e) {
            logger.error("Job finished with Errors: " + e.getMessage());
        }
    }

    /**
     * Verifies if Job is null.
     * @return Job is null/Job is not null. 
     */
    private boolean verifyJobIsNull() {
        return getJob() == null;
    }

    /**
     * Gets Job instance.
     * @return the job.
     */
    public final Job getJob() {
        return job;
    }

    /**
     * Sets Job instance.
     * @param job the job to set.
     */
    public final void setJob(Job job) {
        this.job = job;
    }

    /**
     * Gets JobLauncher instance.
     * @return the jobLauncher.
     */
    public final JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    /**
     * Sets JobLauncher instance.
     * @param jobLauncher the jobLauncher to set.
     */
    public final void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }
}
