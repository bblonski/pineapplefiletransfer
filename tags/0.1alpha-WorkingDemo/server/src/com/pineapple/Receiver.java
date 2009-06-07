package com.pineapple;

import java.io.*;
import java.lang.Thread;

public class Receiver {

	private String clientAddressFile;

	public Receiver()
	{
		this.clientAddressFile = null;
	}

	public Receiver(String clientAddressFile) 
	{
		this.clientAddressFile = clientAddressFile;
	}
	
	public String getClientAddress() {
		while(true) 
		{
			// Open the file that contains the client IP address string and return it
			try
			{
				System.out.println("Opening client address file: " + this.clientAddressFile);
				FileInputStream fis = new FileInputStream(this.clientAddressFile);
				DataInputStream dis = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(dis));
				String str = br.readLine();
				if (str != null)
				{
					System.out.println("Client IP Address: " + str);
					return str;
				}
			}
			catch (Exception e)
			{
				System.out.println("Unable to open " + this.clientAddressFile + ". Waiting to recieve clientAddress...");
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //Wait 30 seconds then attempt to open client IP file again
				continue;
			}
		}
	}
	
	public void setClientAddress(String clientAddressFile) {
		this.clientAddressFile = clientAddressFile;
	}
}
