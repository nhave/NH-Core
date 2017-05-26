package com.nhave.nhc.helpers;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.api.items.INHWrench;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
		if (!player.world.isRemote && !player.inventory.addItemStackToInventory(stack))
		{
			player.entityDropItem(stack, 1F);
		}
	}
	
	/**
	 * Gets the players current item or armor
	 * 0. Mainhand Item
	 * 1. Offhand Item
	 * 2. Boots
	 * 3. Leggings
	 * 4. Chest
	 * 5. Helmet
	 * 
	 * @param player
	 * @param slot
	 */
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
	
	public static void dismantleBlock(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		Block block = blockState.getBlock();
    	List drops = block.getDrops(world, blockPos, blockState, 0);
    	block.onBlockHarvested(world, blockPos, blockState, player);
	    world.setBlockToAir(blockPos);
        
        if (!world.isRemote)
        {
        	ArrayList<? extends ItemStack> items = (ArrayList<? extends ItemStack>) drops;
        	for (ItemStack stack : items)
        	{
        		dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
            }
        }
	}

	public static void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
	{
		float f = 0.3F;
    	double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	EntityItem theItem = new EntityItem(world, x + x2, y + y2, z + z2, stack);
    	theItem.setDefaultPickupDelay();
    	world.spawnEntity(theItem);
	}
}