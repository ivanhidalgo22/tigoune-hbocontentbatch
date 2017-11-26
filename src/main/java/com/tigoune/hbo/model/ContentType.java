package com.tigoune.hbo.model;

import java.util.Hashtable;

public class ContentType {
	
	private static Hashtable<String, String> table = new Hashtable<String, String>();
    
	public ContentType() {
		table.put("Películas", "Movie");
		table.put("Filmes", "Movie");
		table.put("Movies", "Movie");
	    table.put("Series/A-Z", "Episode");
	    table.put("Séries/A-Z", "Episode");
	    table.put("Especiales", "Special");
	    table.put("Especiais", "Special");
	    table.put("Specials", "Special");
	    table.put("Documentales", "Documentary");
	    table.put("Documentários", "Documentary");
	    table.put("Documentary", "Documentary");
	    table.put("Infantil", "Kids");
	    table.put("Children", "Kids");
	    table.put("Adulto/18+", "Adult");
	    table.put("Adult", "Adult");
	}
	
	public String getContentTypeByKey(String key) {
		return table.getOrDefault(key, "");
	}
}
