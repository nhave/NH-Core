package com.nhave.nhc.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMachine extends TileEntity
{
	private String owner = "";
	
	public boolean onTileActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return false;
	}
	
	public boolean hasOwner()
	{
		return (this.owner != null && this.owner.length() > 0);
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(String owner)
	{
		if (owner == null) owner = "";
		this.owner = owner;
		sync();
	}
	
	protected void sync()
	{
		world.notifyBlockUpdate(this.pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		markDirty();
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
		tag.setString("OWNER", this.owner);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readSyncableDataFromNBT(tag);
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound tag)
	{
		NBTTagList items = tag.getTagList("ITEM", tag.getId());

		this.owner = tag.getString("OWNER");
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