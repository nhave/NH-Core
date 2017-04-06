package com.nhave.nhc.items;

import com.nhave.nhc.api.items.INHWrench;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase implements INHWrench
{
	public ItemWrench(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if (hand == EnumHand.OFF_HAND) return EnumActionResult.PASS;
		final IBlockState blockState = world.getBlockState(pos);
	    IBlockState bs = blockState;
	    Block block = bs.getBlock();
	    
		if (!player.isSneaking() && block.rotateBlock(world, pos, side))
	    {
	    	if (!world.isRemote)
			{
				return EnumActionResult.SUCCESS;
			}
			else
			{
				SoundEvent soundName = block.getSoundType(blockState, world, pos, player).getPlaceSound();
				player.playSound(soundName, 1.0F, 0.6F);
				player.swingArm(EnumHand.MAIN_HAND);
			}
	    }
	    
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return true;
	}

	@Override
	public boolean canItemWrench(EntityPlayer player, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void onWrenchUsed(EntityPlayer player, int x, int y, int z)
	{
		player.swingArm(EnumHand.MAIN_HAND);
	}
}
