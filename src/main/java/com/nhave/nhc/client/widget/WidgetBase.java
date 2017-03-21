package com.nhave.nhc.client.widget;

import net.minecraftforge.client.event.RenderTooltipEvent;

public abstract class WidgetBase
{
	public abstract int getSizeX(RenderTooltipEvent.PostText event);
	
	public abstract int getSizeY(RenderTooltipEvent.PostText event);
	
	public abstract void drawWidget(RenderTooltipEvent.PostText event, int x, int y);
	
	public abstract boolean shouldDraw(RenderTooltipEvent.PostText event);
}