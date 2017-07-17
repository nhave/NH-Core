package com.nhave.nhc.integration.top;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.nhave.nhc.Reference;
import com.nhave.nhc.api.blocks.IHudBlock;
import com.nhave.nhc.tiles.TileEntityDisplay;
import com.nhave.nhc.tiles.TileEntityMachine;
import com.nhave.nhc.tiles.TileEntityToolStation;
import com.nhave.nhc.util.StringUtils;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPCompatibility
{
    private static boolean registered;
    
    public static void register()
    {
        if (registered) return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.nhave.nhc.integration.top.TOPCompatibility$GetTheOneProbe");
    }
    
    public static class GetTheOneProbe implements com.google.common.base.Function<ITheOneProbe, Void>
    {
        public static ITheOneProbe probe;
        
        @Nullable
        @Override
        public Void apply(ITheOneProbe theOneProbe)
        {
            probe = theOneProbe;
            probe.registerProvider(new IProbeInfoProvider()
            {
                @Override
                public String getID()
                {
                    return Reference.MODID + ":default";
                }
                
				@Override
				public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
				{
					TileEntity tile = world.getTileEntity(data.getPos());
					if (tile instanceof TileEntityMachine)
					{
						TileEntityMachine machineTile = ((TileEntityMachine) tile);
						if (machineTile.hasOwner())
						{
							probeInfo.text(StringUtils.localize("tooltip.nhc.owner") + ": " + StringUtils.format(machineTile.getOwner(), StringUtils.YELLOW, StringUtils.ITALIC));
						}
						
						ItemStack stack = null;
						if (tile instanceof TileEntityToolStation) stack = ((TileEntityToolStation) tile).getItemStack();
						else if (tile instanceof TileEntityDisplay) stack = ((TileEntityDisplay) tile).getItemStack();
						
						if (blockState.getBlock() instanceof IHudBlock && stack != null && !stack.isEmpty())
						{
							List<String> info = new ArrayList<String>();
							((IHudBlock) blockState.getBlock()).addHudInfo(world, data.getPos(), blockState, info);
							if (info.size() > 1)
							{
								for (int i = 1; i < info.size(); ++i)
								{
									if (i == 1) probeInfo.horizontal().item(stack).text(info.get(i));
									else probeInfo.text(info.get(i));
								}
							}
						}
					}
				}
            });
            return null;
        }
    }
}