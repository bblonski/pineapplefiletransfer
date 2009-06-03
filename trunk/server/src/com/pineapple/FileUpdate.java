/**
 * PineappleFileTransfer
 * CSC 550 Spring 2009
 * Brian Blonski
 * Matt Schlachtman
 * Jon McElroy
 * Greg Gire
 * 
 * This project is licensed under the GNU General Public License v3
 */
package com.pineapple;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUpdate is an object that represents a file event. It holds the
 * name, size, and whether the file is being deleted or created/modified. The
 * file name include the relative path.
 */
public class FileUpdate {
    
    /** The file name. */
    private String fileName;
    
    /** The file size. */
    private long fileSize;
    
    /** The status of the file. True for added/changed, false for deleted. */
    private boolean status;
    
    /**
     * Instantiates a new file update.
     * 
     * @param fileName the file name
     */
    public FileUpdate(String fileName) {
        this(fileName, 0, false);
    }
    
    /**
     * Instantiates a new file update.
     * 
     * @param fileName the file name
     * @param size the size
     * @param status true for added/changed, false for deleted.
     */
    public FileUpdate(String fileName, long size, boolean exists) {
        this.fileName = fileName;
        this.fileSize = size;
        this.status = exists;
    }
    
    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * Gets the file size.
     * 
     * @return the file size
     */
    public long getFileSize() {
        return this.fileSize;
    }
    
    /**
     * Gets the status.
     * 
     * @return true, if successful
     */
    public boolean getStatus() {
        return this.status;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof FileUpdate) {
            FileUpdate obj = (FileUpdate) o;
            if (obj.getFileName().equals(this.fileName)) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (status) {
            sb.append("+ ");
        } else {
            sb.append("- ");
        }
        sb.append(fileName);
        sb.append(" ");
        sb.append(fileSize);
        return sb.toString();
    }
    
}
