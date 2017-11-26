/**
 * MonitoringExecutionListener.java
 *
 * @description: Notifies when job fails.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.listener;

import com.tigoune.hbo.notifier.BatchMonitoringNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * Notifies when job fails.
 * @author Ivan Hidalgo.
 */
public class MonitoringExecutionListener {
    private BatchMonitoringNotifier monitoringNotifier;
    
    /**
    * Logger instance.
    */
    private final Logger logger = LoggerFactory.getLogger(BatchMonitoringNotifier.class);
    
    @BeforeJob
    public void executeBeforeJob(JobExecution se) {
    }

    @AfterJob
    public void executeAfterJob(JobExecution jobExecution) {
        if((jobExecution.getStatus() == BatchStatus.FAILED) ||
        		(jobExecution.getStatus() == BatchStatus.UNKNOWN)) {
            logger.info("Notify when job fails.");
            monitoringNotifier.notify(jobExecution);
        }
    }
    
    /**
     * @return the monitoringNotifier.
     */
    public BatchMonitoringNotifier getMonitoringNotifier() {
        return monitoringNotifier;
    }

    /**
     * @param monitoringNotifier the monitoringNotifier to set.
     */
    public void setMonitoringNotifier(BatchMonitoringNotifier monitoringNotifier) {
        this.monitoringNotifier = monitoringNotifier;
    }
    
}
