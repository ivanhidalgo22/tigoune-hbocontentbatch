/**
 * IContentRepository.java
 *
 * @description: Contract to save HBO content in the database.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-10-2017.
 *
 **/

package com.tigoune.hbo.repository;

import com.tigoune.hbo.model.Content;

/**
 * Contract to save HBO content in the database.
 * @author IVAN HIDALGO.
 *
 */
public interface IContentRepository {

	/**
	 * Saves HBO content in the database.
	 * @param content HBO content (series/movies)
     * @throws com.tigoune.hbo.repository.CustomDataAccessException
	 */
	void saveContent(Content content) throws CustomDataAccessException;
}
