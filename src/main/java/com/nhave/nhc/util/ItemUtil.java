package com.nhave.nhc.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtil
{
	public static ItemStack getItemFromStack(ItemStack stack, String compound)
	{
		String key = "ITEM";
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return null;
        }
        else
        {
        	NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);

            if (nbttagcompound1 == null)
            {
                return null;
            }
            else
            {
                NBTTagCompound nbttagcompound2 = nbttagcompound1.getCompoundTag(key);
                return nbttagcompound2 == null ? null : (nbttagcompound1.hasKey(key) ? new ItemStack(nbttagcompound2) : null);
            }
        }
	}
	
	public static void addItemToStack(ItemStack stack, ItemStack subItem, String compound)
	{
		String key = "ITEM";
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);

        if (!nbttagcompound.hasKey(compound))
        {
            nbttagcompound.setTag(compound, nbttagcompound1);
        }

        NBTTagCompound nbttagcompound2 = nbttagcompound1.getCompoundTag(key);

        if (!nbttagcompound1.hasKey(key))
        {
        	nbttagcompound1.setTag(key, nbttagcompound2);
        }
        
        subItem.writeToNBT(nbttagcompound2);
    }
	
	public static void removeItemFromStack(ItemStack stack, String compound)
	{
		if (stack.getTagCompound() != null) stack.getTagCompound().removeTag(compound);
	}
}