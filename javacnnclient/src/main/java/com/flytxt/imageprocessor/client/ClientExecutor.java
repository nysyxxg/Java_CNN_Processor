package com.flytxt.imageprocessor.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientExecutor {
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(6);
		
		
		ClassThread airplanes = new ClassThread("F:/shiju/workspace/JavaCnnProcessor/"
				+ "javacnnserver/src/main/resources/classdataset/test/image/CORE_JAVA", "CORE_JAVA");
	/*	
		ClassThread airplanes = new ClassThread("F:/shiju/workspace/JavaCnnProcessor/"
				+ "javacnnserver/src/main/resources/classdataset/test/image/JPA", "JPA");
		ClassThread airplanes = new ClassThread("F:/shiju/workspace/JavaCnnProcessor/"
				+ "javacnnserver/src/main/resources/classdataset/test/image/SPRING_BOOT", "SPRING_BOOT"); */
		executor.execute(airplanes);
		
		
		
		executor.shutdown();
		
	
		
	}

}
