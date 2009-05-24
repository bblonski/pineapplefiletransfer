package com.pineapple.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pineapple.FileUpdate;

public class FileUpdateTest
{
    private final String filename = "myFileName";
    private int size = 32;
    private FileUpdate fd;
    
    @Before
    public void setup()
    {
        fd = new FileUpdate(filename, size, true);
    }

    @Test
    public void testGetFileName()
    {
        assertEquals(fd.getFileName(), "myFileName");
    }

    @Test
    public void testGetFileSize()
    {
        assertEquals(fd.getFileSize(), 32);
    }

    @Test
    public void testExists()
    {
        assertEquals(fd.exists(), true);
        fd = new FileUpdate(filename, 32, false);
        assertEquals(fd.exists(), false);
    }

    @Test
    public void testEqualsObject()
    {
        FileUpdate fd2 = new FileUpdate(filename, size, true);
        assertEquals(fd, fd2);
        fd2 = new FileUpdate(filename, size, false);
        assertEquals(fd, fd2);
        fd2 = new FileUpdate(filename, 0, false);
        assertEquals(fd, fd2);
        fd2 = new FileUpdate("noMyFilename",32, true);
        assertFalse(fd.equals(fd2));
    }

    @Test
    public void testToString()
    {
        assertEquals(fd.toString(), "+ myFileName, 32");
        fd = new FileUpdate(filename, size, false);
        assertEquals(fd.toString(), "- myFileName, 32");
    }

}
