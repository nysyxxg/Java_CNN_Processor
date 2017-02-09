package com;
import java.util.Scanner;

public class ReplaceChar {
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter string : ");
		String input =scanner.nextLine();
		System.out.println("Result "+ new ReplaceChar().process(input));
	}

	private String process(String inputString) {
		String outString="";
		boolean flag=false;
		if(inputString.length()<=2){
			outString = inputString;
		}
		for(int i=0;i<inputString.length()-2;i++){
			if(inputString.charAt(i)!=inputString.charAt(i+1)){
				outString+=inputString.charAt(i);				
			}else if(inputString.charAt(i)!=inputString.charAt(i+2)){
				outString+=inputString.charAt(i);				
			}else{
				i=i+2;
				flag =true;
				
			}
			if(i==inputString.length()-3 ){
				outString+=inputString.charAt(i+1);
				outString+=inputString.charAt(i+2);
			}else if(i>inputString.length()-3 && i!=inputString.length()-1){
				outString+=inputString.charAt(i+1);
			}
			
			
		}
		if(flag){
			return process(outString);
		}
		return outString;
	}

}
