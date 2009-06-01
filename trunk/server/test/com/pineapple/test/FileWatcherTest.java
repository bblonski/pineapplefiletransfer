package com.pineapple.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pineapple.FileWatcher;

public class FileWatcherTest {
    
    private FileWatcher myFileWatcher;
    private File testdir;
    
    @Before
    public void setUp() throws Exception {
        try {
            testdir = new File("testdir");
            if (testdir.exists()) {
                testdir.delete();
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
            Thread.sleep(500);
            myFileWatcher.removeWatch();
            testdir.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            assertEquals(getIP() + "\n+ test" + File.separator + "test2.txt, 8\n", myFileWatcher
                    .getMessage());
            new File("testdir" + File.separator + "test" + File.separator + "test2.txt").delete();
            temp.delete();
            Thread.sleep(10);
            assertEquals(getIP() + "\n- test" + File.separator + "test2.txt, 0\n", myFileWatcher
                    .getMessage());
        } catch (Exception e) {
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

			fout = new FileWriter("testdir" + File.separator + "file2.txt");
			fwrite = new BufferedWriter(fout);
			fwrite.append("asdf");
			fwrite.close();
			Thread.sleep(10);
			assertEquals(getIP() + "\n+ file1.txt, 14\n+ file2.txt, 4\n",
					myFileWatcher.getMessage());
			new File("testdir" + File.separator + "file1.txt").delete();
			new File("testdir" + File.separator + "file2.txt").delete();
		} catch (Exception e) {
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
					"testdir" + File.separator + "file2.txt"));
			assertEquals(getIP() + "\n- file1.txt, 0\n+ file2.txt, 10\n",
					myFileWatcher.getMessage());
			new File("testdir" + File.separator + "file2.txt").delete();
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}
    
    @Test
    public void testGetAllFiles()
    {
    	try{
    		//Setup Directory
	    	File file = new File("testdir2" + File.separator + "test");
	    	file.mkdirs();
	    	FileWriter fout = new FileWriter("testdir2" + File.separator + "file1.txt");
	        BufferedWriter fwrite = new BufferedWriter(fout);
	        fwrite.append("1");
	        fwrite.close();
	        fout = new FileWriter("testdir2" + File.separator + "file2.txt");
	        fwrite = new BufferedWriter(fout);
	        fwrite.append("12");
	        fwrite.close();
	        fout = new FileWriter("testdir2" + File.separator + "test" + File.separator + "file3.txt");
	        fwrite = new BufferedWriter(fout);
	        fwrite.append("123");
	        fwrite.close();
	        FileWatcher fw = new FileWatcher("testdir2");
	        StringBuilder sb = new StringBuilder();
	        //build comparison string
	        sb.append(getIP() +"\n");
	        sb.append("+ file1.txt, 1\n");
	        sb.append("+ file2.txt, 2\n");
	        sb.append("+ test" + File.separator + "file3.txt, 3\n");
	        //AssertEquals
	        assertEquals(sb.toString(), fw.getAllFiles());
	        //Cleanup
	        fw.removeWatch();
	        new File("testdir2" + File.separator + "file1.txt").delete();
	        new File("testdir2" + File.separator + "file2.txt").delete();
	        new File("testdir2" + File.separator + "test" + File.separator + "file3.txt").delete();
	        file.delete();
	        new File("testdir2").delete();
    	}catch (Exception e) {
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
			e.printStackTrace(System.err);
			publicIP = "<Could-Not-Resolve-Public-IP-Address>";

		}
		return publicIP;
	}
    
}
