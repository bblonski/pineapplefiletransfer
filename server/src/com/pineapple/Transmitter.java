/*
 * PineappleFileTransfer
 * CSC 550 Spring 2009
 * Brian Blonski
 * Matt Schlachtman
 * Jon McElroy
 * Greg Gire
 * This project is licensed under the GNU General Public License v3
 */
package com.pineapple;

import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * The Class Transmitter.
 */
public class Transmitter {
    
    /** The message. */
    private UpdateMessage message;
    
    /** The server address. */
    private String serverAddress;
    
    /**
     * Instantiates a new transmitter.
     */
    public Transmitter(String serverAddress) {
    	this.serverAddress = serverAddress;
        message = new UpdateMessage(serverAddress);
    }
    
    /**
     * Adds the file.
     * 
     * @param fileName the file name
     * @param size the size
     */
    public void addFile(String fileName, long size) {
        message.add(fileName, size);
    }
    
    /**
     * Removes the file.
     * 
     * @param fileName the file name
     */
    public void removeFile(String fileName) {
        message.remove(fileName);
    }
    
    /**
     * Send message using SCP.
     * 
     * @param username the username
     * @param password the password
     */
    public void send(String username, String password) {
    	//TODO Connection should be kept open with client as long as possible
    	try {
    		// Connect to server
    		Connection conn = new Connection(serverAddress);
    		conn.connect();
    		
    		// Authenticate username and password
    		boolean isAuthenticated = conn.authenticateWithPassword(username, password);
    		
    		if(!isAuthenticated) {
    			throw new IOException("ERROR: Authentication Failed");
    		}
    		
    		// Create a session
    		Session sess = conn.openSession();
    		
    		// Send Message
    		sess.execCommand("scp -p -t updateFile");
    		// OR is it sess.execCommand("scp updateFile directory/TargetFile"); ???
    		
    		
    		//DEBUG
    		sess.close();
    		conn.close();    		
    	} catch (IOException e)
    	{
    		e.printStackTrace(System.err);
    		System.exit(2);
    	}
    }
    
    /**
     * Gets the message.
     * 
     * @return the message
     */
    public String getMessage() {
        return message.toString();
    }
    
}
