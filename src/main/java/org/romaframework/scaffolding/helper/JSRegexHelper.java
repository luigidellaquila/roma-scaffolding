package org.romaframework.scaffolding.helper;

import java.util.Map;

public class JSRegexHelper {
	private Map<String, String> feature2regex;

	public Map<String, String> getFeature2regex() {
		return feature2regex;
	}

	public void setFeature2regex(Map<String, String> feature2regex) {
		this.feature2regex = feature2regex;
	}
	
	public String getRegex(String key){
		String regex = null;
		
		if(key!=null)
			regex = feature2regex.get(key);
		
		return (regex != null)? regex : key;
	}
}
