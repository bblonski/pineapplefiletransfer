/**
 * PineappleFileTransfer CSC 550 Spring 2009
 * Brian Blonski
 * Matt Schlachtman
 * Jon McElroy
 * Greg Gire
 * This project is licensed under the GNU General Public License v3
 */
package com.pineapple;

import java.util.ArrayList;

/**
 * The Class FileList is a list of FileUpdates.
 */
public class FileList {
    
    /** The list of file updates. */
    private ArrayList<FileUpdate> list;
    
    /**
     * Instantiates a new file list.
     */
    public FileList() {
        list = new ArrayList<FileUpdate>();
    }
    
    /**
     * Adds file updates to the list.
     * 
     * @param file the file update to add
     * 
     * @return the size of the list
     */
    public int add(FileUpdate file) {
        list.add(file);
        return list.size();
    }
    
    /**
     * Removes an update from the list.
     * 
     * @param file the file update to remove
     * 
     * @return true, if the file was removed
     */
    public boolean remove(FileUpdate file) {
        return list.remove(file);
    }
    
    /**
     * Gets the size of the list.
     * 
     * @return the size of the list
     */
    public int size() {
        return list.size();
    }
    
    
    public void delete(){
    	list = new ArrayList<FileUpdate>();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (FileUpdate fu : list) {
            sb.append(fu.toString());
            /*
             * I think we can do Unix only newlines here because we open the
             * file on Unix machines only.
             */
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
