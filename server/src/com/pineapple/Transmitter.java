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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Thread;
import java.io.File;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.StreamGobbler;

/**
 * The Class Transmitter.
 */
public class Transmitter {
    
    /** The message. */
    private UpdateMessage message;
    
    /**
     * Instantiates a new transmitter.
     */
    public Transmitter() {
        message = new UpdateMessage(getIP());
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
    public void send(String username, String password, String clientAddressFile) {
		while (true)
		{
			System.out.println("In Send Loop.");
			try
			{
				File file = new File("metadata.wtf");
				try
				{
					// Create file if it does not exist
					boolean fileExists = file.createNewFile();
					if (fileExists)
					{
						//File does not exist
						try
						{
							//Write to file
							BufferedWriter out = new BufferedWriter(new FileWriter("metadata.wtf"));
							out.write(message.toString());
							message.clear(); // Message was transmitted successfully, clear all updates
							out.close();
						}
						catch (IOException e)
						{
							Thread.sleep(30000); // Waits 30 seconds then tries to establish connection to the client again
							continue;
						}

					}
					else
					{
						// File already exists
						try
						{
							// Append to file;
							BufferedWriter out = new BufferedWriter(new FileWriter("metadata.wtf", true));
							out.write(message.getBody());
							message.clear(); // Message was transmitted successfully, clear all updates
							out.close();
						}
						catch (IOException e)
						{
							Thread.sleep(30000); // Waits 30 seconds then tries to establish connection to the client again
							continue;
						}

					}
				}
				catch (Exception e)
				{
					Thread.sleep(30000); // Waits 30 seconds then tries to establish connection to the client again
					continue;
				}
				
				// Get client's address
				Receiver receiver = new Receiver(clientAddressFile);
				String clientAddress = receiver.getClientAddress();

				// Connect to server
				System.out.println("Try to establish connection to: " + clientAddress);
				Connection conn = new Connection(clientAddress);
				
				conn.connect();
				System.out.println("Trying to authenticate.");
				// Authenticate username and password
				boolean isAuthenticated = conn.authenticateWithPassword(username, password);

				if (!isAuthenticated)
				{
					System.out.println("Auth failed!");
					Thread.sleep(30000); // Waits 30 seconds then tries to establish connection to the client again
					continue;
				}
				
			
				SCPClient scp = conn.createSCPClient();
				
				scp.put("metadata.wtf", "~/Documents/CSC550/CodeBase/uniClient/");
				System.out.println("Metadata File Sent.");
				conn.close();
				file.delete();
				break;
			}
			catch (Exception e)
			{
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} // Waits 30 seconds then tries to establish connection to the client again
				continue;
			}
		}
    }
    
    /**
     * Gets the message.
     * 
     * @return the message
     */
    public UpdateMessage getMessage() {
		return message;
    }

	/**
	 * Returns the public IP address of the server. This method opens a
	 * connection to an www.ip-adress.com and reads the current IP address off
	 * the website. This is a total hack, but hey it works.
	 * 
	 * @return
	 */
	private String getIP() {
		//taken from example at http://forums.sun.com/thread.jspa?threadID=762802&tstart=0
		String publicIP = "";
		//String search = "<h2>My IP address: ";
		String search = "<span class=\"ip_value\">";
		try {

			URL tempURL = new URL("http://testip.edpsciences.org/");
			HttpURLConnection tempConn = (HttpURLConnection) tempURL
					.openConnection();
			InputStream tempInStream = tempConn.getInputStream();
			InputStreamReader tempIsr = new InputStreamReader(tempInStream);
			BufferedReader tempBr = new BufferedReader(tempIsr);
			//Find the line with the IP address on it.
			while(!publicIP.contains(search))
			{
				publicIP = tempBr.readLine();
			}
            // hack to find IP address in the HTML
			publicIP = publicIP.substring(publicIP.indexOf(search)+search.length(), publicIP.indexOf("</span>"));

			tempBr.close();
			tempInStream.close();

		} catch (Exception e) {
			e.printStackTrace(System.err);
			publicIP = "<Could-Not-Resolve-Public-IP-Address>";

		}
		return publicIP;
	}
    
}
