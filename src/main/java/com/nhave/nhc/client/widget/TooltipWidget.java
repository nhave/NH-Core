package com.nhave.nhc.client.widget;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TooltipWidget
{
	private static final ArrayList<WidgetBase> WIDGETS = new ArrayList<WidgetBase>();
	
	@SubscribeEvent
	public void renderTooltip(RenderTooltipEvent.PostText event)
	{
		if(event.getStack() != null)
		{
			int currentX = event.getX() - 4;
			int currentY = event.getY() - 4;
			int texWidth = 0;
			
			boolean draw = false;
			for (int i = 0; i < WIDGETS.size(); ++i)
			{
				if (WIDGETS.get(i).shouldDraw(event.getStack()))
				{
					draw = true;
					texWidth = Math.max(texWidth, WIDGETS.get(i).getSizeX(event.getStack()));
					currentY -= (WIDGETS.get(i).getSizeY(event.getStack()) + 2);
				}
			}
			if (!draw) return;
			
			if(currentY < 0) currentY = event.getY() + event.getLines().size() * 10 + 5;
			
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
			int right = currentX + texWidth;
			if(right > res.getScaledWidth()) currentX -= (right - res.getScaledWidth());
			
			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.translate(0, 0, 700);
			
			for (int i = 0; i < WIDGETS.size(); ++i)
			{
				if (WIDGETS.get(i).shouldDraw(event.getStack()))
				{
					WIDGETS.get(i).drawWidget(event.getStack(), currentX, currentY);
					currentY += (WIDGETS.get(i).getSizeY(event.getStack()) + 2);
				}
			}
			
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		}
	}
	
	/**
	 * Registers a new Widget to be rendered on top of an Item Tooltip
	 * 
	 * @param widget new instance of a tooltip Widget
	 */
	public static void register(WidgetBase widget)
	{
		WIDGETS.add(widget);
	}
	
	public static ArrayList<WidgetBase> getWidgets()
	{
		return WIDGETS;		
	}
}