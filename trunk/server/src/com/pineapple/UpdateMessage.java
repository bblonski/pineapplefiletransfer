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

/**
 * The Class UpdateMessage.
 */
public class UpdateMessage implements IMessage {
    
    /** The server IP header. */
    private String header;
    
    /** The list of file updates. */
    private FileList list;
    
    /**
     * Instantiates a new update message.
     * 
     * @param header the server identification header
     */
    public UpdateMessage(String header) {
        this.list = new FileList();
        this.header = header;
    }
    
    /**
     * Adds a file added update to the update message.
     * 
     * @param fileName the file name
     * @param size the size of the file
     */
    public void add(String fileName, long size) {
        list.remove(new FileUpdate(fileName));
        this.list.add(new FileUpdate(fileName, size, true));
    }
    
    /**
     * Adds a removed file update to the update message.
     * 
     * @param fileName the file name
     */
    public void remove(String fileName) {
        list.remove(new FileUpdate(fileName));
        this.list.add(new FileUpdate(fileName, 0, false));
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return header + "\n" + list.toString();
    }
}
