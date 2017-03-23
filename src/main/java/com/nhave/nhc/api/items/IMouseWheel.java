package com.nhave.nhc.api.items;

import com.nhave.nhc.network.Key;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IMouseWheel
{
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key, boolean chat);
}