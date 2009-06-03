package com.pineapple.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pineapple.FileWatcher;

public class FileWatcherTest {
    
    private FileWatcher myFileWatcher;
    private File testdir = new File("testdir");
    
    @Before
    public void setUp() throws Exception {
        try {
            if (testdir.exists()) {
                deleteFile(testdir);
            }
            testdir.mkdir();
            myFileWatcher = new FileWatcher("testdir");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @After
    public void tearDown() throws Exception {
        try {
        	myFileWatcher.removeWatch();
            if (testdir.exists()) {
                deleteFile(testdir);
            }
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
	 * This function will recursivly delete directories and files.
	 * @param path File or Directory to be deleted
	 * @return true indicates success.
	 */
	private static boolean deleteFile(File path) {
	    if( path.exists() ) {
	        if (path.isDirectory()) {
	            File[] files = path.listFiles();
	            for(int i=0; i<files.length; i++) {
	               if(files[i].isDirectory()) {
	                   deleteFile(files[i]);
	               } else {
	                   files[i].delete();
	               }
	           }
	       }
	   }
	   return(path.delete());
	}
    
    /**
     * Time sensitive.  Sleeps should insure proper behavior.  Kinda a hack.
     */
    @Test
    public void testFileWatcher() {
        File temp = new File("testdir" + File.separator + "test" + File.separator + "");
        try {
            temp.mkdirs();
            FileWriter fout = new FileWriter("testdir" + File.separator + "test" + File.separator + "test2.txt");
            BufferedWriter fwrite = new BufferedWriter(fout);
            fwrite.append("asdf1234");
            fwrite.close();
            Thread.sleep(10);
            assertEquals(getIP() + "\n+ test" + File.separator + "test2.txt 8\n", myFileWatcher
                    .getMessage());
            new File("testdir" + File.separator + "test" + File.separator + "test2.txt").delete();
            temp.delete();
            Thread.sleep(10);
            assertEquals(getIP() + "\n- test" + File.separator + "test2.txt 0\n", myFileWatcher
                    .getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
        
    }
    
    /**
     * More thread sleep hackiness.
     */
    @Test
    public void testGetMessage() {
        try {
			FileWriter fout = new FileWriter("testdir" + File.separator + "file1.txt");
			BufferedWriter fwrite = new BufferedWriter(fout);
			fwrite.append("123415asdfasdf");
			fwrite.close();

			fout = new FileWriter("testdir" + File.separator + "file 2.txt");
			fwrite = new BufferedWriter(fout);
			fwrite.append("asdf");
			fwrite.close();
			Thread.sleep(10);
			assertEquals(getIP() + "\n+ file1.txt 14\n+ file 2.txt 4\n",
					myFileWatcher.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
    
	@Test
	public void testFileRename() {
		try {
			FileWriter fout = new FileWriter("testdir" + File.separator + "file1.txt");
			BufferedWriter fwrite = new BufferedWriter(fout);
			fwrite.append("1234567890");
			fwrite.close();
			new File("testdir" + File.separator + "file1.txt").renameTo(new File(
					"testdir" + File.separator + "file 2.txt"));
			Thread.sleep(10);
			assertEquals(getIP() + "\n- file1.txt 0\n+ file 2.txt 10\n",
					myFileWatcher.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
    
    @Test
    public void testGetAllFiles()
    {
    	try{
    		myFileWatcher.removeWatch();
    		//Setup Directory
	    	File file = new File("testdir" + File.separator + "test");
	    	file.mkdirs();
	    	FileWriter fout = new FileWriter("testdir" + File.separator + "file1.txt");
	        BufferedWriter fwrite = new BufferedWriter(fout);
	        fwrite.append("1");
	        fwrite.close();
	        fout = new FileWriter("testdir" + File.separator + "file2.txt");
	        fwrite = new BufferedWriter(fout);
	        fwrite.append("12");
	        fwrite.close();
	        fout = new FileWriter("testdir" + File.separator + "test" + File.separator + "file 3.txt");
	        fwrite = new BufferedWriter(fout);
	        fwrite.append("123");
	        fwrite.close();
	        myFileWatcher = new FileWatcher("testdir");
	        StringBuilder sb = new StringBuilder();
	        //build comparison string
	        sb.append(getIP() +"\n");
	        sb.append("+ file1.txt 1\n");
	        sb.append("+ file2.txt 2\n");
	        sb.append("+ test" + File.separator + "file 3.txt 3\n");
	        //AssertEquals
	        assertEquals(sb.toString(), myFileWatcher.getAllFiles());
    	}catch (Exception e) {
    		e.printStackTrace();
			fail(e.getMessage());
		}
    }
    
	private String getIP() {
		//Modified from example at http://www.daniweb.com/forums/thread62812.html#
		String publicIP = "";
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
			e.printStackTrace();
			publicIP = "<Could-Not-Resolve-Public-IP-Address>";

		}
		return publicIP;
	}
    
}
