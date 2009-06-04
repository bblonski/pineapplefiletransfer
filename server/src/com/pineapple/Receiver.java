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
				FileInputStream fis = new FileInputStream(this.clientAddressFile);
				DataInputStream dis = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(dis));
				String str = br.readLine();
				if (str != null)
					return str;
			}
			catch (Exception e)
			{
				System.out.println("Attempting to open " + this.clientAddressFile + " and get clientAddress again...");
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
