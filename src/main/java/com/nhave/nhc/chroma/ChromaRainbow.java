package com.nhave.nhc.chroma;

import com.nhave.nhc.api.client.ITickingData;

public class ChromaRainbow extends Chroma implements ITickingData
{
	private int curTick = 0;
	private int[] colorCodes = new int[] {16711680, 16727040, 16752896, 16318208, 9699072, 3014400, 65280, 65437, 64767, 38911, 13055, 3408127, 10027263, 16646399, 16711835, 16711734};
	
	public ChromaRainbow()
	{
		super(0);
	}
	
	@Override
	public int getColor()
	{
		if (this.curTick < 0 || this.curTick >= colorCodes.length) this.curTick = 0;
		return colorCodes[this.curTick];
	}
	
	@Override
	public void onTick()
	{
		++this.curTick;
		if (this.curTick < 0 || this.curTick >= colorCodes.length) this.curTick = 0;
	}
}