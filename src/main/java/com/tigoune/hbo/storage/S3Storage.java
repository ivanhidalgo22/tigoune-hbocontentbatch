/**
 * S3Storage.java
 *
 * @description: saves objects in Amazon Storage's bucket.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 05-04-2017.
 *
 **/
package com.tigoune.hbo.storage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Saves objects in Amazon Storage's bucket.
 * @author IVAN HIDALGO.
 *
 */
public class S3Storage extends AbstractStorage {
	
    /**
     * Amazon repository bucket.
     */
    private String bucketName;

    /**
     * Amazon S3 client.
     */
    @Autowired
    private AmazonS3 amazonS3;

    /**
     * Logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(S3Storage.class);

    /**
     * Default constructor.
     */
    public S3Storage() {}

    /* Saves objects in Amazon Storage's bucket. 
     * @see com.example.storage.AbstractStorage#saveObject(java.io.File, java.lang.String)
     */
    @Override
    public String saveObject(File resource, String resourceName) throws IOException, URISyntaxException {
    	logger.debug("Uploading new object to S3");
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        try {
        	amazonS3.putObject(new PutObjectRequest(getBucketName(),  resourceName, resource).withAccessControlList(acl));
            URL urlNew = amazonS3.getUrl(getBucketName(), resourceName);
            logger.debug("URL: " + urlNew.toURI().toString());
            deleteFile(resource);
            return urlNew.toURI().toString();
        } catch (AmazonServiceException ase) {
            logger.warn("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                "to Amazon S3, but was rejected with an error response" +
                " for some reason.");
            logger.warn("Error Message:    " + ase.getMessage()+
             " HTTP Status Code: " + ase.getStatusCode()+
             " AWS Error Code:   " + ase.getErrorCode()+
             " Error Type:       " + ase.getErrorType()+
             " Request ID:       " + ase.getRequestId());
             throw new IOException("AmazonServiceException: "+ ase.getMessage());
        } catch (AmazonClientException | IOException ace) {
            logger.warn("There was an error when this class tried to upload the object in Amazon S3: " + ace.getMessage());
                throw new IOException("AmazonServiceException: "+ ace.getMessage());
        }
    }

    /**
     * Gets storage's bucket name.
     * @return the bucketName.
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * Sets storage's bucket name.
     * @param bucketName the bucketName to set
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

	/**
	 * @return the amazonS3.
	 */
	public AmazonS3 getAmazonS3() {
		return amazonS3;
	}

	/**
	 * @param amazonS3 the amazonS3 to set.
	 */
	public void setAmazonS3(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}
}
