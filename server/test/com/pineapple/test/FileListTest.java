package com.pineapple.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pineapple.FileList;
import com.pineapple.FileUpdate;

public class FileListTest
{
    private FileList fl;
    
    @Before
    public void setUp() throws Exception
    {
        fl = new FileList();
        fl.add(new FileUpdate("file1", 32, true));
        fl.add(new FileUpdate("file2", 51, true));
        fl.add(new FileUpdate("file3", 0, false));
        fl.add(new FileUpdate("file4", 231, false));
    }

    @Test
    public void testAdd()
    {
        fl.add(new FileUpdate("file5", 172, true));
        assertEquals(5, fl.size());
    }

    @Test
    public void testRemove()
    {
        assertTrue(fl.remove(new FileUpdate("file1")));
        assertEquals(3, fl.size());
        assertFalse(fl.remove(new FileUpdate("file5", 0, true)));
        assertEquals(3, fl.size());
    }

    @Test
    public void testToString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("+ file1, 32\n");
        sb.append("+ file2, 51\n");
        sb.append("- file3, 0\n");
        sb.append("- file4, 231\n");
        assertEquals(sb.toString(), fl.toString());
    }

}
