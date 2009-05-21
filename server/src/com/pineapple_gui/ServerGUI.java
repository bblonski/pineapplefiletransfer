package com.pineapple_gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.Severity;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class contains the server side gui code. It is written in Java Swing.
 * 
 * @author jmcelroy
 * 
 */
public class ServerGUI extends JFrame {

	private static String serverName = "";
	private static String rootFolder = "";

	private static JTextField serverNameInput;

	public ServerGUI() {
		this.setTitle("PineApple Server");

		this.setSize(400, 400);
		this.setLocation(200, 200);
		this.add(setUpGui());

	}

	public JPanel setUpGui() {
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(2, 1));
		main.add(serverNameGui());

		main.add(rootFolderGui());

		main.add(buttons());

		return main;
	}

	public Box serverNameGui() {
		Box box = Box.createHorizontalBox();

		serverNameInput = new JTextField();
		box.add(serverNameInput);

		JButton setButton = new JButton("Set Server Name");
		setButton.addActionListener(new ButtonListener());
		box.add(setButton);

		return box;
	}

	public Box rootFolderGui() {
		Box box = Box.createHorizontalBox();

		return box;
	}

	public Box buttons() {
		Box box = Box.createHorizontalBox();

		JButton okButton = new JButton("Run Server");
		okButton.addActionListener(new ButtonListener());
		box.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ButtonListener());
		box.add(cancelButton);

		return box;

	}

	public static String getServerName() {
		return serverName;
	}

	public static String getRootFolder() {
		return rootFolder;
	}

	public static void setServerName(String name) {
		serverName = name;
	}

	public static void setRootFolder(String folder) {
		rootFolder = folder;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerGUI server = new ServerGUI();
		server.setVisible(true);

	}

	public static void changeServerName() {
		if (serverNameInput.getText() != "") {
			serverName = serverNameInput.getText();
		}
	}
	
	public static void exit() {
		System.exit(0);
	}

}

class ButtonListener implements ActionListener {
	ButtonListener() {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Run Server")) {
			System.out.println("Run that server!");
		} else if (e.getActionCommand().equals("Set Server Name")) {
			ServerGUI.changeServerName();
			System.out.println("Server name changed to: " + ServerGUI.getServerName());
		} else if (e.getActionCommand().equals("Cancel")) {
			System.out.println("Exiting the system");
			ServerGUI.exit();
		}
	}
}
