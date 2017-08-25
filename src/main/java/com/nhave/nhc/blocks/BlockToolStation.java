package com.nhave.nhc.blocks;

import java.util.List;

import com.nhave.nhc.api.blocks.IHudBlock;
import com.nhave.nhc.api.items.IToolStationHud;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.tiles.TileEntityToolStation;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToolStation extends BlockMachineBase implements IHudBlock
{
	public BlockToolStation(String name)
	{
		super(name, true);
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
	public boolean doBlockRotation(World world, BlockPos pos, EnumFacing axis)
	{
		if (axis == EnumFacing.UP) return false;
		return super.doBlockRotation(world, pos, axis);
	}
	
	@Override
	public boolean doBlockActivate(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand == EnumHand.MAIN_HAND)
		{
			TileEntityToolStation tile = (TileEntityToolStation) worldIn.getTileEntity(pos);
			if (facing == EnumFacing.UP && tile != null && !playerIn.isSneaking())
			{
				playerIn.swingArm(hand);
				return tile.onTileActivated(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
			}
		}
		return super.doBlockActivate(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.isRemote)
        {
    		if (worldIn.getTileEntity(pos) != null && !worldIn.isAirBlock(pos.up(1)))
    		{
    			TileEntityToolStation tile = (TileEntityToolStation) worldIn.getTileEntity(pos);
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) ItemHelper.dropBlockAsItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
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
    			//if (tile.hasOwner()) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModItems.itemLock));
    			ItemStack stack = tile.getItemStack();
    			if (stack != null) ItemHelper.dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
    		}
        }
		super.onBlockHarvested(world, blockPos, blockState, player);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		if (StringUtils.isShiftKeyDown()) TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.nhc.toolstation"), ";", StringUtils.GRAY);
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public void addHudInfo(World world, BlockPos pos, IBlockState state, List list)
	{
		TileEntityToolStation tile = (TileEntityToolStation) world.getTileEntity(pos);
		if (tile != null)
		{
			list.add(StringUtils.format(getLocalizedName(), StringUtils.YELLOW, StringUtils.ITALIC));
			ItemStack stack = tile.getItemStack();
			if (stack != null && !stack.isEmpty())
			{
				list.add(StringUtils.localize("tooltip.nhc.toolstation.item") + ": " + StringUtils.format(StringUtils.limitString(stack.getDisplayName(), 20), StringUtils.YELLOW, StringUtils.ITALIC));
				if (stack.getItem() instanceof IToolStationHud) ((IToolStationHud) stack.getItem()).addToolStationInfo(stack, list);
			}
		}
	}
}