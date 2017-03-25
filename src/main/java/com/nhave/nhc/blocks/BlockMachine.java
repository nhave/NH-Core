package com.nhave.nhc.blocks;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachine extends BlockBase
{
	protected boolean canRotate;
	
	public BlockMachine(Material materialIn, String name, boolean canRotate)
	{
		super(name, materialIn);
		this.setHardness(50F);
		this.canRotate = canRotate;
		this.setCreativeTab(NHCore.CREATIVETAB);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			ItemStack stack = playerIn.getHeldItemMainhand();
			if (doBlockActivate(worldIn, pos, state, playerIn, hand, stack, facing, hitX, hitY, hitZ)) return true;
			else if (!playerIn.getHeldItemMainhand().isEmpty() && ItemHelper.isToolWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ()))
			{
				if (playerIn.isSneaking())
				{
					dismantleBlock(worldIn, pos, state, playerIn);;
					playerIn.swingArm(EnumHand.MAIN_HAND);
					ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
					return !worldIn.isRemote;
				}
				else if (this.canRotate)
				{
					doBlockRotation(worldIn, pos, state, playerIn, hand, stack, facing, hitX, hitY, hitZ);
				}
			}
		}
		return false;
	}
	
	public boolean doBlockActivate(World world, BlockPos blockPos,	IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing enumFacing, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	public boolean doBlockRotation(World world, BlockPos blockPos,	IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing enumFacing, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	public void dismantleBlock(World world, BlockPos blockPos,	IBlockState blockState, EntityPlayer player)
	{
		Block block = blockState.getBlock();
		//int metadata = world.getBlockMetadata(x, y, z);
    	List drops = block.getDrops(world, blockPos, blockState, 0);
    	block.onBlockHarvested(world, blockPos, blockState, player);;
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

	public void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
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