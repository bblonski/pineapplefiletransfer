package com.pineapple_gui;

import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.pineapple.Server;

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
	private static JTextField rootInput;

	private static FileDialog fd;

	public ServerGUI() {
		this.setTitle("PineApple Server");
		fd = new FileDialog(this);
		fd.setTitle("Choose Root Directory");
		this.setSize(400, 200);
		this.setLocation(200, 200);
		this.add(setUpGui());

	}

	public JPanel setUpGui() {
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;

		main.add(serverNameGui(), gbc);

		gbc.gridy++;

		main.add(rootFolderGui(), gbc);

		gbc.gridy++;

		main.add(buttons(), gbc);

		return main;
	}

	public Box serverNameGui() {
		Box box = Box.createHorizontalBox();

		serverNameInput = new JTextField();
		serverNameInput.setColumns(15);
		box.add(serverNameInput);

		JButton setButton = new JButton("Set Server Name");
		setButton.addActionListener(new ButtonListener());
		box.add(setButton);

		return box;
	}

	public Box rootFolderGui() {
		Box box = Box.createHorizontalBox();

		rootInput = new JTextField();
		rootInput.setColumns(25);
		box.add(rootInput);

		JButton setButton = new JButton("Choose");

		setButton.addActionListener(new ButtonListener());
		box.add(setButton);

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

	public static FileDialog getFD() {
		return fd;
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

	public static JTextField getRootInput() {
		return rootInput;
	}

}

class ButtonListener implements ActionListener {
	ButtonListener() {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Run Server")) {
			if(ServerGUI.getServerName().equals("")) {
				System.out.println("Server Name is not yet set.");
			}
			else if(ServerGUI.getRootFolder().equals("")){
				System.out.println("Root directory is not yet selected.");
			}
			else{
				System.out.println("Run that server named: "
						+ ServerGUI.getServerName() + " with directory: "
						+ ServerGUI.getRootFolder());
				Server server = new Server(ServerGUI.getRootFolder());
			}
		} else if (e.getActionCommand().equals("Set Server Name")) {
			ServerGUI.changeServerName();
			System.out.println("Server name changed to: "
					+ ServerGUI.getServerName());
		} else if (e.getActionCommand().equals("Choose")) {
			System.out.println("Run Root Directory");
			FileDialog fd = ServerGUI.getFD();
			fd.setVisible(true);
			ServerGUI.getRootInput().setText(fd.getDirectory());
			ServerGUI.setRootFolder(fd.getDirectory());
		} else if (e.getActionCommand().equals("Cancel")) {
			System.out.println("Exiting the system");
			ServerGUI.exit();
		}
	}
}
