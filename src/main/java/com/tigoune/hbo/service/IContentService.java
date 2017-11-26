/**
 * IContentService.java
 *
 * @description: Contract to validate an HBO content before saving it in the database.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.service;

import com.tigoune.hbo.model.Content;
import com.tigoune.hbo.repository.PersisterException;

/**
 * Contract to validate an HBO content before saving it in the database.
 * @author IVAN HIDALGO.
 *
 */
public interface IContentService {
	
	/**
	* validates an HBO content before saving it in the database.
	* @param content HBO content (series/movies).
    * @throws com.tigoune.hbo.repository.PersisterException
    * @throws java.lang.InterruptedException
	*/
	void addContent(Content content) throws PersisterException, InterruptedException ;
}
