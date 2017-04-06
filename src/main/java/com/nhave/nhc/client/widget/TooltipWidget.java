package com.nhave.nhc.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.api.items.IWidgetControl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TooltipWidget
{
	private static final List<WidgetBase> WIDGETS = new ArrayList<WidgetBase>();
	
	@SubscribeEvent
	public void renderTooltip(RenderTooltipEvent.PostText event)
	{
		if(event.getStack() != null)
		{
			List<WidgetBase> list = WIDGETS;
			if (event.getStack().getItem() instanceof IWidgetControl)
			{
				list = new ArrayList<WidgetBase>();
				((IWidgetControl) event.getStack().getItem()).addWidgets(event.getStack(), list);
			}
			
			int currentX = event.getX() - 4;
			int currentY = event.getY() - 4;
			int texWidth = 0;
			
			boolean draw = false;
			for (int i = 0; i < list.size(); ++i)
			{
				if (list.get(i).shouldDraw(event.getStack()))
				{
					draw = true;
					texWidth = Math.max(texWidth, list.get(i).getSizeX(event.getStack()));
					currentY -= (list.get(i).getSizeY(event.getStack()) + 2);
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
			
			for (int i = 0; i < list.size(); ++i)
			{
				if (list.get(i).shouldDraw(event.getStack()))
				{
					list.get(i).drawWidget(event.getStack(), currentX, currentY);
					currentY += (list.get(i).getSizeY(event.getStack()) + 2);
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
	
	public static List<WidgetBase> getWidgets()
	{
		return WIDGETS;		
	}
}