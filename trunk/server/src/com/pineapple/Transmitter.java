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

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jcraft.jsch.*;

/**
 * The Class Transmitter.
 */
public class Transmitter {
    
    /** The message. */
    private UpdateMessage message;
    
    /** The server name. */
    private String serverName;
    
    /** The server address. */
    private InetAddress serverAddress;
    
    /**
     * Instantiates a new transmitter.
     */
    public Transmitter() {
        try {
            serverAddress = InetAddress.getLocalHost();
            message = new UpdateMessage(serverAddress.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host: " + e.getMessage());
        }
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
     * Send.
     * 
     * @param user the user
     * @param host the host
     * @param port the port
     */
    public void send(String user, String host, int port) {
        try {
            JSch scp = new JSch();
            Session session = scp.getSession(user, host, port);
            // May need to add userInfo here
            // UserInfo userInfo = new
            // session.setUserInfo(userInfo);
            session.connect();
            // Used this from an example. Probably not correct
            // exec 'scp -t rfile' remotely
            String command = "scp -p -t updateFile";
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            
        } catch (JSchException e) {
            System.out.println(e.getMessage());
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
