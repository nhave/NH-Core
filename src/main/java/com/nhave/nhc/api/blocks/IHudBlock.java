package com.nhave.nhc.api.blocks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHudBlock
{
	@SideOnly(Side.CLIENT)
	public void addHudInfo(World world, BlockPos pos, IBlockState state, List list);
}