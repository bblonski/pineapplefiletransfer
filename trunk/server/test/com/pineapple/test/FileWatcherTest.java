package com.pineapple.test;

import static org.junit.Assert.*;

import java.io.*;
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
            testdir.mkdir();
            myFileWatcher = new FileWatcher("testdir");
        }catch(Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception
    {
        try{
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
        try{

            File temp = new File("testdir/test/test2.txt");
            temp.mkdirs();
            temp.createNewFile();
            temp.delete();
            new File("testdir/test").delete();
            Thread.sleep(500);
        }catch (Exception e)
        {
            fail(e.getMessage());
        }
        fail("Must manually verify for now");
    }

}
