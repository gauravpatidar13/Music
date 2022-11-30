package com.my;

public class VideoFile
{
	String id,path,name,duration,title,size,dateAdded;

	public VideoFile(String id, String path, String name, String duration, String title, String size, String dateAdded)
	{
		this.id = id;
		this.path = path;
		this.name = name;
		this.duration = duration;
		this.title = title;
		this.size = size;
		this.dateAdded = dateAdded;
	}
	public VideoFile()
	{}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getPath()
	{
		return path;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getSize()
	{
		return size;
	}

	public void setDateAdded(String dateAdded)
	{
		this.dateAdded = dateAdded;
	}

	public String getDateAdded()
	{
		return dateAdded;
	}
}