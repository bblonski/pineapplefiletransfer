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
        this.list.remove(new FileUpdate(fileName));
        this.list.add(new FileUpdate(fileName, size, true));
    }
    
    /**
     * Adds a removed file update to the update message.
     * 
     * @param fileName the file name
     */
    public void remove(String fileName) {
        this.list.remove(new FileUpdate(fileName));
        this.list.add(new FileUpdate(fileName, 0, false));
    }
    
	/**
	 *  Clears the update message.
	 */
	public void clear() {
		this.list.delete();
	}

	/**
	 * Returns header.
	 */
	public String getHeader() {
		return this.header;
	}

	/**
	 * Returns true if file list is empty.
	 */
	public boolean isEmpty() {
		return this.list.size() == 0;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return list.toString();
    }
}
