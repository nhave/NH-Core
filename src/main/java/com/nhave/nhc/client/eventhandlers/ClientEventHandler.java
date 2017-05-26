package com.nhave.nhc.client.eventhandlers;

import com.nhave.nhc.api.items.IItemQuality;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void makeTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();
		if(stack != null && stack.getItem() instanceof IItemQuality)
		{
			event.getToolTip().set(0, ((IItemQuality) stack.getItem()).getQualityColor(stack) + stack.getDisplayName());
		}
	}
}