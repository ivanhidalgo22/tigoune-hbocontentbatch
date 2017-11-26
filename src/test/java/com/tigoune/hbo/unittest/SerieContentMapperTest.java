package com.tigoune.hbo.unittest;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tigoune.hbo.mapper.InvalidContentException;
import com.tigoune.hbo.mapper.SerieContentMapper;
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

public class SerieContentMapperTest {

	SerieContentMapper serieContentMapper;
	
	public SerieContentMapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    	serieContentMapper = new SerieContentMapper();
    }
    
    @After
    public void tearDown() {
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
    	categories.setCategory("[Series/A-Z]");
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
    	image.setType("[Series/A-Z]");
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
    	
    	Content content = serieContentMapper.map(metadata);
    	
    	assertEquals(1, content.getDescriptions().size());
    	assertEquals(1, content.getContentChilds().size());
    	assertEquals(0, content.getOfferings().size());
    	assertEquals(0, content.getAudioTracks().size());
    	assertEquals(0, content.getGenresOfContent().size());
    	assertEquals(1, content.getContentSeasons().size());
    }
    
    @Test
    public void mapWithFirstEpisode() throws Exception {
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
    	image.setType("[Series/A-Z]");
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	Content content = serieContentMapper.map(metadata);
    	
    	assertEquals(1, content.getDescriptions().size());
    	assertEquals(1, content.getContentChilds().size());
    	assertEquals(1, content.getOfferings().size());
    	assertEquals(0, content.getAudioTracks().size());
    	assertEquals(1, content.getGenresOfContent().size());
    	assertEquals(1, content.getContentSeasons().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getSerieContentFromMetadataWithoutDescription() throws Exception {
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
    	image.setType("[Series/A-Z]");
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	metadata.setFeature(feature);
    	
    	serieContentMapper.map(metadata);
    }
    
    @Test(expected=InvalidContentException.class)
    public void getSerieContentFromMetadataWithoutVideos() throws Exception {
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
    	image.setType("[Series/A-Z]");
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	feature.setSeason(season);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	serieContentMapper.map(metadata);
    }
    
    @Test(expected=InvalidContentException.class)
    public void getSerieDescriptionFromMetadataWhenItDoesNotExist() throws Exception {
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
    	image.setType("[Series/A-Z]");
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	description.setGenres(genres);
    	
    	feature.setVideos(videos);
    	
    	feature.setSeries(series);
    	
    	feature.setParties(parties);
    	
    	feature.setOffering(offering);
    	
    	feature.setSeason(season);
    	
    	feature.getDescription().add(description);
    	
    	metadata.setFeature(feature);
    	
    	serieContentMapper.map(metadata);
    }
    
    @Test
    public void getSerieLinksFromMetadata640x960() throws Exception {
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
    	image.setImageResolution("640x960");
    	image.setType("Serie");
    	image.setLc("ORI");
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	Content content = serieContentMapper.map(metadata);
    	
    	assertEquals(1, content.getDescriptions().size());
    	assertEquals(3, content.getLinks().size());
    	assertEquals(1, content.getContentChilds().size());
    	assertEquals(1, content.getOfferings().size());
    	assertEquals(0, content.getAudioTracks().size());
    	assertEquals(1, content.getGenresOfContent().size());
    	assertEquals(1, content.getContentSeasons().size());
    }
    
    @Test
    public void getSerieLinksFromMetadata1920x1080() throws Exception {
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
    	image.setImageResolution("1920x1080");
    	image.setType("Serie");
    	image.setLc("ORI");
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	Content content = serieContentMapper.map(metadata);
    	
    	assertEquals(1, content.getDescriptions().size());
    	assertEquals(4, content.getLinks().size());
    	assertEquals(1, content.getContentChilds().size());
    	assertEquals(1, content.getOfferings().size());
    	assertEquals(0, content.getAudioTracks().size());
    	assertEquals(1, content.getGenresOfContent().size());
    	assertEquals(1, content.getContentSeasons().size());
    }
    
    @Test(expected=InvalidContentException.class)
    public void getSerieLinksFromMetadataWithoutImages() throws Exception {
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
    	season.setEpisodes(new BigInteger("1"));
    	season.setSeasonNumber(new BigInteger("01"));
    	
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
    	
    	serieContentMapper.map(metadata);
    }

    
}
