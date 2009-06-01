package com.pineapple;

import net.contentobjects.jnotify.JNotifyException;

/**
 * This class is the main driver for running the server. It is run from the pineapple GUI.\
 * 
 * @author jmcelroy
 *
 */


public class Server {

	
	private Transmitter transmitter;
	private FileWatcher fileWatcher;
	
	public Server(String path){
		try {
			System.out.println("Path is " + path);
			fileWatcher = new FileWatcher(path);
		} catch (JNotifyException e) {
			e.printStackTrace();
		} 
		
	}
}
