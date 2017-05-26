package com.nhave.nhc.api.items;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IToolStationHud
{
	@SideOnly(Side.CLIENT)
	public void addToolStationInfo(ItemStack stack, List list);
}