package com.pineapple;

public class UpdateMessage implements IMessage
{
    private String header;
    private FileList list;
    
    public UpdateMessage(String header)
    {
        this.list = new FileList();
        this.header = header;
    }
    
    public void add(String fileName, long size)
    {
        list.remove(new FileUpdate(fileName));
        this.list.add(new FileUpdate(fileName, size, true));
    }
    
    public void remove(String fileName)
    {
        list.remove(new FileUpdate(fileName));
        this.list.add(new FileUpdate(fileName, 0, false));
    }
    
    @Override
    public String toString()
    {
        return header + "\n" + list.toString();
    }
}
