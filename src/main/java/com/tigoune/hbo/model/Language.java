package com.tigoune.hbo.model;

import java.util.Hashtable;

public class Language {

	private Hashtable<String, String> table = new Hashtable<String, String>();
	
	public Language() {
		table.put("SPA", "ES");
		table.put("POR", "PT");
		table.put("ENG", "US");
	}
	
	public String getLanguageTypeByKey(String key) {
		return table.getOrDefault(key, "US");
	}
}
