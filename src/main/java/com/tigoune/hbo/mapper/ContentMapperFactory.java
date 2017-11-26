/**
 * ContentMapperFactory.java
 *
 * @description: this class creates a new content instance.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-11-2017.
 *
 **/

package com.tigoune.hbo.mapper;

/**
 * this class creates a new content instance.
 * @author Ivan Hidalgo.
 *
 */
public class ContentMapperFactory {

	/**
	 * creates a new content instance.
	 * @param content type
	 * @return new Content instance.
	 */
	public synchronized static ContentMapper createContentMapper(String type) {
		ContentMapper contentMapper = null;
		
		if(type.equals("SERIE")) {
			contentMapper = new SerieContentMapper();
		} else if (type.equals("MOVIE"))
			contentMapper = new MovieContentMapper();
		return contentMapper;
	}
	
}
