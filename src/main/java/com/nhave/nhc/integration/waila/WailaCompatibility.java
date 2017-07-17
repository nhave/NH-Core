package com.nhave.nhc.integration.waila;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.api.blocks.IHudBlock;
import com.nhave.nhc.blocks.BlockBase;
import com.nhave.nhc.tiles.TileEntityDisplay;
import com.nhave.nhc.tiles.TileEntityMachine;
import com.nhave.nhc.tiles.TileEntityToolStation;
import com.nhave.nhc.util.StringUtils;

import mcjty.lib.tools.ItemStackTools;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaCompatibility implements IWailaDataProvider
{
    public static final WailaCompatibility INSTANCE = new WailaCompatibility();
    
    private WailaCompatibility() {}
    
    private static boolean registered;
    private static boolean loaded;
    
    public static void load(IWailaRegistrar registrar)
    {
        System.out.println("WailaCompatibility.load");
        if (!registered)
        {
            throw new RuntimeException("Please register this handler using the provided method.");
        }
        if (!loaded)
        {
            registrar.registerHeadProvider(INSTANCE, BlockBase.class);
            registrar.registerBodyProvider(INSTANCE, BlockBase.class);
            registrar.registerTailProvider(INSTANCE, BlockBase.class);
            loaded = true;
        }
    }

    public static void register()
    {
        if (registered) return;
        registered = true;
        FMLInterModComms.sendMessage("waila", "register", "com.nhave.nhc.integration.waila.WailaCompatibility.load");
    }
    
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        return tag;
    }
    
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return ItemStackTools.getEmptyStack();
    }
    
    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }
    
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileEntity tile = accessor.getTileEntity();
        IBlockState blockState = accessor.getBlockState();
        
		if (tile instanceof TileEntityMachine)
		{
			TileEntityMachine machineTile = ((TileEntityMachine) tile);
			if (machineTile.hasOwner())
			{
				currenttip.add(StringUtils.localize("tooltip.nhc.owner") + ": " + StringUtils.format(machineTile.getOwner(), StringUtils.YELLOW, StringUtils.ITALIC));
			}
			
			ItemStack stack = null;
			if (tile instanceof TileEntityToolStation) stack = ((TileEntityToolStation) tile).getItemStack();
			else if (tile instanceof TileEntityDisplay) stack = ((TileEntityDisplay) tile).getItemStack();
			
			if (blockState.getBlock() instanceof IHudBlock && stack != null && !stack.isEmpty())
			{
				List<String> info = new ArrayList<String>();
				((IHudBlock) blockState.getBlock()).addHudInfo(accessor.getWorld(), accessor.getPosition(), blockState, info);
				if (info.size() > 1)
				{
					for (int i = 1; i < info.size(); ++i)
					{
						if (i == 1) currenttip.add(info.get(i));
						else currenttip.add(info.get(i));
					}
				}
			}
		}
        return currenttip;
    }
    
    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }
}