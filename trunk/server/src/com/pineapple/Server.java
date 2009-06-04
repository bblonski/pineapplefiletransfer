package com.pineapple;

import net.contentobjects.jnotify.JNotifyException;
import java.lang.Thread;

/**
 * This class is the main driver for running the server. It is run from the pineapple GUI.\
 * 
 * @author jmcelroy
 *
 */


public class Server {

	
	private Transmitter transmitter;
	private FileWatcher fileWatcher;
	String username;
	String password;
	String clientAddressFile;
	
	public Server(String path, String username, String password, String clientAddressFile){
		this.username = username;
		this.password = password;
		this.clientAddressFile = clientAddressFile;

		try {
			transmitter = new Transmitter();
			fileWatcher = new FileWatcher(path, transmitter);
			
		} catch (JNotifyException e) {
			e.printStackTrace();
		}

		continuousUpdate();
	}

	public void continuousUpdate()
	{
		while (true)
		{
			System.out.println("In Continuous Update");
			if (!transmitter.getMessage().isEmpty())
			{
				transmitter.send("username", "password", "clientAddressFile");
			}

			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //Wait 30 seconds
		}
	}
}
