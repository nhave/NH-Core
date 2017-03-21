package com.nhave.nhc.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemNBTHelper
{
	public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack)
	{
		NBTTagCompound ret = itemStack.getTagCompound();
	    
    	if (ret == null)
    	{
	    	ret = new NBTTagCompound();
	    	
	    	itemStack.setTagCompound(ret);
	    }
	    
	    return ret;
	}
	
	public static boolean hasTag(ItemStack itemStack, String tagName)
	{
		if (itemStack.hasTagCompound())
		{
			return itemStack.getTagCompound().hasKey(tagName);
	    }
	    return false;
	}

	public static void removeTag(ItemStack itemStack, String tagName)
	{
		if (itemStack.hasTagCompound())
			itemStack.getTagCompound().removeTag(tagName);
	}
	
	/**
     * Gets an Integer from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The Integer from the tag on the stack,
     * if null returns -1
     */
	public static int getInteger(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return -1;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? -1 : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getInteger(key) : -1);
        }
    }
	
	/**
     * Gets an IntArray from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The IntArray from the tag on the stack,
     * if null returns null
     */
	public static int[] getIntArray(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return null;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? null : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getIntArray(key) : null);
        }
    }
	
	/**
     * Gets an Integer from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param def The default int to return
     * 
     * @return The Integer from the tag on the stack,
     * if null returns -1
     */
	public static int getInteger(ItemStack stack, String compound, String key, int def)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return def;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? def : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getInteger(key) : def);
        }
    }
	
	/**
     * Gets a Double from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The Double from the tag on the stack,
     * if null returns -1
     */
	public static double getDouble(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return -1;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? -1 : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getDouble(key) : -1);
        }
    }
	
	/**
     * Gets a Boolean from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The Boolean from the tag on the stack,
     * if null returns false
     */
	public static boolean getBoolean(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return false;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? false : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getBoolean(key) : false);
        }
    }
	
	/**
     * Gets a String from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The String from the tag on the stack,
     * if null returns null
     */
	public static String getString(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return null;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? null : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getString(key) : null);
        }
    }
	
	/**
     * Gets a String from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The String from the tag on the stack,
     * if null returns null
     */
	public static String getString(ItemStack stack, String compound, String key, String def)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return def;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? def : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getString(key) : def);
        }
    }
	
	/**
     * Gets a Float from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The Float from the tag on the stack,
     * if null returns 0.0F
     */
	public static float getFloat(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return 0.0F;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? 0.0F : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getFloat(key) : 0.0F);
        }
    }
	
	/**
     * Gets a Byte from the NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * 
     * @return The Float from the tag on the stack,
     * if null returns 0.0F
     */
	public static byte getByte(ItemStack stack, String compound, String key)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return -1;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag(compound);
            return nbttagcompound1 == null ? -1 : (nbttagcompound1.hasKey(key) ? nbttagcompound1.getByte(key) : -1);
        }
    }
	
	/**
     * Sets an Integer to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param number The number to set
     */
  	public static void setInteger(ItemStack stack, String compound, String key, int number)
    {
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

        nbttagcompound1.setInteger(key, number);
    }
	
	/**
     * Sets an IntArray to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param array The array to set
     */
  	public static void setIntArray(ItemStack stack, String compound, String key, int[] array)
    {
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

        nbttagcompound1.setIntArray(key, array);
    }
	
	/**
     * Sets an Double to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param number The Double to set
	 * @return 
     */
  	public static void setDouble(ItemStack stack, String compound, String key, double number)
    {
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

        nbttagcompound1.setDouble(key, number);
    }
	
	/**
     * Sets a Boolean to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param value The value to set
	 * @return 
     */
  	public static void setBoolean(ItemStack stack, String compound, String key, boolean value)
    {
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

        nbttagcompound1.setBoolean(key, value);
    }
	
	/**
     * Sets a String to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param string The string to set
	 * @return 
     */
  	public static void setString(ItemStack stack, String compound, String key, String string)
    {
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

        nbttagcompound1.setString(key, string);
    }
	
	/**
     * Sets a Float to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param number The float to set
	 * @return 
     */
  	public static void setFloat(ItemStack stack, String compound, String key, float number)
    {
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

        nbttagcompound1.setFloat(key, number);
    }
	
	/**
     * Sets a Byte to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param param The Byte to set
	 * @return 
     */
  	public static void setByte(ItemStack stack, String compound, String key, byte param)
    {
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

        nbttagcompound1.setByte(key, param);
    }
	
	/**
     * Sets an Integer to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param number The number to set
     */
  	public static ItemStack setStackInteger(ItemStack stack, String compound, String key, int number)
    {
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

        nbttagcompound1.setInteger(key, number);
        return stack;
    }
	
	/**
     * Sets an Double to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param number The Double to set
	 * @return 
     */
  	public static ItemStack setStackDouble(ItemStack stack, String compound, String key, double number)
    {
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

        nbttagcompound1.setDouble(key, number);
        return stack;
    }
	
	/**
     * Sets a Boolean to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param value The value to set
	 * @return 
     */
  	public static ItemStack setStackBoolean(ItemStack stack, String compound, String key, boolean value)
    {
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

        nbttagcompound1.setBoolean(key, value);
        return stack;
    }
	
	/**
     * Sets a String to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param string The string to set
	 * @return 
     */
  	public static ItemStack setStackString(ItemStack stack, String compound, String key, String string)
    {
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

        nbttagcompound1.setString(key, string);
        return stack;
    }
	
	/**
     * Sets a Float to an NBT tag
     * @param stack The Itemstack
     * @param compound The tag compund to read from
     * @param key The key to read
     * @param number The float to set
	 * @return 
     */
  	public static ItemStack setStackFloat(ItemStack stack, String compound, String key, float number)
    {
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

        nbttagcompound1.setFloat(key, number);
        return stack;
    }
}