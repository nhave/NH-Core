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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityToolStation extends TileEntity
{
	private ItemStack item = null;
	
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
	
	private void sync()
	{
		world.notifyBlockUpdate(this.pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		markDirty();
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
	
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeSyncableDataToNBT(tag);
		return tag;
	}

	public void writeSyncableDataToNBT(NBTTagCompound tag)
	{
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
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readSyncableDataFromNBT(tag);
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound tag)
	{
		NBTTagList items = tag.getTagList("ITEM", tag.getId());

		int i = 0;
		NBTTagCompound item = items.getCompoundTagAt(i);
        int j = item.getByte("Slot");
        ItemStack stack = new ItemStack(item);
        
        this.item = stack.copy();
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound p_handleUpdateTag_1_)
	{
		readSyncableDataFromNBT(p_handleUpdateTag_1_);
		super.handleUpdateTag(p_handleUpdateTag_1_);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeSyncableDataToNBT(syncData);
		return new SPacketUpdateTileEntity(this.pos, 3, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}
}