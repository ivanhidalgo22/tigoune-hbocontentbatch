package com.tigoune.hbo.model;

import java.util.Hashtable;;

public class GenreType {
	
	private Hashtable<String, Integer> spanishGenres = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> englishGenres = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> portugueseGenres = new Hashtable<String, Integer>();
	
	public GenreType() {
		spanishGenres.put("", 0);
		spanishGenres.put("acción", 1);
		spanishGenres.put("aventura - fantasía", 3);
		spanishGenres.put("comedia", 5);
		spanishGenres.put("drama", 9);
		spanishGenres.put("familiar", 12);
		spanishGenres.put("romance", 16);
		spanishGenres.put("suspenso", 17);
		spanishGenres.put("terror - ciencia ficción", 19);
		
		englishGenres.put("", 0);
		englishGenres.put("action", 23);
		englishGenres.put("family", 28);
		englishGenres.put("terror-scifi", 36);
		englishGenres.put("thriller", 38);
		englishGenres.put("drama", 53);
		englishGenres.put("comedy", 54);
		englishGenres.put("romance", 55);
		englishGenres.put("adventure or fantasy", 56);
		
		portugueseGenres.put("", 0);
		portugueseGenres.put("ação", 39);
		portugueseGenres.put("aventura - fantasia", 41);
		portugueseGenres.put("comédia", 42);
		portugueseGenres.put("família", 47);
		portugueseGenres.put("terror-scifi", 49);
		portugueseGenres.put("drama", 50);
		portugueseGenres.put("suspense", 51);
		portugueseGenres.put("romance", 52);
		
		
	}
	
	public int getSpanishGenreTypeByKey(String key) {
		return spanishGenres.getOrDefault(key, 0);
	}
	
	public int getEnglishGenreTypeByKey(String key) {
		return englishGenres.getOrDefault(key, 0);
	}
	
	public int getPortugueseGenreTypeByKey(String key) {
		return portugueseGenres.getOrDefault(key, 0);
	}

}
