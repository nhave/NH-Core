package com.nhave.nhc.api.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHudItem
{
	@SideOnly(Side.CLIENT)
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list, boolean isArmor);
}