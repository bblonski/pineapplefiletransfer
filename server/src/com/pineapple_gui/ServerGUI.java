package com.pineapple_gui;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import com.pineapple.Server;

/**
 * This class contains the server side gui code. It is written in Java Swing.
 * 
 * @author jmcelroy
 * 
 */
public class ServerGUI extends JFrame{

	private static String serverName = "";
	private static String rootFolder = "";

	private static JTextField serverNameInput;
	private static JTextField rootInput;

	// private static FileDialog fd;
	private static JFileChooser fd;
	
	private static ServerGUI self;

	public ServerGUI() {
		this.setTitle("PineApple Server");
		// fd = new FileDialog(this);
		fd = new JFileChooser();
		fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// fd.setTitle("Choose Root Directory");
		this.setSize(400, 200);
		this.setLocation(200, 200);
		this.add(setUpGui());
		self = this;
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

	public static JFileChooser getFD() {
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
	
	public static void noShow(){
		self.setVisible(false);
	}

	public static void exit() {
		System.exit(0);
	}

	public static JTextField getRootInput() {
		return rootInput;
	}
	
	public static String getIP(){
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

class ButtonListener implements ActionListener {
	ButtonListener() {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Run Server")) {
			if (ServerGUI.getServerName().equals("")) {
				System.out.println("Server Name is not yet set.");
			} else if (ServerGUI.getRootFolder().equals("")) {
				System.out.println("Root directory is not yet selected.");
			} else {
				System.out.println("Run that server named: "
						+ ServerGUI.getServerName() + " with directory: "
						+ ServerGUI.getRootFolder());
				//Put you name and password for vogon
				
				JOptionPane.showMessageDialog(new JFrame(), "IP address: " + ServerGUI.getIP());
				//String pass = JOptionPane.showInputDialog("Input your password FOOL!");
				String root = ServerGUI.getRootFolder();
				ServerGUI.noShow();
				Server server = new Server(root,"google","testpass","/home/jmcelroy/client_info.txt");
				
				
				
			}
		} else if (e.getActionCommand().equals("Set Server Name")) {
			ServerGUI.changeServerName();
			System.out.println("Server name changed to: "
					+ ServerGUI.getServerName());
		} else if (e.getActionCommand().equals("Choose")) {
			System.out.println("Run Root Directory");
			JFileChooser jf = ServerGUI.getFD();
			// FileDialog fd = ServerGUI.getFD();
			int returnVal = jf.showOpenDialog(new JFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				ServerGUI.getRootInput().setText(
						jf.getSelectedFile().toString());
				ServerGUI.setRootFolder(jf.getSelectedFile().toString());
			}
		} else if (e.getActionCommand().equals("Cancel")) {
			System.out.println("Exiting the system");
			ServerGUI.exit();
		}
	}
}
