package com.pineapple.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pineapple.UpdateMessage;

public class UpdateMessageTest {
    private UpdateMessage msg;
    private final String ip = "123.456.789:1234";
    
    @Before
    public void setUp() throws Exception {
        msg = new UpdateMessage(ip);
        msg.add("file1", 13);
    }
    
    @Test
    public void testAdd() {
        msg.add("file 2", 137);
        assertEquals(ip + "\n+ file1 13\n+ file 2 137\n", msg.toString());
    }
    
    @Test
    public void testRemove() {
        msg.remove("file1");
        assertEquals(ip + "\n- file1 0\n", msg.toString());
    }
    
    @Test
    public void testMessage() {
        msg.add("file 2", 137);
        msg.add("file3", 579);
        msg.remove("file4");
        msg.remove("file 2");
        assertEquals(ip
                + "\n+ file1 13\n+ file3 579\n- file4 0\n- file 2 0\n", msg
                .toString());
    }
    
    @Test
    public void testToString() {
        assertEquals(ip + "\n+ file1 13\n", msg.toString());
    }
    
}
