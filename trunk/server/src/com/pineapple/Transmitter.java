package com.pineapple;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jcraft.jsch.*;

public class Transmitter
{
    private UpdateMessage message;
    private String serverName;
    private InetAddress serverAddress;
    
    public Transmitter()
    {
        try{
            serverAddress = InetAddress.getLocalHost();
            message = new UpdateMessage(serverAddress.getHostAddress());
        }catch (UnknownHostException e)
        {
            System.out.println("Unknown Host: " + e.getMessage());
        }
    }
    
    public void addFile(String fileName, long size)
    {
        message.add(fileName, size);
    }
    
    public void removeFile(String fileName){
        message.remove(fileName);
    }
    
    public void send(String user, String host, int port)
    {
        try{
            JSch scp = new JSch();
            Session session = scp.getSession(user, host, port);
            //May need to add userInfo here
            //UserInfo userInfo = new 
            //session.setUserInfo(userInfo);
            session.connect();
            //Used this from an example.  Probably not correct
            // exec 'scp -t rfile' remotely
            String command="scp -p -t updateFile";
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

        }catch(JSchException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    public String getMessage()
    {
        return message.toString();
    }

}
