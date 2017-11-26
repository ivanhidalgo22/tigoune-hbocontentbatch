package com.tigoune.hbo.integrationtest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tigoune.hbo.helper.FileHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	    "classpath:spring/batch/config/job-test.xml"})
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
public class ContentStepTest {
    
	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	private static FileHelper fileHelper;
	private static Resource resource;
	
	@BeforeClass
    public static void setUpClass() throws IOException {
    }
	
	@Before
    public void setUp() throws IOException {
		jdbcTemplate.update("INSERT INTO Genres (genreId, genreName, genreLanguage) VALUES (9, 'drama','SPA')");
		jdbcTemplate.update("INSERT INTO Genres (genreId, genreName, genreLanguage) VALUES (50, 'drama','POR')");
		jdbcTemplate.update("INSERT INTO Genres (genreId, genreName, genreLanguage) VALUES (53, 'drama','ENG')");
		
	}
	
	@After
    public void tearDown() throws IOException {
		jdbcTemplate.update("delete from genresofcontents where genreId = 9");
		jdbcTemplate.update("delete from genresofcontents where genreId = 50");
		jdbcTemplate.update("delete from genresofcontents where genreId = 53");
		jdbcTemplate.update("delete from genres where genreId = 9");
		jdbcTemplate.update("delete from genres where genreId = 50");
		jdbcTemplate.update("delete from genres where genreId = 53");
    }
	
	@Test
	@DirtiesContext
	public void MapSerieOktest() throws Exception {
		
		String dateParam = new Date().toString();
		JobParameters param = new JobParametersBuilder().
                addString("date", dateParam).toJobParameters();
		
		JobExecution jobExecution = jobLauncher.run(job, param);
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
	}
	
	
}
