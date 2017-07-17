package com.nhave.nhc.tiles;

import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityDisplay extends TileEntityMachine implements ITickable
{
	private ItemStack item = null;
	private String owner = "";
	public float itemRotaion = 0;

	@Override
	public void update()
	{
		if (getWorld().isRemote)
		{
			this.itemRotaion += 2F;
			if (this.itemRotaion < 0 || this.itemRotaion >= 360) this.itemRotaion = 0;
		}
	}

	@Override
	public boolean onTileActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		if ((this.item == null || this.item.getItem() == Item.getItemFromBlock(Blocks.AIR)) && !player.getHeldItemMainhand().isEmpty())
		{
			this.item = player.getHeldItemMainhand().copy();
			this.item.setCount(1);
			player.getHeldItemMainhand().shrink(1);
			sync();
			return true;
		}
		else if (this.item != null)
		{
			ItemHelper.addItemToPlayer(player, item.copy());
			this.item = null;
			sync();
			return true;
		}
		return false;
	}
	
	public ItemStack getItemStack()
	{
		return this.item;
	}
	
	public void clearItemStack()
	{
		this.item = null;
		sync();
	}
	
	public void writeSyncableDataToNBT(NBTTagCompound tag)
	{
		super.writeSyncableDataToNBT(tag);
		NBTTagList tagList = new NBTTagList();
		int i = 0;
		if(this.item != null)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			
			tag1.setByte("Slot", (byte)i);
			this.item.writeToNBT(tag1);
			
			tagList.appendTag(tag1);
		}
		tag.setTag("ITEM", tagList);
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound tag)
	{
		super.readSyncableDataFromNBT(tag);
		NBTTagList items = tag.getTagList("ITEM", tag.getId());

		int i = 0;
		NBTTagCompound item = items.getCompoundTagAt(i);
        int j = item.getByte("Slot");
        ItemStack stack = new ItemStack(item);
        
        this.item = stack.copy();
	}
}