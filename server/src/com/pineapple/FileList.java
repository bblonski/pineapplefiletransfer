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

}
