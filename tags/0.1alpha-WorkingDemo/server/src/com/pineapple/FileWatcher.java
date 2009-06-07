/*
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

import java.io.File;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

/**
 * The Class FileWatcher registers event handlers for native I/O calls on a
 * given directory.
 */
public class FileWatcher implements IWatcher {
    
    /** Add watch to subtrees. */
    private boolean watchSubtree = true;
    
    /** The watch id. */
    private int watchID;
    
    /** The message transmitter. */
    private Transmitter transmitter;

    /** The path to watch. */
	private String path;
    
    /**
     * Instantiates a new file watcher on the given path.
     * 
     * @param path the directory to add the file watch
     * 
     * @throws Exception JNotify Exception
     */
	public FileWatcher(String path, final Transmitter transmitter) throws JNotifyException
	{
		this.path = path;
		this.transmitter = transmitter;
		
        int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED
                | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;

		watchID = JNotify.addWatch(path, mask, watchSubtree, new JNotifyListener()
		{
			public void fileRenamed(int wd, String rootPath, String oldName, String newName)
			{
				System.out.println("JNotifyTest.fileRenamed() : wd #" + wd + " root = " + rootPath
						+ ", " + oldName + " -> " + newName);
	            File file = new File(File.separator + rootPath + File.separator + oldName);
                System.out.println("- " + oldName + ", " + file.length());
                transmitter.removeFile(oldName);
	            file = new File(rootPath + File.separator + newName);
	            if(file.exists() && file.isFile())
	            {
	                System.out.println("+ " + newName + ", " + file.length());
	                transmitter.addFile(newName, file.length());
	            }
			}

			public void fileModified(int wd, String rootPath, String name)
			{
	            System.out.println("JNotifyTest.fileModified() : wd #" + wd + " root = " + rootPath
	                        + ", " + name);
	            File file = new File(rootPath + File.separator + name);
	            if(file.exists() && file.isFile())
	            {
	                System.out.println("+ " + name + ", " + file.length());
	                transmitter.addFile(name, file.length());
	            }
			}

			public void fileDeleted(int wd, String rootPath, String name)
			{
				System.out.println("JNotifyTest.fileDeleted() : wd #" + wd + " root = " + rootPath
						+ ", " + name);
				File file = new File(rootPath + File.separator + name);
				System.out.println("- " + name + ", " + file.length());
				transmitter.removeFile(name);
			}

			public void fileCreated(int wd, String rootPath, String name)
			{
				System.out.println("JNotifyTest.fileCreated() : wd #" + wd + " root = " + rootPath
						+ ", " + name);
				File file = new File(rootPath + File.separator + name);
				if(file.exists() && file.isFile())
				{
                    System.out.println("+ " + name + ", " + file.length());
                    transmitter.addFile(name, file.length());
				}
			}
		});
	}
	
    
    /**
     * Returns the update message currently waiting in the transmitter.
     * 
     * @return The message to send.
     */
    public String getMessage() {
        return transmitter.getMessage().toString();
    }
    
	/**
	 * Adds all files in the watch path to the updateMessage and returns is as a
	 * String.
	 * 
	 * @return An updateMessage containing a list of all files in the watch
	 *         directory.
	 */
	public String getAllFiles() {
		searchDir(path);
		return getMessage();
	}

	/**
	 * searchDir is a recursive search method for adding all files in the watch
	 * directory to the update message.
	 * 
	 * @param pathName
	 *            The path to do the recursive search on.
	 */
	private void searchDir(String pathName) {
		File file = new File(pathName);
		for (String subdir : file.list()) {
			file = new File(pathName + File.separator + subdir);
			if (file.isFile()) {
				if (pathName.equals(path)) {
					transmitter.addFile(subdir, file.length());
				} else {
					String test = pathName.substring(path.length() + 1) + File.separator + subdir;
					transmitter.addFile(test, file.length());
				}
			} else if (file.isDirectory()) {
				searchDir(pathName + File.separator + subdir);
			}
		}
	}
    
    /**
     * Removes the watch.
     * 
     * @throws Exception JNotify Exception
     */
    public void removeWatch() throws Exception {
        boolean res = JNotify.removeWatch(watchID);
        if (!res) {
            // invalid watch ID specified.
            throw new Exception("Invalid watch ID specified");
        }
    }
    
}
