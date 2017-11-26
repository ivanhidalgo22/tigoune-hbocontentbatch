/**
 * BatchMonitoringNotifier.java
 *
 * @description: notifies an email when there is a fail. 
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.notifier;

import org.springframework.batch.core.JobExecution;

/**
 * Notifies an email when there is a fail
 * @author ihidalgo
 */
public interface BatchMonitoringNotifier {

    /**
     * Notifies an email when there is a fail.
     * @param jobExecution
     */
    void notify(JobExecution jobExecution);
}
