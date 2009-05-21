/*
 * Team: Pineapple
 * Members: Brian Blonski,
 * 			Jon McElroy,
 * 			Matthew Schlatman
 * 			Greg Gire
 * Date: 5/12/09
 */
package com.pineapple;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;

// TODO: Auto-generated Javadoc
/**
 * The Class FileWatcher registers event handlers for native I/O calls on a given directory.
 */
public class FileWatcher implements IWatcher
{
	
	/** Add watch to subtrees. */
	private boolean watchSubtree = true;
	
	/** The watch id. */
	private int watchID;

	/**
	 * Instantiates a new file watcher on the given path.
	 * 
	 * @param path the directory to add the file watch
	 * 
	 * @throws Exception JNotify Exception
	 */
	public FileWatcher(String path) throws Exception
	{
		int mask =  JNotify.FILE_CREATED | 
					JNotify.FILE_DELETED | 
					JNotify.FILE_MODIFIED| 
					JNotify.FILE_RENAMED;

		watchID = JNotify.addWatch(path, mask, watchSubtree, new JNotifyListener()
		{
			public void fileRenamed(int wd, String rootPath, String oldName, String newName)
			{
				System.out.println("JNotifyTest.fileRenamed() : wd #" + wd + " root = " + rootPath
						+ ", " + oldName + " -> " + newName);
			}

			public void fileModified(int wd, String rootPath, String name)
			{
				System.out.println("JNotifyTest.fileModified() : wd #" + wd + " root = " + rootPath
						+ ", " + name);
			}

			public void fileDeleted(int wd, String rootPath, String name)
			{
				System.out.println("JNotifyTest.fileDeleted() : wd #" + wd + " root = " + rootPath
						+ ", " + name);
			}

			public void fileCreated(int wd, String rootPath, String name)
			{
				System.out.println("JNotifyTest.fileCreated() : wd #" + wd + " root = " + rootPath
						+ ", " + name);
			}
		});
	}



	/**
	 * Removes the watch.
	 * 
	 * @throws Exception JNotify Exception
	 */
	public void removeWatch() throws Exception
	{
		boolean res = JNotify.removeWatch(watchID);
		if (!res)
		{
			// invalid watch ID specified.
			throw new Exception("Invalid watch ID specified");
		}
	}
}
