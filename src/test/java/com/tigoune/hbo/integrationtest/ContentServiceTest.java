package com.tigoune.hbo.integrationtest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.ContentId;
import com.tigoune.hbo.model.ContentSeason;
import com.tigoune.hbo.model.ContentSeasonId;
import com.tigoune.hbo.repository.PersisterException;
import com.tigoune.hbo.service.IContentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	    "classpath:spring/batch/config/job-test.xml"})
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
public class ContentServiceTest {
	
	@Autowired
	private IContentService contentService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void SavesContentObjectTest() throws PersisterException, InterruptedException {
		
		Content content = new Content();
		ContentId contentId = new ContentId();
		contentId.setContentId("123");
		content.setContentId(contentId);
		content.setContetType("Movie");
		content.setContentInSeason(1);
		content.setContentInSerie(1);
		content.setManifestUrl("http://localhost");
		content.setSeasonNumber(12);
		content.setRuntime(2);
		contentService.addContent(content);
		
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(contentId) FROM contents WHERE contentId = '123'"));
	}
	
	@Test
	public void SavesManyContentObjectsTest() throws PersisterException, InterruptedException {
		for (int i = 0; i < 2 ; i++) {
			Content content = new Content();
			ContentId contentId = new ContentId();
			contentId.setContentId(""+i);
			content.setContentId(contentId);
			content.setContetType("Movie");
			content.setContentInSeason(1);
			content.setContentInSerie(1);
			content.setManifestUrl("http://localhost");
			content.setSeasonNumber(12);
			content.setRuntime(2);
			contentService.addContent(content);
		}
		
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(contentId) FROM contents WHERE contentId = '0'"));
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(contentId) FROM contents WHERE contentId = '1'"));
	}
	
	@Test//(expected=PersisterException.class)
	public void SavesTheSameContentTest() throws PersisterException, InterruptedException {
		for (int i = 0; i < 2 ; i++) {
			Content content = new Content();
			ContentId contentId = new ContentId();
			contentId.setContentId("123");
			content.setContentId(contentId);
			content.setContetType("Movie");
			content.setContentInSeason(1);
			content.setContentInSerie(1);
			content.setManifestUrl("http://localhost");
			content.setSeasonNumber(12);
			content.setRuntime(2);
			contentService.addContent(content);
		}
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(contentId) FROM contents WHERE contentId = '123'"));
	}
	
	@Test
	public void SavesContentWithSeasonTest() throws PersisterException, InterruptedException {
		
		Content content = new Content();
		ContentId contentId = new ContentId();
		contentId.setContentId("456");
		content.setContentId(contentId);
		content.setContetType("Movie");
		content.setContentInSeason(1);
		content.setContentInSerie(1);
		content.setManifestUrl("http://localhost");
		content.setSeasonNumber(12);
		content.setRuntime(2);
		
		ContentSeason season1 = new ContentSeason();
		ContentSeasonId contentSeasonsId = new ContentSeasonId();
		contentSeasonsId.setContentId(contentId);
		contentSeasonsId.setSeasonNumber(1);
		season1.setContentSeasonsId(contentSeasonsId);
		season1.setContent(content);
		content.getContentSeasons().add(season1);
		
		contentService.addContent(content);
		
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(contentId) FROM contents WHERE contentId = '456'"));
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(contentId) FROM contentseasons WHERE contentId = '456'"));
	}
	
	@Test(expected=PersisterException.class)
	public void SavesContentWithTheSameSeasonTest() throws PersisterException, InterruptedException {
		
		Content content = new Content();
		ContentId contentId = new ContentId();
		contentId.setContentId("456");
		content.setContentId(contentId);
		content.setContetType("Movie");
		content.setContentInSeason(1);
		content.setContentInSerie(1);
		content.setManifestUrl("http://localhost");
		content.setSeasonNumber(12);
		content.setRuntime(2);
		
		ContentSeason season1 = new ContentSeason();
		ContentSeasonId contentSeasonsId = new ContentSeasonId();
		contentSeasonsId.setContentId(contentId);
		contentSeasonsId.setSeasonNumber(1);
		season1.setContentSeasonsId(contentSeasonsId);
		season1.setContent(content);
		
		ContentSeason season2 = new ContentSeason();
		ContentSeasonId contentSeasonsId2 = new ContentSeasonId();
		contentSeasonsId2.setContentId(contentId);
		contentSeasonsId2.setSeasonNumber(1);
		season2.setContentSeasonsId(contentSeasonsId2);
		season2.setContent(content);
		
		content.getContentSeasons().add(season1);
		content.getContentSeasons().add(season2);
		
		contentService.addContent(content);
	}

}