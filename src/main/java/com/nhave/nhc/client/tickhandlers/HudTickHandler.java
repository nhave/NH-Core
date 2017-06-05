package com.nhave.nhc.client.tickhandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.nhave.nhc.api.blocks.IHudBlock;
import com.nhave.nhc.api.items.IHudDisplay;
import com.nhave.nhc.api.items.IHudItem;
import com.nhave.nhc.client.util.RenderUtils;
import com.nhave.nhc.client.util.RenderUtils.HUDPositions;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.registry.ModConfig;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class HudTickHandler
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    
    private static void tickEnd()
    {
        
        if ((mc.currentScreen == null || ModConfig.showHudWhileChatting && mc.currentScreen instanceof GuiChat) && !mc.gameSettings.hideGUI && !mc.gameSettings.showDebugInfo)
        {
            List<String> info = new ArrayList<String>();
            
            for (int j = 0; j < 6; j++)
            {
            	int slot = j;
            	if (ModConfig.hudPosition < 5) slot = 4-j;
                
            	ItemStack hudItem = ItemHelper.getCurrentItemOrArmor(mc.player, slot);

                if (hudItem != null && hudItem.getItem() instanceof IHudItem)
                {
                	IHudItem provider = (IHudItem) hudItem.getItem();
                    
                    provider.addHudInfo(hudItem, mc.player, info, (slot == 0 ? false : true));
                }
            }
            
    	    addBlockOrEntityinView(info);
            
            if (ModConfig.hudPosition >= 5) Collections.reverse(info);
            
            if (info.isEmpty())
            {
            	return;
            }
            
            GL11.glPushMatrix();
            mc.entityRenderer.setupOverlayRendering();
            GL11.glScaled(ModConfig.hudScale, ModConfig.hudScale, 1.0D);
            int i = 0;
            for (String s : info)
            {
                RenderUtils.drawStringAtHUDPosition(s, HUDPositions.values()[ModConfig.hudPosition], mc.fontRendererObj, ModConfig.hudOffsetX, ModConfig.hudOffsetY, ModConfig.hudScale, 0xeeeeee, true, i);
                i++;
            }
            
            GL11.glPopMatrix();
        }
    }
    
    public static void addBlockOrEntityinView(List list)
    {
	    RayTraceResult rayTrace = mc.objectMouseOver;
	    if (rayTrace != null)
	    {
	    	if (rayTrace.typeOfHit == RayTraceResult.Type.ENTITY) list.add(StringUtils.format(StringUtils.limitString(rayTrace.entityHit.getName(), 20), StringUtils.YELLOW, StringUtils.ITALIC));
	    	else if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
	    	{
	    		IBlockState state = mc.world.getBlockState(rayTrace.getBlockPos());
	    	    Block block = state.getBlock();
	    	    if (block == null || block == Blocks.AIR) return;
	    	    
	    	    if (block instanceof IHudBlock) ((IHudBlock) block).addHudInfo(mc.world, rayTrace.getBlockPos(), state, list);
	    	    else
	    	    {
	    	    	ItemStack item = block.getPickBlock(state, rayTrace, mc.world, rayTrace.getBlockPos(), mc.player);
	    	    	if (item != null) list.add(StringUtils.format(StringUtils.limitString(item.getDisplayName(), 20), StringUtils.YELLOW, StringUtils.ITALIC));
	    	    }
	    	}
	    }
    }
    
    @SubscribeEvent
    public void onRenderTick(RenderTickEvent evt)
    {
    	if (mc.player != null)
        {
	    	ItemStack hudDisplay = ItemHelper.getCurrentItemOrArmor(mc.player, 5);
	    	boolean showHud = hudDisplay.getItem() instanceof IHudDisplay && ((IHudDisplay)hudDisplay.getItem()).isHudActive(hudDisplay);
	        if (evt.phase == Phase.END && (showHud || ModConfig.forceShowHud))
	        {
	            tickEnd();
	        }
        }
    }
}