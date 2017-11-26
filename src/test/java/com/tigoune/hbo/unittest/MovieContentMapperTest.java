/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tigoune.hbo.unittest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tigoune.hbo.mapper.InvalidContentException;
import com.tigoune.hbo.mapper.MovieContentMapper;
import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.model.ContentId;
import com.tigoune.hbo.model.metadata.AudioTracks;
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
import com.tigoune.hbo.model.metadata.Localized;
import com.tigoune.hbo.model.metadata.Metadata;
import com.tigoune.hbo.model.metadata.Offering;
import com.tigoune.hbo.model.metadata.Original;
import com.tigoune.hbo.model.metadata.Parties;
import com.tigoune.hbo.model.metadata.Party;
import com.tigoune.hbo.model.metadata.PublishCountries;
import com.tigoune.hbo.model.metadata.Rating;
import com.tigoune.hbo.model.metadata.Ratings;
import com.tigoune.hbo.model.metadata.Season;
import com.tigoune.hbo.model.metadata.Subtitles;
import com.tigoune.hbo.model.metadata.Video;
import com.tigoune.hbo.model.metadata.Videos;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author ivan
 */
public class MovieContentMapperTest {
    
	MovieContentMapper movieContentMapper;
	
    public MovieContentMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    	movieContentMapper = new MovieContentMapper();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getEnglishContentFromMetadata() throws InvalidContentException {
        Content content = new Content();
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	Content result = movieContentMapper.getContentFromMetadata(metadata, content);
    	
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(2017, result.getYear());
    	assertEquals("http://localhost", result.getManifestUrl());
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(0, result.getContentInSeason());
    	assertEquals(0, result.getContentInSerie());
    	assertEquals("Movie", result.getContetType());
    }
    
    @Test
    public void getSpanishContentFromMetadata() throws InvalidContentException {
        Content content = new Content();
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("7"));
    	episodeNumber.setInSeason(new BigInteger("7"));
    	Categories categories = new Categories();
    	categories.setCategory("Películas");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("SPA");
    	
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
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	Content result = movieContentMapper.getContentFromMetadata(metadata, content);
    	
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(2017, result.getYear());
    	assertEquals("http://localhost", result.getManifestUrl());
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(0, result.getContentInSeason());
    	assertEquals(0, result.getContentInSerie());
    	assertEquals("Movie", result.getContetType());
    }
    
    @Test
    public void getPortuguesContentFromMetadata() throws InvalidContentException {
        Content content = new Content();
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("7"));
    	episodeNumber.setInSeason(new BigInteger("7"));
    	Categories categories = new Categories();
    	categories.setCategory("Filmes");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("POR");
    	
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
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	Content result = movieContentMapper.getContentFromMetadata(metadata, content);
    	
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(2017, result.getYear());
    	assertEquals("http://localhost", result.getManifestUrl());
    	assertEquals("123", result.getContentId().getContentId());
    	assertEquals(0, result.getContentInSeason());
    	assertEquals(0, result.getContentInSerie());
    	assertEquals("Movie", result.getContetType());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getContentFromMetadataWithInvalidVideoFormat() throws Exception {
        Content content = new Content();
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("7"));
    	episodeNumber.setInSeason(new BigInteger("7"));
    	Categories categories = new Categories();
    	categories.setCategory("Filmes");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("POR");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w9");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	movieContentMapper.getContentFromMetadata(metadata, content);
    }
    
    @Test(expected=InvalidContentException.class)
    public void getContentFromMetadataWithoutDescriptions() throws Exception {
        Content content = new Content();
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("7"));
    	episodeNumber.setInSeason(new BigInteger("7"));
    	Categories categories = new Categories();
    	categories.setCategory("Filmes");
    	
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
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	movieContentMapper.getContentFromMetadata(metadata, content);
    }
    
    @Test(expected=InvalidContentException.class)
    public void getContentFromMetadataWithoutVideos() throws Exception {
        Content content = new Content();
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("7"));
    	episodeNumber.setInSeason(new BigInteger("7"));
    	Categories categories = new Categories();
    	categories.setCategory("Filmes");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("POR");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w9");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	movieContentMapper.getContentFromMetadata(metadata, content);
    }
    
    @Test
    public void getDescriptionFromMetadata() throws InvalidContentException {
        Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setDescriptions(movieContentMapper.getDescriptionFromMetadata(metadata, content));
    	
    	assertEquals(1, content.getDescriptions().size());
    }
    
    @Test
    public void getDescriptionFromMetadataWithAllCountries() throws InvalidContentException {
        Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	
    	Description description2 = new Description();
    	EpisodeNumber episodeNumber2 = new EpisodeNumber();
    	episodeNumber2.setInSeries(new BigInteger("1"));
    	episodeNumber2.setInSeason(new BigInteger("1"));
    	Categories categories2 = new Categories();
    	categories2.setCategory("Movies");
    	description2.setEpisodeNumber(episodeNumber2);
    	description2.setReleaseYear(2017);
    	description2.setCategories(categories2);
    	description2.setLanguage("ENG");
    	description2.setTitle("Pelicula de prueba");
    	description2.setSynopsis("Sypnosis");
    	description2.setShortSynopsis("pelicula");
    	description2.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	feature.getDescription().add(description);
    	feature.getDescription().add(description2);
    	
    	metadata.setFeature(feature);
    	
    	content.setDescriptions(movieContentMapper.getDescriptionFromMetadata(metadata, content));
    	
    	assertEquals(2, content.getDescriptions().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getDescriptionFromMetadataWithError() throws InvalidContentException {
        Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
    	
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	metadata.setFeature(feature);
    	
    	content.setDescriptions(movieContentMapper.getDescriptionFromMetadata(metadata, content));
    }
    
    @Test
    public void getOfferingFromMetadata() throws Exception {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
    	
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	offering.setPublishCountries(countries);
    	
    	feature.setOffering(offering);
    	
    	metadata.setFeature(feature);
    	
    	content.getOfferings().add(movieContentMapper.getOfferingFromMetadata(metadata, content));
    	
    	assertEquals(1, content.getOfferings().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getOfferingFromMetadataWhenItIsNull() throws Exception {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
    	
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	offering.setPublishCountries(countries);
    	
    	metadata.setFeature(feature);
    	
    	content.getOfferings().add(movieContentMapper.getOfferingFromMetadata(metadata, content));
    }
    
    @Test(expected=InvalidContentException.class)
    public void getOfferingFromMetadataWithoutPublishCountries() throws Exception {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
    	
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse("2014-04-24 11:15:00");

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar starDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        XMLGregorianCalendar endDate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setStartDate(starDate);
    	offering.setEndDate(endDate);
    	
    	feature.setOffering(offering);
    	metadata.setFeature(feature);
    	
    	content.getOfferings().add(movieContentMapper.getOfferingFromMetadata(metadata, content));
    }
    
    @Test
    public void getLinksFromMetadataWithResolution640x960() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	Image image = new Image();
    	image.setImageResolution("640x960");
    	image.setType("Movie");
    	image.setLc("SPA");
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    	assertEquals(3, content.getLinks().size());
    }
    
    @Test
    public void getLinksFromMetadataWithResolution1920x1080() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	Image image = new Image();
    	image.setImageResolution("1920x1080");
    	image.setType("Movie");
    	image.setLc("SPA");
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    	assertEquals(4, content.getLinks().size());
    }
    
    @Test
    public void getLinksFromMetadataWithAllResolutions() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x1080");
    	image.setType("Movie");
    	image.setLc("SPA");
    	
    	Image image2 = new Image();
    	image2.setImageResolution("640x960");
    	image2.setType("Movie");
    	image2.setLc("SPA");
    	
    	images.getImage().add(image);
    	images.getImage().add(image2);
    	video.setImages(images);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    	assertEquals(7, content.getLinks().size());
    }
    
    @Test
    public void getLinksFromMetadataWithInvalidResolutions() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x1080");
    	image.setType("Adults");
    	image.setLc("SPA");
    	
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    	assertEquals(0, content.getLinks().size());
    }

    
    @Test
    public void getLinksFromMetadataWithInvalidContentType() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x400");
    	image.setType("Movie");
    	image.setLc("SPA");
    	
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    	assertEquals(0, content.getLinks().size());
    }
    
    @Test
    public void getLinksFromMetadataWhenItIsAnEpisodeContent() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x1080");
    	image.setType("Episode");
    	image.setLc("SPA");
    	
    	images.getImage().add(image);
    	video.setImages(images);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    	assertEquals(4, content.getLinks().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getLinksFromMetadataWhenItIsNull() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x1080");
    	image.setType("Episode");
    	image.setLc("SPA");
    	
    	images.getImage().add(image);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    }

    @Test(expected=InvalidContentException.class)
    public void getLinksFromMetadataWithoutVideos() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Images images = new Images();
    	
    	Image image = new Image();
    	image.setImageResolution("1920x1080");
    	image.setType("Episode");
    	image.setLc("SPA");
    	
    	images.getImage().add(image);
    	
    	Formats formats = new Formats();
    	Format format = new Format();
    	format.setValue("http://localhost");
    	format.setManifestNumber("w8");
    	format.setPackaging("SS");
    	formats.getFormat().add(format);
    	video.setFormats(formats);
    	videos.setVideo(video);
    	
    	metadata.setFeature(feature);
    	
    	content.setLinks(movieContentMapper.getLinksFromMetadata(metadata, content));
    }
    
    @Test
    public void getPartiesFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	
    	feature.setParties(parties);
    	
    	metadata.setFeature(feature);
    	
    	content.setParties(movieContentMapper.getPartiesFromMetadata(metadata, content));
    	assertEquals(1, content.getParties().size());
    }
    
    @Test
    public void getPartiesFromMetadataWhenTheCharacterIsAnActor() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Parties parties = new Parties();
    	Party actorParty = new Party();
    	actorParty.setCharacter("Jason");
    	actorParty.setBilling(new BigInteger("2"));
    	actorParty.setRoleType("ACTOR");
    	actorParty.setUniqueId("51712");
    	actorParty.setCharacter("");
    	actorParty.setFirstName("Ivan");
    	actorParty.setLastName("Hidalgo");
    	
    	parties.getParty().add(actorParty);
    	
    	feature.setParties(parties);
    	
    	metadata.setFeature(feature);
    	
    	content.setParties(movieContentMapper.getPartiesFromMetadata(metadata, content));
    	assertEquals(1, content.getParties().size());
    }
    
    @Test
    public void getAllPartiesFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Parties parties = new Parties();
    	
    	Party actorParty = new Party();
    	actorParty.setCharacter("Jason");
    	actorParty.setBilling(new BigInteger("2"));
    	actorParty.setRoleType("ACTOR");
    	actorParty.setUniqueId("51712");
    	actorParty.setCharacter("");
    	actorParty.setFirstName("Ivan");
    	actorParty.setLastName("Hidalgo");
    	
    	Party directorParty = new Party();
    	directorParty.setCharacter("Stars");
    	directorParty.setBilling(new BigInteger("2"));
    	directorParty.setRoleType("DIRECTOR");
    	directorParty.setUniqueId("51712");
    	directorParty.setCharacter("");
    	directorParty.setFirstName("Ivan");
    	directorParty.setLastName("Hidalgo");
    	
    	parties.getParty().add(directorParty);
    	parties.getParty().add(actorParty);
    	
    	feature.setParties(parties);
    	
    	metadata.setFeature(feature);
    	
    	content.setParties(movieContentMapper.getPartiesFromMetadata(metadata, content));
    	assertEquals(2, content.getParties().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getPartiesFromMetadataWhenPartiesDoesNotExits() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	metadata.setFeature(feature);
    	
    	content.setParties(movieContentMapper.getPartiesFromMetadata(metadata, content));
    }
    
    @Test
    public void getRatingFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Ratings ratings = new Ratings();
    	Rating rating = new Rating();
    	rating.setCountry("Default");
    	rating.setValue("PG");
    	ratings.setRating(rating);
    	
    	PublishCountries countries = new PublishCountries();
    	Country country = new Country();
    	country.setId("COL");
    	countries.setCountry(country);
    	
    	Offering offering = new Offering();
    	offering.setUniqueId("119847");
    	offering.setPublishCountries(countries);
    	
    	feature.setOffering(offering);
    	
    	feature.setRatings(ratings);
    	
    	metadata.setFeature(feature);
    	
    	content.getRatings().add(movieContentMapper.getRatingFromMetadata(metadata, content));
    	assertEquals(1, content.getRatings().size());
    }
    
    @Test
    public void getRatingFromMetadataWhenItDoesNotHasRatings() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	metadata.setFeature(feature);
    	
    	com.tigoune.hbo.model.Rating rating = movieContentMapper.getRatingFromMetadata(metadata, content);
    	
    	assertEquals(null, rating);
    }
    
    @Test
    public void getAudioTrackFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	AudioTracks audioTracks = new AudioTracks();
    	
    	Original original = new Original();
    	original.setValue("ENG");
    	original.setAvailable(true);
    	
    	Localized spaLocalized = new Localized();
    	spaLocalized.setValue("SPA");
    	spaLocalized.setAvailable(true);
    	
    	Localized porLocalized = new Localized();
    	porLocalized.setValue("POR");
    	porLocalized.setAvailable(true);
    	
    	audioTracks.setOriginal(original);
    	audioTracks.getLocalized().add(porLocalized);
    	audioTracks.getLocalized().add(spaLocalized);
    	
    	video.setAudioTracks(audioTracks);
    	
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	content.setAudioTracks(movieContentMapper.getAudioTrackFromMetadata(metadata, content));
    	
    	assertEquals(3, content.getAudioTracks().size());
    }
    
    
    @Test
    public void getAudioTrackFromMetadataWithoutOriginalTrack() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	AudioTracks audioTracks = new AudioTracks();
    	
    	Localized spaLocalized = new Localized();
    	spaLocalized.setValue("SPA");
    	spaLocalized.setAvailable(true);
    	
    	Localized porLocalized = new Localized();
    	porLocalized.setValue("POR");
    	porLocalized.setAvailable(true);
    	
    	audioTracks.getLocalized().add(porLocalized);
    	audioTracks.getLocalized().add(spaLocalized);
    	
    	video.setAudioTracks(audioTracks);
    	
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	content.setAudioTracks(movieContentMapper.getAudioTrackFromMetadata(metadata, content));
    	
    	assertEquals(2, content.getAudioTracks().size());
    }
    
    @Test
    public void getAudioTrackFromMetadataWithoutLocalizedTracks() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	AudioTracks audioTracks = new AudioTracks();
    	
    	Original original = new Original();
    	original.setValue("ENG");
    	original.setAvailable(true);
    	
    	audioTracks.setOriginal(original);
    	
    	video.setAudioTracks(audioTracks);
    	
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	content.setAudioTracks(movieContentMapper.getAudioTrackFromMetadata(metadata, content));
    	
    	assertEquals(1, content.getAudioTracks().size());
    }
    
    @Test
    public void getAudioTrackFromMetadataWithoutTracks() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	content.setAudioTracks(movieContentMapper.getAudioTrackFromMetadata(metadata, content));
    	
    	assertEquals(0, content.getAudioTracks().size());
    }
    
    @Test
    public void getSubtitlesFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	Subtitles subtitles = new Subtitles();
    	subtitles.getSubtitleLanguage().add("SPA");
    	subtitles.getSubtitleLanguage().add("ENG");
    	subtitles.getSubtitleLanguage().add("POR");
    	
    	video.setSubtitles(subtitles);
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	content.setSubtitles(movieContentMapper.getSubtitlesFromMetadata(metadata, content));
    	
    	assertEquals(3, content.getSubtitles().size());
    }
    
    @Test
    public void getSubtitlesFromMetadataWhenItDoesNotExits() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Videos videos = new Videos();
    	Video video = new Video();
    	video.setRuntime(new BigInteger("1234"));
    	
    	videos.setVideo(video);
    	
    	feature.setVideos(videos);
    	
    	metadata.setFeature(feature);
    	content.setSubtitles(movieContentMapper.getSubtitlesFromMetadata(metadata, content));
    	
    	assertEquals(0, content.getSubtitles().size());
    }
    
    @Test
    public void getGeneresFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("1"));
    	episodeNumber.setInSeason(new BigInteger("1"));
    	Categories categories = new Categories();
    	categories.setCategory("Movies");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("SPA");
    	description.setTitle("Pelicula de prueba");
    	description.setSynopsis("Sypnosis");
    	description.setShortSynopsis("pelicula");
    	description.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("drama");
    	
    	Genre actionGenre = new Genre();
    	actionGenre.setOrder(new BigInteger("2"));
    	actionGenre.setValue("comedia");
    	
    	genres.getGenre().add(dramaGenre);
    	genres.getGenre().add(actionGenre);
    	
    	description.setGenres(genres);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setGenresOfContent(movieContentMapper.getGeneresFromMetadata(metadata, content));
    	
    	assertEquals(2, content.getGenresOfContent().size());
    }
    
    @Test
    public void getEnglishGeneresFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("terror-scifi");
    	
    	Genre actionGenre = new Genre();
    	actionGenre.setOrder(new BigInteger("2"));
    	actionGenre.setValue("drama");
    	
    	genres.getGenre().add(dramaGenre);
    	genres.getGenre().add(actionGenre);
    	
    	description.setGenres(genres);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setGenresOfContent(movieContentMapper.getGeneresFromMetadata(metadata, content));
    	
    	assertEquals(2, content.getGenresOfContent().size());
    }
    
    @Test
    public void getPortuguesGeneresFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
    	Description description = new Description();
    	EpisodeNumber episodeNumber = new EpisodeNumber();
    	episodeNumber.setInSeries(new BigInteger("1"));
    	episodeNumber.setInSeason(new BigInteger("1"));
    	Categories categories = new Categories();
    	categories.setCategory("Movies");
    	description.setEpisodeNumber(episodeNumber);
    	description.setReleaseYear(2017);
    	description.setCategories(categories);
    	description.setLanguage("POR");
    	description.setTitle("Pelicula de prueba");
    	description.setSynopsis("Sypnosis");
    	description.setShortSynopsis("pelicula");
    	description.setOriginalLanguageTitle("Pelicula de prueba");
    	
    	Genres genres = new Genres();
    	Genre dramaGenre = new Genre();
    	dramaGenre.setOrder(new BigInteger("1"));
    	dramaGenre.setValue("ação");
    	
    	Genre actionGenre = new Genre();
    	actionGenre.setOrder(new BigInteger("2"));
    	actionGenre.setValue("drama");
    	
    	genres.getGenre().add(dramaGenre);
    	genres.getGenre().add(actionGenre);
    	
    	description.setGenres(genres);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setGenresOfContent(movieContentMapper.getGeneresFromMetadata(metadata, content));
    	
    	assertEquals(2, content.getGenresOfContent().size());
    }
    
    
    
    @Test(expected=InvalidContentException.class)
    public void getGeneresFromMetadataWhenItDoesNotExist() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setGenresOfContent(movieContentMapper.getGeneresFromMetadata(metadata, content));
    }
    
    @Test
    public void getSeasonsFromMetadata() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	
    	Season season = new Season();
    	season.setUniqueId("500702S06");
    	season.setEpisodes(new BigInteger("13"));
    	season.setSeasonNumber(new BigInteger("07"));
    	
    	feature.setSeason(season);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setContentSeasons(movieContentMapper.getSeasonsFromMetadata(metadata, content));
    	
    	assertEquals(1, content.getContentSeasons().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getSeasonsFromMetadataWhenItDoesNOtExist() throws InvalidContentException, DatatypeConfigurationException, ParseException {
    	Content content = new Content();
        ContentId contentId = new ContentId();
        contentId.setContentId("123");
        content.setContentId(contentId);
        
    	Metadata metadata = new Metadata();
    	metadata.setId("FG-123");
    	
    	Feature feature = new Feature();
    	feature.setUniqueId("123");
    	
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
    	
    	Season season = new Season();
    	season.setUniqueId("500702S06");
    	season.setEpisodes(new BigInteger("13"));
    	season.setSeasonNumber(new BigInteger("07"));
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	content.setContentSeasons(movieContentMapper.getSeasonsFromMetadata(metadata, content));
    }
    
    @Test(expected=InvalidContentException.class)
    public void getContentWithoutCategoryTest() throws Exception {
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
    	
    	movieContentMapper.map(metadata);
    }

    
    @Test
    public void map() throws Exception {
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
    	
    	Content content = movieContentMapper.map(metadata);
    	
    	assertEquals(1, content.getDescriptions().size());
    	assertEquals(0, content.getContentChilds().size());
    	assertEquals(1, content.getOfferings().size());
    	assertEquals(0, content.getAudioTracks().size());
    	assertEquals(1, content.getGenresOfContent().size());
    	assertEquals(0, content.getContentSeasons().size());
    }
}
