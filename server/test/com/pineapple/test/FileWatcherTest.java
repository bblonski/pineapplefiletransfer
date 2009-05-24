package com.pineapple.test;

import static org.junit.Assert.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pineapple.FileWatcher;

public class FileWatcherTest
{

    private FileWatcher myFileWatcher;
    private File testdir;
    
    @Before
    public void setUp() throws Exception
    {
        try{
            testdir = new File("testdir");
            if(testdir.exists())
            {
                testdir.delete();
            }
            testdir.mkdir();
            myFileWatcher = new FileWatcher("testdir");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception
    {
        try{
            Thread.sleep(500);
            myFileWatcher.removeWatch();
            testdir.delete();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFileWatcher()
    {
        File temp = new File("testdir\\test\\");
        String host = null;
        try{
            temp.mkdirs();
            FileWriter fout = new FileWriter("testdir\\test\\test2.txt");
            BufferedWriter fwrite = new BufferedWriter(fout);
            fwrite.append("asdf1234");
            fwrite.close();
            host = InetAddress.getLocalHost().getHostAddress();
            assertEquals(host +"\n+ test\\test2.txt, 8\n", 
                    myFileWatcher.getMessage());
            new File("testdir\\test\\test2.txt").delete();
            assertEquals(host + "\n- test\\test2.txt, 0\n",
                    myFileWatcher.getMessage());
            temp.delete();
        }catch (Exception e)
        {
            fail(e.getMessage());
        }
        
    }
    
    /**
     * There are apparently some race conditions here.
     */
    @Test
    public void testGetMessage()
    {
        try
        {
            FileWriter fout = new FileWriter("testdir\\file1.txt");
            BufferedWriter fwrite = new BufferedWriter(fout);
            fwrite.append("123415asdfasdf");
            fwrite.close();
            
            fout = new FileWriter("testdir\\file2.txt");
            fwrite = new BufferedWriter(fout);
            fwrite.append("asdf");
            fwrite.close();
            assertEquals("10.0.0.2" +"\n+ file1.txt, 14\n+ file2.txt, 4\n",
                    myFileWatcher.getMessage());
            new File("testdir\\file1.txt").delete();
            new File("testdir\\file2.txt").delete();
        }catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

}
