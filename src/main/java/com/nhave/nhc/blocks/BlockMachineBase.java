package com.nhave.nhc.blocks;

import com.nhave.nhc.helpers.ItemHelper;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		return false;
		//if (!this.rotation) return false;
		//return super.rotateBlock(world, pos, axis);
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
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
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
		return false;
	}
}