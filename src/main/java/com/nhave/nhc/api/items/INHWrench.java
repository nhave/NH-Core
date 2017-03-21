package com.nhave.nhc.api.items;

import net.minecraft.entity.player.EntityPlayer;

public interface INHWrench
{
	boolean canItemWrench(EntityPlayer player, int x, int y, int z);
	
	void onWrenchUsed(EntityPlayer player, int x, int y, int z);
}