package com.flytxt.imageprocessor.request;

import java.io.IOException;
import java.util.List;

import com.flytxt.imageprocessor.coordinator.ImageCoordinator;
import com.flytxt.imageprocessor.coordinator.LOAD_STRATEGY;

/**
 * 
 * @author shiju.john
 *
 */
public class RequestHandler implements RequestProcessor {

	private ImageCoordinator coordinator = null;
	
	public RequestHandler() throws IOException, InterruptedException{
		coordinator = ImageCoordinator.getInstance(LOAD_STRATEGY.CREATE);
	}
	


	@Override
	public String predict(String path) {
		return coordinator.predict(path);
	}

	@Override
	public List<String> evaluates(String path) {
		return coordinator.evaluates(path);
	}

}
