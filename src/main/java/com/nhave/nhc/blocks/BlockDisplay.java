package com.nhave.nhc.blocks;

import java.util.List;

import com.nhave.nhc.api.blocks.IHudBlock;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.registry.ModItems;
import com.nhave.nhc.tiles.TileEntityDisplay;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDisplay extends BlockMachineBase implements IHudBlock
{
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    
	public BlockDisplay(String name)
	{
		super(name);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState blockState)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState)
	{
		return new TileEntityDisplay();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			TileEntityDisplay tile = (TileEntityDisplay) worldIn.getTileEntity(pos);
			if (tile.hasOwner() && !tile.getOwner().equals(playerIn.getName())) return false;
			
			if (playerIn.isSneaking() && playerIn.getHeldItem(hand).getItem() == ModItems.itemLock && !tile.hasOwner())
			{
				tile.setOwner(playerIn.getName());
				playerIn.getHeldItem(hand).shrink(1);
				playerIn.swingArm(EnumHand.MAIN_HAND);
				return !worldIn.isRemote;
			}
			else if (playerIn.isSneaking() && playerIn.getHeldItem(hand).getItem() == ModItems.itemKey && tile.hasOwner())
			{
				tile.setOwner(null);
				ItemHelper.addItemToPlayer(playerIn, new ItemStack(ModItems.itemLock));
				playerIn.swingArm(EnumHand.MAIN_HAND);
				return !worldIn.isRemote;
			}
			else if (tile != null && !playerIn.isSneaking())
			{
				playerIn.swingArm(hand);
				return tile.onTileActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		if (!world.isRemote)
        {
    		if (world.getTileEntity(blockPos) != null)
    		{
    			TileEntityDisplay tile = (TileEntityDisplay) world.getTileEntity(blockPos);
    			if (tile.hasOwner()) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModItems.itemLock));
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    		}
        }
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		if (StringUtils.isShiftKeyDown()) TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.nhc.display"), ";", StringUtils.GRAY);
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public void addHudInfo(World world, BlockPos pos, IBlockState state, List list)
	{
		TileEntityDisplay tile = (TileEntityDisplay) world.getTileEntity(pos);
		if (tile != null)
		{
			list.add(StringUtils.format(getLocalizedName(), StringUtils.YELLOW, StringUtils.ITALIC));
			ItemStack stack = tile.getItemStack();
			if (stack != null && !stack.isEmpty())
			{
				list.add(StringUtils.localize("tooltip.nhc.display.item") + ": " + StringUtils.format(StringUtils.limitString(stack.getDisplayName(), 20), StringUtils.YELLOW, StringUtils.ITALIC));
			}
		}
	}
}