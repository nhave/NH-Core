package com.nhave.nhc.chroma;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Chroma
{
	private int color;
	
	public Chroma(int color)
	{
		this.color = color;
	}

	@SideOnly(Side.CLIENT)
	public int getColor()
	{
		return this.color;
	}
	
	public String getQualityColor()
	{
		return "";
	}
}