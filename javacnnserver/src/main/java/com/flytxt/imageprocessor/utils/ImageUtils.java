package com.flytxt.imageprocessor.utils;

import java.awt.Color;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author shiju.john
 * 
 */
public final class ImageUtils  {
	
	private static volatile ImageUtils me =null;
	private Map<Character,Integer> charMap ;
	private Map<String,Integer> keyWordMap;

	 
	public static  ImageUtils  getInstance(){
		if(null==me){
			synchronized (ImageUtils.class) {
				if(null==me){
					me = new ImageUtils();
				}
			}
		}
		return me;
	}
	
	private ImageUtils(){
		
		charMap = new HashMap<>(256);
		keyWordMap =  getKeyWordCollection();
		characterToMapConversion();
	}
	
	
	/**
	 * Convert the character to int value. 
	 * First 50 values are reserved for Java Keywords.
	 * Now the index is expected be , below 256.
	 */
	 private void characterToMapConversion() {
		int index= 60;
		for(char character:getDefaultCharacterSet()){
			if(!charMap.containsKey(character)){
				charMap.put(character, index++);
			}
		}			
	}
	
	/** A minimal character set, with a-z, A-Z, 0-9 and common punctuation etc */
	private static char[] getMinimalCharacterSet(){
		List<Character> validChars = new LinkedList<>();
		for(char c='a'; c<='z'; c++) validChars.add(c);
		for(char c='A'; c<='Z'; c++) validChars.add(c);
		for(char c='0'; c<='9'; c++) validChars.add(c);
		char[] temp = {'!', '&', '(', ')', '?', '-', '\'', '"', ',', '.', ':', ';', ' ', '\n', '\r','\t'};
		for( char c : temp ) validChars.add(c);
		char[] out = new char[validChars.size()];
		int i=0;
		for( Character c : validChars ) out[i++] = c;
		return out;
	}

	/** As per getMinimalCharacterSet(), but with a few extra characters */
	private static char[] getDefaultCharacterSet(){
		List<Character> validChars = new LinkedList<>();
		for(char c : getMinimalCharacterSet() ) validChars.add(c);
		char[] additionalChars = {'@', '#', '$', '%', '^', '*', '{', '}', '[', ']', '/', '+', '_','=',
				'\\', '|', '<', '>'};
		for( char c : additionalChars ) validChars.add(c);
		char[] out = new char[validChars.size()];
		int i=0;
		for( Character c : validChars ) out[i++] = c;
		return out;
	}
	
	/**
	 * 
	 * @param charValue
	 */
	public int getRGBValueOf(char charValue ){	
		int intVal = getIntValue(charValue);
		Color color = new Color(intVal, 0, 0);
		return color.getRGB();		
	}
	
	/**
	 * 
	 * @param charValue
	 * @return
	 */
	private int getIntValue(char charValue) {
		if(charMap.containsKey(charValue)){
			return charMap.get(charValue);
		}
		throw new UnsupportedCharsetException("Unsupported character "+charValue);
	}
	
	/**
	 * 
	 * @param charValue
	 * @return
	 */
	private int getIntValue(String  keyWord) {
		if(keyWordMap.containsKey(keyWord)){
			return keyWordMap.get(keyWord);
		}else{
			return -1;
		}
	}
	
	public  Map<String,Integer>  getKeyWordCollection(){
		String []keyWordCollection =  { "abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package",
				"synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected",
				"throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return",
				"transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void",
				"class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while" };
		 Map<String,Integer> keyWordMap = new  HashMap<>(64);
		 int index = 1;
		 for(String keyWord: keyWordCollection){
			 keyWordMap.put(keyWord, index++); 
		 }
		 return keyWordMap;
	}
	
	/**
	 * RGB value of a keyword
	 * @param value
	 * @return
	 */
	public int getRGBValueOf(String value) {
		int intVal = getIntValue(value);
		if(-1!=intVal){
			Color color = new Color(intVal, 0, 0);
			return color.getRGB();	
		}
		return intVal;
	}
	
}
