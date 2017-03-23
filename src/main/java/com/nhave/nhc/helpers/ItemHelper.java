package com.nhave.nhc.helpers;

import com.nhave.nhc.api.items.INHWrench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemHelper
{
	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 != null && stack2 != null)
		{
			if ((stack1.getItem() == stack2.getItem()) && (stack1.getItemDamage() == stack2.getItemDamage())) return true;
		}
		return false;
	}
	
	public static boolean isToolWrench(EntityPlayer player, ItemStack stack, int x, int y, int z)
	{
		return stack != null && stack.getItem() instanceof INHWrench && ((INHWrench)stack.getItem()).canItemWrench(player, x, y, z);
	}
	
	public static void useWrench(EntityPlayer player, ItemStack stack, int x, int y, int z)
	{
		if (stack != null && stack.getItem() instanceof INHWrench) ((INHWrench)stack.getItem()).onWrenchUsed(player, x, y, z);
	}
	
	public static void addItemToPlayer(EntityPlayer player, ItemStack stack)
	{
		if (!player.inventory.addItemStackToInventory(stack))
		{
			if (!player.world.isRemote) player.entityDropItem(stack, 1F);
		}
	}
	
	public static ItemStack getCurrentItemOrArmor(EntityPlayer player, int slot)
	{
		if (slot == 0) return player.getHeldItemMainhand();
		else if (slot == 1) return player.getHeldItemOffhand();
		else if (slot == 2) return player.inventory.armorItemInSlot(0);
		else if (slot == 3) return player.inventory.armorItemInSlot(1);
		else if (slot == 4) return player.inventory.armorItemInSlot(2);
		else if (slot == 5) return player.inventory.armorItemInSlot(3);
		else return null;
	}
}