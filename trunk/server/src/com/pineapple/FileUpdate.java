package com.pineapple;

public class FileUpdate
{
    private String fileName;
    
    private long fileSize;
    
    private boolean exists;
    
    public FileUpdate(String fileName)
    {
        this(fileName, 0, false);
    }
    
    public FileUpdate(String fileName, long size, boolean exists)
    {
        this.fileName = fileName;
        this.fileSize = size;
        this.exists = exists;
    }
    
    public String getFileName()
    {
        return this.fileName;
    }
    
    public long getFileSize()
    {
        return this.fileSize;
    }
    
    public boolean exists()
    {
        return this.exists;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof FileUpdate)
        {
            FileUpdate obj = (FileUpdate)o;
            if(obj.getFileName().equals(this.fileName))
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(exists)
        {
            sb.append("+ ");
        }else
        {
            sb.append("- ");
        }
        sb.append(fileName);
        sb.append(", ");
        sb.append(fileSize);
        return sb.toString();
    }

}
