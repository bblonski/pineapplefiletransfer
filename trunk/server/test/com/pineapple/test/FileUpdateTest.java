package com.pineapple.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pineapple.FileUpdate;

public class FileUpdateTest {
    private final String filename = "myFileName";
    private int size = 32;
    private FileUpdate fd;
    
    @Before
    public void setup() {
        fd = new FileUpdate(filename, size, true);
    }
    
    @Test
    public void testGetFileName() {
        assertEquals("myFileName", fd.getFileName());
    }
    
    @Test
    public void testGetFileSize() {
        assertEquals(32, fd.getFileSize());
    }
    
    @Test
    public void testGetStatus() {
        assertEquals(fd.getStatus(), true);
        fd = new FileUpdate(filename, 32, false);
        assertEquals(false, fd.getStatus());
    }
    
    @Test
    public void testEqualsObject() {
        FileUpdate fd2 = new FileUpdate(filename, size, true);
        assertEquals(fd, fd2);
        fd2 = new FileUpdate(filename, size, false);
        assertEquals(fd, fd2);
        fd2 = new FileUpdate(filename);
        assertEquals(fd, fd2);
        fd2 = new FileUpdate("noMyFilename", 32, true);
        assertFalse(fd.equals(fd2));
    }
    
    @Test
    public void testToString() {
        assertEquals("+ myFileName, 32", fd.toString());
        fd = new FileUpdate(filename, size, false);
        assertEquals("- myFileName, 32", fd.toString());
    }
    
}
