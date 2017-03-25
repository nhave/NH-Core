package com.nhave.nhc.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ToolStationUpdateEvent extends Event
{
	public final ItemStack input;
	public final ItemStack mod;
	public ItemStack output;
	public int materialCost;

	public ToolStationUpdateEvent(ItemStack input, ItemStack mod)
	{
		this.input = input;
		this.mod = mod;
		this.materialCost = 0;
	}
}