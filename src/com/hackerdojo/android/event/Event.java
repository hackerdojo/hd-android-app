package com.hackerdojo.android.event;

import java.util.Date;

import android.util.Log;

public class Event implements Comparable<Event> {
	private Date startDate;
	private Date endDate;
	private String title;
	private String host;
	private String location;
	private String description; //add extended description for SubEvent Activity...
	private int size;
	private String id;
	public Date getStartDate() 
	{
		return startDate;
	}
	
	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public String getHost() 
	{
		return host;
	}
	public void setHost(String host) 
	{
		this.host = host;
	}
	public String getLocation() 
	{
		return location;
	}
	public void setLocation(String location) 
	{
		this.location = location;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	public int compareTo(Event another) 
	{
		Event anotherEvent = (Event) another;
		int comp = this.getStartDate().compareTo(anotherEvent.getStartDate());
		if(comp == 0) 
		{
			comp = this.title.compareTo(anotherEvent.title);
		}
		return comp;
	}
	public int getSize() 
	{
		return size;
	}
	public void setSize(int size) 
	{
		this.size = size;
	}


	public String getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = Integer.toString(id);
	}


}
