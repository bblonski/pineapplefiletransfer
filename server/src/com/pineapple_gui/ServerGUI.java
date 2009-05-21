package com.pineapple_gui;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;




/**
 * This class contains the server side gui code. It is written in Java Swing.
 * @author jmcelroy
 *
 */
public class ServerGUI extends JFrame {

	private String serverName = "";
	private String rootFolder = "";
	
	public ServerGUI(){
		this.setTitle("PineApple Server");
		
		this.setSize(300,300);
		
		this.add(setUpGui());
		
		
	}
	
	
	public JPanel setUpGui(){
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(2,1));
		main.add(serverNameGui());
		
		main.add(rootFolderGui());
		
		
		return main;
	}
	
	
	public Box serverNameGui(){
		Box box = Box.createHorizontalBox();
		
		return box;
	}
	
	public Box rootFolderGui(){
		Box box = Box.createHorizontalBox();
		
		return box;
	}
	
	
	
	
	
	public String getServerName(){
		return serverName;
	}
	
	public String getRootFolder(){
		return rootFolder;
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerGUI server = new ServerGUI();
		server.setVisible(true);

	}

}
