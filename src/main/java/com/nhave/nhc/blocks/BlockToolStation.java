package com.nhave.nhc.blocks;

import com.nhave.nhc.helpers.ItemHelper;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToolStation extends BlockBase
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockToolStation(String name)
	{
		super(name, Material.IRON);
		this.setHardness(50F);
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
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		if (axis == EnumFacing.UP) return false;
		NBTTagCompound nbt = world.getTileEntity(pos).serializeNBT();
		boolean result = super.rotateBlock(world, pos, axis);
		if (result) world.getTileEntity(pos).deserializeNBT(nbt);
		
		return result;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			TileEntityToolStation tile = (TileEntityToolStation) worldIn.getTileEntity(pos);
			if (facing == EnumFacing.UP && tile != null && !playerIn.isSneaking() && worldIn.isAirBlock(pos.up(1)))
			{
				playerIn.swingArm(hand);
				return tile.onTileActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
			}
			else if (!playerIn.getHeldItemMainhand().isEmpty() && ItemHelper.isToolWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ()))
			{
				if (playerIn.isSneaking())
				{
					ItemHelper.dismantleBlock(worldIn, pos, state, playerIn);;
					playerIn.swingArm(EnumHand.MAIN_HAND);
					ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
					return !worldIn.isRemote;
				}
			}
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
    			if (stack != null) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
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
    			if (stack != null) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    		}
        }
	}
}