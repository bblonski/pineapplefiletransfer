package com.pineapple;

public class FileUpdate
{
    private String fileName;
    
    private int fileSize;
    
    private boolean exists;
    
    public FileUpdate(String fileName)
    {
        this(fileName, 0, false);
    }
    
    public FileUpdate(String fileName, int fileSize, boolean exists)
    {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.exists = exists;
    }
    
    public String getFileName()
    {
        return this.fileName;
    }
    
    public int getFileSize()
    {
        return this.fileSize;
    }
    
    public boolean exists()
    {
        return this.exists;
    }
    
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
