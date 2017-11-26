package com.tigoune.hbo.integrationtest;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tigoune.hbo.mapper.InvalidContentException;
import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.metadata.Categories;
import com.tigoune.hbo.model.metadata.Country;
import com.tigoune.hbo.model.metadata.Description;
import com.tigoune.hbo.model.metadata.EpisodeNumber;
import com.tigoune.hbo.model.metadata.Feature;
import com.tigoune.hbo.model.metadata.Format;
import com.tigoune.hbo.model.metadata.Formats;
import com.tigoune.hbo.model.metadata.Genre;
import com.tigoune.hbo.model.metadata.Genres;
import com.tigoune.hbo.model.metadata.Image;
import com.tigoune.hbo.model.metadata.Images;
import com.tigoune.hbo.model.metadata.Metadata;
import com.tigoune.hbo.model.metadata.Offering;
import com.tigoune.hbo.model.metadata.Parties;
import com.tigoune.hbo.model.metadata.Party;
import com.tigoune.hbo.model.metadata.PublishCountries;
import com.tigoune.hbo.model.metadata.Season;
import com.tigoune.hbo.model.metadata.Series;
import com.tigoune.hbo.model.metadata.Video;
import com.tigoune.hbo.model.metadata.Videos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	    "classpath:spring/batch/config/job-test.xml"})
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
public class CompositeItemProcessorTest {

	@Autowired
	private ItemProcessor<Metadata, Content> processor;
	
	@Test
	public void HboMovieProcessorTest() throws Exception {
		
		Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateformat.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("1"));
    	episodeNumber.setInSeason(new BigInteger("1"));
    	Categories categories = new Categories();
    	categories.setCategory("Movies");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("ENG");
    	description.setTitle("Pelicula de prueba");
    	description.setSynopsis("Sypnosis");
    	description.setShortSynopsis("pelicula");
    	description.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x400");
    	image.setType("Movie");
    	image.setLc("SPA");
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	videos.setVideo(video);
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	offering.setPublishCountries(countries);
    	
    	Season season = new Season();
    	season.setUniqueId("500702S06");
    	season.setEpisodes(new BigInteger("13"));
    	season.setSeasonNumber(new BigInteger("07"));
    	
    	Parties parties = new Parties();
    	Party directorParty = new Party();
    	directorParty.setCharacter("Stars");
    	directorParty.setBilling(new BigInteger("2"));
    	directorParty.setRoleType("DIRECTOR");
    	directorParty.setUniqueId("51712");
    	directorParty.setCharacter("");
    	directorParty.setFirstName("Ivan");
    	directorParty.setLastName("Hidalgo");
    	parties.getParty().add(directorParty);
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("Drama");
    	genres.getGenre().add(dramaGenre);
    	
    	description.setGenres(genres);
    	
    	feature.setParties(parties);
    	
    	feature.setOffering(offering);
    	
    	feature.setVideos(videos);
    	
    	feature.setSeason(season);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);

		Content result = processor.process(metadata);
		
		assertEquals("123", result.getContentId().getContentId());
    	assertEquals(2017, result.getYear());
    	assertEquals("http://localhost", result.getManifestUrl());
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(0, result.getContentInSeason());
    	assertEquals(0, result.getContentInSerie());
    	assertEquals("Movie", result.getContetType());
	}
	
	@Test
	public void HboSerieProcessorTest() throws Exception {
		Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateformat.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("1"));
    	episodeNumber.setInSeason(new BigInteger("1"));
    	Categories categories = new Categories();
    	categories.setCategory("Series/A-Z");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("ENG");
    	description.setTitle("Pelicula de prueba");
    	description.setSynopsis("Sypnosis");
    	description.setShortSynopsis("pelicula");
    	description.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x400");
    	image.setType("Series/A-Z");
    	image.setLc("SPA");
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	videos.setVideo(video);
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	offering.setPublishCountries(countries);
    	
    	Season season = new Season();
    	season.setUniqueId("500702S06");
    	season.setEpisodes(new BigInteger("13"));
    	season.setSeasonNumber(new BigInteger("07"));
    	
    	Parties parties = new Parties();
    	Party directorParty = new Party();
    	directorParty.setCharacter("Stars");
    	directorParty.setBilling(new BigInteger("2"));
    	directorParty.setRoleType("DIRECTOR");
    	directorParty.setUniqueId("51712");
    	directorParty.setCharacter("");
    	directorParty.setFirstName("Ivan");
    	directorParty.setLastName("Hidalgo");
    	parties.getParty().add(directorParty);
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("Drama");
    	genres.getGenre().add(dramaGenre);
    	
    	
    	Series series = new Series();
    	series.setUniqueId("123");
    	series.getDescription().add(description);
    	
    	
    	description.setGenres(genres);
    	
    	feature.setSeries(series);
    	
    	feature.setParties(parties);
    	
    	feature.setOffering(offering);
    	
    	feature.setVideos(videos);
    	
    	feature.setSeason(season);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	Content content = processor.process(metadata);
    	
    	assertEquals(1, content.getDescriptions().size());
    	assertEquals(1, content.getContentChilds().size());
    	assertEquals(0, content.getOfferings().size());
    	assertEquals(0, content.getAudioTracks().size());
    	assertEquals(0, content.getGenresOfContent().size());
    	assertEquals(1, content.getContentSeasons().size());
	}
	
	@Test(expected=InvalidContentException.class)
	public void HboMovieProcessorWithoutDescrptionTest() throws Exception {
		
		Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateformat.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("1"));
    	episodeNumber.setInSeason(new BigInteger("1"));
    	Categories categories = new Categories();
    	categories.setCategory("Movies");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("ENG");
    	description.setTitle("Pelicula de prueba");
    	description.setSynopsis("Sypnosis");
    	description.setShortSynopsis("pelicula");
    	description.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x400");
    	image.setType("Movie");
    	image.setLc("SPA");
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	videos.setVideo(video);
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	offering.setPublishCountries(countries);
    	
    	Season season = new Season();
    	season.setUniqueId("500702S06");
    	season.setEpisodes(new BigInteger("13"));
    	season.setSeasonNumber(new BigInteger("07"));
    	
    	Parties parties = new Parties();
    	Party directorParty = new Party();
    	directorParty.setCharacter("Stars");
    	directorParty.setBilling(new BigInteger("2"));
    	directorParty.setRoleType("DIRECTOR");
    	directorParty.setUniqueId("51712");
    	directorParty.setCharacter("");
    	directorParty.setFirstName("Ivan");
    	directorParty.setLastName("Hidalgo");
    	parties.getParty().add(directorParty);
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("Drama");
    	genres.getGenre().add(dramaGenre);
    	
    	description.setGenres(genres);
    	
    	feature.setParties(parties);
    	
    	feature.setOffering(offering);
    	
    	feature.setVideos(videos);
    	
    	feature.setSeason(season);
    	
    	//feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);

		processor.process(metadata);
	}
	
	@Test(expected=InvalidContentException.class)
	public void HboMovieProcessorWithInvalidContentTest() throws Exception {
		
		Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateformat.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("1"));
    	episodeNumber.setInSeason(new BigInteger("1"));
    	Categories categories = new Categories();
    	categories.setCategory("Invalid");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("ENG");
    	description.setTitle("Pelicula de prueba");
    	description.setSynopsis("Sypnosis");
    	description.setShortSynopsis("pelicula");
    	description.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x400");
    	image.setType("Movie");
    	image.setLc("SPA");
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	videos.setVideo(video);
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	offering.setPublishCountries(countries);
    	
    	Season season = new Season();
    	season.setUniqueId("500702S06");
    	season.setEpisodes(new BigInteger("13"));
    	season.setSeasonNumber(new BigInteger("07"));
    	
    	Parties parties = new Parties();
    	Party directorParty = new Party();
    	directorParty.setCharacter("Stars");
    	directorParty.setBilling(new BigInteger("2"));
    	directorParty.setRoleType("DIRECTOR");
    	directorParty.setUniqueId("51712");
    	directorParty.setCharacter("");
    	directorParty.setFirstName("Ivan");
    	directorParty.setLastName("Hidalgo");
    	parties.getParty().add(directorParty);
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("Drama");
    	genres.getGenre().add(dramaGenre);
    	
    	description.setGenres(genres);
    	
    	feature.setParties(parties);
    	
    	feature.setOffering(offering);
    	
    	feature.setVideos(videos);
    	
    	feature.setSeason(season);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);

		processor.process(metadata);
	}
	
}
