package com.nhave.nhc.client.eventhandlers;

import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.api.items.IMouseWheel;
import com.nhave.nhc.network.Key;
import com.nhave.nhc.network.KeyBinds;
import com.nhave.nhc.network.MessageKeyPressed;
import com.nhave.nhc.network.PacketHandler;
import com.nhave.nhc.registry.ModConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputEventHandler
{
	private static Key getPressedKeybinding()
    {
        if (KeyBinds.toggle.isKeyDown())
        {
            return Key.TOGGLE;
        }

        return Key.UNKNOWN;
    }
	
	@SubscribeEvent
    public void mouseHandler(MouseEvent event)
    {
		if (event.getDwheel() == 0) return;
		if (FMLClientHandler.instance().getClientPlayerEntity() != null)
        {
            EntityPlayer entityPlayer = FMLClientHandler.instance().getClientPlayerEntity();

            if (entityPlayer.getHeldItemMainhand() != null)
            {
                if (entityPlayer.isSneaking())
                {
                	ItemStack currentlyEquippedItemStack = entityPlayer.getHeldItemMainhand();
                    
                	if (currentlyEquippedItemStack.getItem() instanceof IMouseWheel)
	                {
                		boolean chat = ModConfig.postModeToChat;
                		Key key = event.getDwheel() < 0 ? Key.SCROLLDN : Key.SCROLLUP;
                        if (entityPlayer.world.isRemote)
                        {
                            PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(key, chat));
                        }
                        else
                        {
                            ((IKeyBound) currentlyEquippedItemStack.getItem()).doKeyBindingAction(entityPlayer, currentlyEquippedItemStack, key, chat);
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
    	if (getPressedKeybinding() == Key.UNKNOWN)
        {
            return;
        }

        if (FMLClientHandler.instance().getClient().inGameHasFocus)
        {
            if (FMLClientHandler.instance().getClientPlayerEntity() != null)
            {
                EntityPlayer entityPlayer = FMLClientHandler.instance().getClientPlayerEntity();

                if (entityPlayer.getHeldItemMainhand() != null)
                {
                    ItemStack currentlyEquippedItemStack = entityPlayer.getHeldItemMainhand();
                    
                    if (currentlyEquippedItemStack.getItem() instanceof IKeyBound)
                    {
                		boolean chat = ModConfig.postModeToChat;
                        if (entityPlayer.world.isRemote)
                        {
                            PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(getPressedKeybinding(), chat));
                        }
                        else
                        {
                            ((IKeyBound) currentlyEquippedItemStack.getItem()).doKeyBindingAction(entityPlayer, currentlyEquippedItemStack, getPressedKeybinding(), chat);
                        }
                    }
                }
            }
        }
   	}
}