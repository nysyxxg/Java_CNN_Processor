package com;

public class ReplaceCharN {
	
	public String checkDuplicate(String strValue){
		String result ="";
		for(int i=0;i<strValue.length();i++){
			boolean duplicateFound = true;
			char characterValue =  strValue.charAt(i);
			for(int j=i+1;j<=i+2 && j < strValue.length();j++){
				if(characterValue!=strValue.charAt(j)){
					duplicateFound = false;
				}
			}
			if(!duplicateFound){
				result = result+characterValue;
			}else{
				i=i+2;
			}
		}
		checkDuplicate(result);
		return null;
	}

}
