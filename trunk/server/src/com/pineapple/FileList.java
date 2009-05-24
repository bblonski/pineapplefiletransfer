package com.pineapple;

import java.util.ArrayList;

public class FileList
{
    private ArrayList<FileUpdate> list;
    
    public FileList()
    {
        list = new ArrayList<FileUpdate>();
    }
    
    public int add(FileUpdate file)
    {
        list.add(file);
        return list.size();
    }
    
    public boolean remove(FileUpdate file)
    {
        return list.remove(file);
    }
    
    public int size()
    {
        return list.size();
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(FileUpdate fu: list)
        {
            sb.append(fu.toString());
            /*I think we can do Unix only newlines here because we open the file
            on Unix machines only.*/
            sb.append("\n");
        }
        return sb.toString();
    }

}
