package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

import com.flytxt.imageprocessor.utils.ImageUtils;
/**
 * 
 * @author shiju.john
 *
 */
public class GitProcessor {
	

	
	/**
	 * For clone the repository 
	 * 
	 * @param repoPath  : Remote repository path 
	 * @param clonePath : local path 
	 * @return			: Git repository 
	 * @throws GitAPIException
	 */
	public Repository cloneRepository(String repoPath,String clonePath) throws GitAPIException{
		Git git = Git.cloneRepository().setURI(repoPath).setDirectory(new File(clonePath)).call();	
		return git.getRepository();
	}
	
	
	/**
	 * 
	 * @param localRepositoryPath : local repository path 
	 * @return Repository 		  : Repository 
	 * @throws CanceledException  : If unable to open the repository then method will throw the canceled exception  
	 */
	public Repository openRepository(String localRepositoryPath) throws CanceledException{
		try {
			Git git = Git.init().setDirectory(new File(localRepositoryPath)).call();
			return git.getRepository();
		} catch (IllegalStateException e) {
			throw new CanceledException("Unable to open the repository") ;			
		} catch (GitAPIException e) {
			throw new CanceledException("Unable to open the repository") ;		
		}		
	}
	
	
	/**
	 * 
	 * @param repository
	 * @throws GitAPIException
	 * @throws IOException 
	 */
	public void processRepository(Repository repository) throws GitAPIException, IOException{
		File rootDirectory = repository.getWorkTree();
		if(rootDirectory.isDirectory()){
			processFiles(rootDirectory);			
		}
	}
	
		
	/**
	 * Process the java files in the given repository 
	 * @param parentDirectory
	 * @throws IOException 
	 */
	private void processFiles(File parentDirectory) throws IOException  {
		File [] files = parentDirectory.listFiles(new JavaFileFilter());
		for(File file:files){
			if(file.isDirectory()){
				processFiles(file);
			}else{
				read(file);
			}
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws GitAPIException
	 */
	public static void main(String[] args) throws GitAPIException {
		Repository repository  = null;
		final String localPath =  "F://tmp/tmp_repo/temp repo1";
		try{
			GitProcessor gitProcessor = new GitProcessor();
			//repository  = gitProcessor.cloneRepository("https://github.com/shiju-john/convolutional_NN_Image.git",localPath);
			// repository  = gitProcessor.cloneRepository("https://github.com/shiju-john/javalearning4.git",localPath);
			repository = gitProcessor.openRepository(localPath);
			gitProcessor.processRepository(repository);
		}catch( GitAPIException  gitAPIException){
			gitAPIException.printStackTrace();			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
	
	/**
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void read(File file) throws IOException{
		if(!hasImageExist(file)){
			convert(file);		
		}		
	}
	
	/**
	 * Check the class image was already created or not 
	 * @param inputClassFile
	 * @param parentPath
	 * @return
	 */
	private boolean hasImageExist(File inputClassFile) {				
		File file = Paths.get(getImagePath(inputClassFile)).toFile();
		return file.exists();
	}
	
	/**
	 * For getting the image path from the given java file
	 * @param inputClassFile
	 * @return
	 */
	private String getImagePath(File inputClassFile) {
		return inputClassFile.getAbsolutePath().replace(".java",".jpg");		
	}


	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void convert(File file) throws IOException{
		String classContent = getFileAsString(file);
		if(null!=classContent){			
			classContent = removeCommentsFromFile(classContent);			
			int rgbArray[] = new int[classContent.length()];
			int index =0;
			String [] spliiedFiles= classContent.split("[\\s]+");
			for(String words: spliiedFiles){
				String [] splittedWords= words.split("[;]+");
				if(splittedWords.length>1){
					for(String value: splittedWords){
						index = processString(value,';',rgbArray,index);
					}
				}else{
					index = processString(words,' ',rgbArray,index); 
				}
			}
			int imageSize = getImageSize(classContent.length());			
			writeToImageFile(imageSize,rgbArray,file);			
		}
	}
	
	/**
	 * 
	 * @param imageSize
	 * @param rgbArray
	 * @param file
	 * @throws IOException
	 */
	private void writeToImageFile(int imageSize, int[] rgbArray, File file) throws IOException {
		BufferedImage bufferedImage = getBufferedImage(imageSize,imageSize,rgbArray);		
		File outFile = new File(getImagePath(file));
		ImageIO.write(bufferedImage, "jpg", outFile);
		outFile.createNewFile();		
	}


	/**
	 * 
	 * @param value
	 * @param separater
	 * @param rgbArray
	 * @param index
	 * @return
	 *   
	 */
	public int processString(String value,char separater,int []rgbArray,int index ){
		int rgbValue = ImageUtils.getInstance().getRGBValueOf(value);
		if(-1!=rgbValue){
			rgbArray[index++] = rgbValue;
		}else{
			char [] fileChars =  value.toCharArray();		
			for(char charValue:fileChars ){				
				rgbArray[index++] = ImageUtils.getInstance().getRGBValueOf(charValue);				
			}
		}
		rgbArray[index++] = ImageUtils.getInstance().getRGBValueOf(separater);
		return index;
	}
	
	

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getName(String name) {		
		int index = name.indexOf(".");
		return index!=-1?name.substring(0,index):name;		
	}

	/**
	 * 
	 * @param length
	 * @return
	 */
	private int getImageSize(int length) {
		Double value = Math.sqrt(length);
		return new Double(Math.ceil(value)).intValue();		
	}

	
	/**
	 * 
	 * @param height
	 * @param width
	 * @param rgb
	 * @return
	 */
	public BufferedImage getBufferedImage(int height,int width,int rgb[]){		
		BufferedImage bufferedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);	
		int index=0;
		for(int x=0;x<height;x++){
			for(int y=0;y<width;y++){
				if(rgb.length>index)
				bufferedImage.setRGB(x, y, rgb[index++]);
			}
		}
		return bufferedImage;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getFileAsString(File file) throws IOException{
		return FileUtils.readFileToString(file);
	}
	
	/**
	 * removing the java comments from the given input 
	 * @param fileContent
	 * @return
	 */
	public String removeCommentsFromFile(String fileContent){
	
		char [] fileCharcters = fileContent.toCharArray();
		char commentType = 'N';
		
		String fileUptoComment = null;
		String fileAfterComment = null;
		for(int index=0;index <fileCharcters.length; index++){
			if('N'==commentType){
				commentType = isStartComment(fileCharcters[index], fileCharcters, index);
				if('N'!=commentType){
					fileUptoComment = fileContent.substring(0,index);
				}					
			}
			if(isEndComment( fileCharcters, index,commentType)){
				fileAfterComment = fileContent.substring(index+2,fileContent.length());
				commentType ='N';
				index = fileContent.length();
			}			
		}	
		if(fileUptoComment!=null && null!=fileAfterComment){
			return removeCommentsFromFile(fileUptoComment+fileAfterComment);
		}
		return fileContent;
	}
	
	
	/**
	 * 
	 * @param charValue
	 * @param strValue
	 */
	public char isStartComment(char charValue, char[] strValue ,int index){
		if('/'==charValue && '/'==strValue[index+1]){
			strValue[index] = ' ';
			return 'S'; // Single Line comment
		}else if('/'==charValue && '*'==strValue[index+1]){	
			strValue[index] = ' ';
			strValue[index+1] = ' ';
			return 'M'; // Multi-line comment
		}
		return 'N'; // Not a comment
	}
	
	
	/**
	 * 
	 * @param charValue
	 * @param strValue
	 */
	
	public boolean isEndComment (char[] strValue ,int index,char commentModel){		
		if(commentModel== 'S'){
			if('\n'==strValue[index] ){
				strValue[index] =' ';
				return true;
			}
		}else if(commentModel == 'M' && '*'==strValue[index] && '/'==strValue[index+1]){	
			strValue[index] =' ';
			strValue[index+1] =' ';
			return true;
		}
		return false;
	}	
}
