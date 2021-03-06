package com.nhave.nhc.tiles;

import com.nhave.nhc.events.ToolStationCraftingEvent;
import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityToolStation extends TileEntityMachine
{
	private ItemStack item = null;

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
			ToolStationUpdateEvent evt = new ToolStationUpdateEvent(item, player.getHeldItemMainhand());
			MinecraftForge.EVENT_BUS.post(evt);
			if (evt.isCanceled()) return false;
			if (evt.output != null && player.getHeldItemMainhand().getItem() == evt.mod.getItem() && player.getHeldItemMainhand().getItemDamage() == evt.mod.getItemDamage())
			{
				ItemStack mod = player.getHeldItemMainhand().copy();
				ItemStack input = this.item.copy();
				if (evt.materialCost > 0)
				{
					if (evt.materialCost > player.getHeldItemMainhand().getCount()) return false;
					else player.getHeldItemMainhand().shrink(evt.materialCost);
				}
				this.item = evt.output.copy();
				sync();
				MinecraftForge.EVENT_BUS.post(new ToolStationCraftingEvent(player, evt.output.copy(), input, mod));
				return true;
			}
			else
			{
				ItemHelper.addItemToPlayer(player, item.copy());
				this.item = null;
				sync();
				return true;
			}
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