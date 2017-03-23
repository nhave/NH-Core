package com.nhave.nhc.items;

import java.util.List;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.Reference;
import com.nhave.nhc.api.items.IHudItem;
import com.nhave.nhc.api.items.IInventoryItemAdvanced;
import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.api.items.IMouseWheel;
import com.nhave.nhc.network.Key;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

public class ItemDebug extends Item implements IInventoryItemAdvanced, IKeyBound, IMouseWheel, IHudItem
{
	public ItemDebug(String name)
	{
		this.setRegistryName(name);
		this.setCreativeTab(NHCore.CREATIVETAB);
		this.setUnlocalizedName(Reference.MODID + "." + name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public int getInventoryX(ItemStack stack)
	{
		return 2;
	}
	
	@Override
	public int getInventoryY(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(ItemStack stack, int slot)
	{
		if (slot == 0) return new ItemStack(Items.COMPASS, Math.min(64, Math.max(1, getCountInSlot(stack, slot))));
		if (slot == 1) return new ItemStack(Items.CLOCK, Math.min(64, Math.max(1, getCountInSlot(stack, slot))));
		return null;
	}
	
	@Override
	public int getCountInSlot(ItemStack stack, int slot)
	{
		return 1;
	}
	
	@Override
	public boolean renderStackOverlay(ItemStack stack, int slot)
	{
		return false;
	}

	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key, boolean chat)
	{
		if (chat) entityPlayer.sendMessage(new TextComponentString(key.name()));
	}

	@Override
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list, boolean isArmor)
	{
		list.add("TEST");
		list.add(stack.getDisplayName());
	}
}