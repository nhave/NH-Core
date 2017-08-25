package com.nhave.nhc.api.blocks;

public interface ILockableTile
{
	public boolean hasOwner();
	
	public String getOwner();

	public void setOwner(String owner);
	
	public boolean isPublic();
	
	public void setPublic();
}