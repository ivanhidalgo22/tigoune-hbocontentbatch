/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tigoune.hbo.unittest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.tigoune.hbo.helper.FileHelper;
import com.tigoune.hbo.storage.S3Storage;

import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author ivan
 */
public class S3StorageTest {
    
    private FileHelper fileHelper;
    private Resource resource;
    private static final String FILE_TEST = "src/documents/test/xml/LGF195536_136PSHW10C_SERIES.xml";
    private static final String SOURCE_DIRECTORY = "src/documents/test/";
    private static final String DEST_DIRECTORY = "src/documents/test/test.xml";
    
    public S3StorageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            FileHelper fileHelper = new FileHelper();
            resource = new FileSystemResource(FILE_TEST);
            fileHelper.setFileNamePattern(".xml");
            fileHelper.copyFile(resource.getFile(), DEST_DIRECTORY);
        } catch (IOException ex) {
            Logger.getLogger(S3StorageTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveDocumentsFromSourceDirectory method, of class S3Storage.
     */
    @Test
    public void testSaveDocumentsFromSourceDirectory() throws Exception {
        FileHelper fileHelperTest = new FileHelper();
        fileHelperTest.setFileNamePattern(".xml");

        // you can mock concrete classes, not only interfaces
        AmazonS3 mockedAmazonS3 = mock(AmazonS3.class);
        
        // stubbing appears before the actual execution
        when(mockedAmazonS3.putObject(any(PutObjectRequest.class))).thenReturn(new PutObjectResult());
        URL url = new URL("http://localhost");
        when(mockedAmazonS3.getUrl(any(String.class),any(String.class))).thenReturn(url);
        
        Resource sourceResource = new FileSystemResource(SOURCE_DIRECTORY);
 		File dir = sourceResource.getFile();
 		File[] files = fileHelperTest.getFilteredFilesFromDirectory(dir);
 		
 		// build instance to test.
 		S3Storage instance= new S3Storage();
 		instance.setAmazonS3(mockedAmazonS3);
 		instance.setFileHelper(fileHelperTest);
        
        assertEquals("http://localhost", instance.saveObject(files[0], "test.bck"));
    }
    
    @Test(expected=java.io.IOException.class)
    public void testSaveDocumentsWhenThrowsAmazonServiceException() throws Exception {
        FileHelper fileHelperTest = new FileHelper();
        fileHelperTest.setFileNamePattern(".xml");

        // you can mock concrete classes, not only interfaces
        AmazonS3 mockedAmazonS3 = mock(AmazonS3.class);

        // stubbing appears before the actual execution
        when(mockedAmazonS3.putObject(any(PutObjectRequest.class))).thenThrow(AmazonServiceException.class);

        Resource sourceResource = new FileSystemResource(SOURCE_DIRECTORY);
 		File dir = sourceResource.getFile();
 		File[] files = fileHelperTest.getFilteredFilesFromDirectory(dir);
 		
 		// build instance to test.
 		S3Storage instance= new S3Storage();
 		instance.setAmazonS3(mockedAmazonS3);;
 		instance.setFileHelper(fileHelperTest);
 		instance.saveObject(files[0], "test.bck");
     }
    
    @Test(expected=java.io.IOException.class)
    public void testSaveDocumentsWhenThrowsAmazonClientException() throws Exception {
        FileHelper fileHelperTest = new FileHelper();
        fileHelperTest.setFileNamePattern(".xml");

        // you can mock concrete classes, not only interfaces
        AmazonS3 mockedAmazonS3 = mock(AmazonS3.class);

        // stubbing appears before the actual execution
        when(mockedAmazonS3.putObject(any(PutObjectRequest.class))).thenThrow(AmazonClientException.class);

     	Resource sourceResource = new FileSystemResource(SOURCE_DIRECTORY);
		File dir = sourceResource.getFile();
		File[] files = fileHelperTest.getFilteredFilesFromDirectory(dir);
		
		// build instance to test.
		S3Storage instance= new S3Storage();
		instance.setAmazonS3(mockedAmazonS3);
		instance.setFileHelper(fileHelperTest);
		instance.saveObject(files[0], "test.bck");
     }
    
    @Test(expected=java.io.IOException.class)
    public void testSaveDocumentsWhenThrowsIOException() throws Exception {
		FileHelper fileHelperTest = new FileHelper();
		fileHelperTest.setFileNamePattern(".xml");
		// you can mock concrete classes, not only interfaces
		AmazonS3 mockedAmazonS3 = mock(AmazonS3.class);
		
		// stubbing appears before the actual execution
		when(mockedAmazonS3.putObject(any(PutObjectRequest.class))).thenThrow(IOException.class);
		
		Resource sourceResource = new FileSystemResource(SOURCE_DIRECTORY);
		File dir = sourceResource.getFile();
		File[] files = fileHelperTest.getFilteredFilesFromDirectory(dir);
		
		// build instance to test.
		S3Storage instance= new S3Storage();
		instance.setAmazonS3(mockedAmazonS3);
		instance.setFileHelper(fileHelperTest);
		instance.saveObject(files[0], "test.bck");
     }
    
}
