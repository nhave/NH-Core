package com.nhave.nhc.blocks;

import com.nhave.nhc.api.blocks.ILockableTile;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.registry.ModItems;

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

public class BlockMachineBase extends BlockBase
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private boolean rotation;
	
	public BlockMachineBase(String name)
	{
		super(name, Material.IRON);
		this.setHardness(50F);
		this.rotation = false;
	}
	
	public BlockMachineBase(String name, boolean rotation)
	{
		this(name);
		this.rotation = rotation;
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
	
	public IBlockState getStateFromMeta(int meta)
	{
	    EnumFacing facing = EnumFacing.getFront(meta);
	    if (facing.getAxis() == EnumFacing.Axis.Y)
	    {
	    	facing = EnumFacing.NORTH;
	    }
	    return getDefaultState().withProperty(FACING, facing);
	}
	
	public int getMetaFromState(IBlockState state)
	{
	    return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rotation)
	{
		return state.withProperty(FACING, rotation.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	public IBlockState withMirror(IBlockState state, Mirror mirror)
	{
		return state.withRotation(mirror.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ILockableTile)
		{
			ILockableTile lockable = (ILockableTile) world.getTileEntity(pos);
			if (lockable.hasOwner() && !lockable.getOwner().equals(player.getName())) return false;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
	
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		return false;
	}
	
	public boolean doBlockRotation(World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);
        for (IProperty<?> prop : state.getProperties().keySet())
        {
            if ((prop.getName().equals("facing") || prop.getName().equals("rotation")) && prop.getValueClass() == EnumFacing.class)
            {
                IBlockState newState;
                IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>) prop;
                EnumFacing facing = state.getValue(facingProperty);

                newState = state.withProperty(facingProperty, facing.rotateY());

                world.setBlockState(pos, newState);
                return true;
            }
        }
        return false;
    }
	
	public boolean doBlockActivate(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof ILockableTile)
		{
			ILockableTile lockable = (ILockableTile) worldIn.getTileEntity(pos);
			if (lockable.hasOwner() && !lockable.getOwner().equals(playerIn.getName())) return false;
			
			if (playerIn.isSneaking() && playerIn.getHeldItem(hand).getItem() == ModItems.itemLock && !lockable.hasOwner())
			{
				lockable.setOwner(playerIn.getName());
				playerIn.getHeldItem(hand).shrink(1);
				playerIn.swingArm(EnumHand.MAIN_HAND);
				return !worldIn.isRemote;
			}
			else if (playerIn.isSneaking() && playerIn.getHeldItem(hand).getItem() == ModItems.itemKey && lockable.hasOwner())
			{
				lockable.setOwner(null);
				ItemHelper.addItemToPlayer(playerIn, new ItemStack(ModItems.itemLock));
				playerIn.swingArm(EnumHand.MAIN_HAND);
				return !worldIn.isRemote;
			}
		}
		if (hand == EnumHand.MAIN_HAND)
		{
			if (!playerIn.getHeldItemMainhand().isEmpty() && ItemHelper.isToolWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ()))
			{
				if (playerIn.isSneaking())
				{
					if (!worldIn.isRemote)
					{
						ItemHelper.dismantleBlock(worldIn, pos, state, playerIn);
						ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
					else
					{
						playerIn.playSound(this.blockSoundType.getPlaceSound(), 1.0F, 0.6F);
						playerIn.swingArm(EnumHand.MAIN_HAND);
					}
				}
				else if (this.rotation && this.doBlockRotation(worldIn, pos, facing))
				{
					if (!worldIn.isRemote)
					{
						ItemHelper.useWrench(playerIn, playerIn.getHeldItemMainhand(), pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
					else
					{
						playerIn.playSound(this.blockSoundType.getPlaceSound(), 1.0F, 0.6F);
						playerIn.swingArm(EnumHand.MAIN_HAND);
					}
				}
			}
		}
		return doBlockActivate(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
}