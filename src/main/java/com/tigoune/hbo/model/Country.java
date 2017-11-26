package com.tigoune.hbo.model;

import java.util.Hashtable;

public class Country {
	
	private Hashtable<String, String> table = new Hashtable<String, String>();
	
	public Country() {
		table.put("COL", "CO");
		table.put("MEX", "MX");
		table.put("USA", "US");
		table.put("CRI", "CR");
		table.put("GTM", "GT");
		table.put("BOL", "BO");
		table.put("HND", "HN");
		table.put("SLV", "SV");
		table.put("NIC", "NI");
		table.put("PRY", "PY");
		
		table.put("Default", "CO");
	}
	
	public String getCountryTypeByKey(String key) {
		return table.getOrDefault(key, "CO");
	}

}
