package com.nhave.nhc.blocks;

import com.nhave.nhc.tiles.TileEntityToolStation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToolStation extends BlockMachine
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockToolStation(String name)
	{
		super(Material.IRON, name, true);
	}
	
	public void onBlockAdded(World world, BlockPos blockPos, IBlockState blockState)
	{
	    setDefaultFacing(world, blockPos, blockState);
	}
	
	private void setDefaultFacing(World world, BlockPos blockPos, IBlockState blockState)
	{
    	if (world.isRemote)
    	{
    		return;
	    }
	    IBlockState faceNorth = world.getBlockState(blockPos.north());
	    IBlockState faceSouth = world.getBlockState(blockPos.south());
	    IBlockState faceWest = world.getBlockState(blockPos.west());
	    IBlockState faceEast = world.getBlockState(blockPos.east());
	    
	    EnumFacing defaultFace = (EnumFacing)blockState.getValue(FACING);
	    if ((defaultFace == EnumFacing.NORTH) && (faceNorth.isFullBlock()) && (!faceSouth.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.SOUTH;
	    }
	    else if ((defaultFace == EnumFacing.SOUTH) && (faceSouth.isFullBlock()) && (!faceNorth.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.NORTH;
	    }
	    else if ((defaultFace == EnumFacing.WEST) && (faceWest.isFullBlock()) && (!faceEast.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.EAST;
	    }
	    else if ((defaultFace == EnumFacing.EAST) && (faceEast.isFullBlock()) && (!faceWest.isFullBlock()))
	    {
	    	defaultFace = EnumFacing.WEST;
	    }
	    world.setBlockState(blockPos, blockState.withProperty(FACING, defaultFace), 2);
	}
	
	public IBlockState onBlockPlaced(World world, BlockPos blockPos, EnumFacing enumFacing, float x, float y, float z, int side, EntityLivingBase entityLiving)
	{
		return getDefaultState().withProperty(FACING, entityLiving.getHorizontalFacing().getOpposite());
	}
	
	public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase entityLiving, ItemStack stack)
	{
		world.setBlockState(blockPos, blockState.withProperty(FACING, entityLiving.getHorizontalFacing().getOpposite()), 2);
	}
	
	public IBlockState getStateFromMeta(int p_getStateFromMeta_1_)
	{
	    EnumFacing lvt_2_1_ = EnumFacing.getFront(p_getStateFromMeta_1_);
	    if (lvt_2_1_.getAxis() == EnumFacing.Axis.Y)
	    {
	    	lvt_2_1_ = EnumFacing.NORTH;
	    }
	    return getDefaultState().withProperty(FACING, lvt_2_1_);
	}
	
	public int getMetaFromState(IBlockState p_getMetaFromState_1_)
	{
	    return ((EnumFacing)p_getMetaFromState_1_.getValue(FACING)).getIndex();
	}
	
	public IBlockState withRotation(IBlockState p_withRotation_1_, Rotation p_withRotation_2_)
	{
		return p_withRotation_1_.withProperty(FACING, p_withRotation_2_.rotate((EnumFacing)p_withRotation_1_.getValue(FACING)));
	}
	
	public IBlockState withMirror(IBlockState p_withMirror_1_, Mirror p_withMirror_2_)
	{
		return p_withMirror_1_.withRotation(p_withMirror_2_.toRotation((EnumFacing)p_withMirror_1_.getValue(FACING)));
	}
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState blockState)
	{
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState blockState)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState)
	{
		return new TileEntityToolStation();
	}
	
	@Override
	public boolean doBlockActivate(World world, BlockPos blockPos,	IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing enumFacing, float hitX, float hitY, float hitZ)
	{
		TileEntityToolStation tile = (TileEntityToolStation) world.getTileEntity(blockPos);
		if (enumFacing == EnumFacing.UP && tile != null && !player.isSneaking() && world.isAirBlock(blockPos.up(1)))
		{
			player.swingArm(hand);
			return tile.onTileActivated(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), player);
		}
		return false;
	}
	
	public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState blockState, Block block)
	{
		if (!world.isRemote)
        {
    		if (world.getTileEntity(blockPos) != null && !world.isAirBlock(blockPos.up(1)))
    		{
    			TileEntityToolStation tile = (TileEntityToolStation) world.getTileEntity(blockPos);
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    			tile.clearItemStack();
    		}
        }
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		if (!world.isRemote)
        {
    		if (world.getTileEntity(blockPos) != null)
    		{
    			TileEntityToolStation tile = (TileEntityToolStation) world.getTileEntity(blockPos);
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    		}
        }
	}
}