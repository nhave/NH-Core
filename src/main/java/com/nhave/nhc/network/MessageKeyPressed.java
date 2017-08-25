package com.nhave.nhc.network;

import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.api.items.IMouseWheel;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageKeyPressed implements IMessage, IMessageHandler<MessageKeyPressed, IMessage>
{
    private byte keyPressed;

    public MessageKeyPressed()
    {
    }

    public MessageKeyPressed(Key key, boolean chat)
    {
        if (key == Key.TOGGLE && !chat)
        {
            this.keyPressed = (byte) 0;
        }
        else if (key == Key.TOGGLE && chat)
        {
            this.keyPressed = (byte) 1;
        }
        else if (key == Key.SCROLLUP && !chat)
        {
            this.keyPressed = (byte) 2;
        }
        else if (key == Key.SCROLLUP && chat)
        {
            this.keyPressed = (byte) 3;
        }
        else if (key == Key.SCROLLDN && !chat)
        {
            this.keyPressed = (byte) 4;
        }
        else if (key == Key.SCROLLDN && chat)
        {
            this.keyPressed = (byte) 5;
        }
        else
        {
            this.keyPressed = (byte) 6;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.keyPressed = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(keyPressed);
    }

    @Override
    public IMessage onMessage(MessageKeyPressed message, MessageContext ctx)
    {
        EntityPlayer entityPlayer = ctx.getServerHandler().player;
        
        if (entityPlayer != null && entityPlayer.getHeldItemMainhand() != null)
        {
        	if (entityPlayer.getHeldItemMainhand().getItem() instanceof IKeyBound)
        	{
	        	if (message.keyPressed == 0)
	            {
	                ((IKeyBound) entityPlayer.getHeldItemMainhand().getItem()).doKeyBindingAction(entityPlayer, entityPlayer.getHeldItemMainhand(), Key.TOGGLE, false);
	            }
	        	else if (message.keyPressed == 1)
	            {
	                ((IKeyBound) entityPlayer.getHeldItemMainhand().getItem()).doKeyBindingAction(entityPlayer, entityPlayer.getHeldItemMainhand(), Key.TOGGLE, true);
	            }
        	}
        	if (entityPlayer.getHeldItemMainhand().getItem() instanceof IMouseWheel)
        	{
	        	if (message.keyPressed == 2)
	            {
	                ((IMouseWheel) entityPlayer.getHeldItemMainhand().getItem()).doKeyBindingAction(entityPlayer, entityPlayer.getHeldItemMainhand(), Key.SCROLLUP, false);
	            }
	        	else if (message.keyPressed == 3)
	            {
	                ((IMouseWheel) entityPlayer.getHeldItemMainhand().getItem()).doKeyBindingAction(entityPlayer, entityPlayer.getHeldItemMainhand(), Key.SCROLLUP, true);
	            }
	        	else if (message.keyPressed == 4)
	            {
	                ((IMouseWheel) entityPlayer.getHeldItemMainhand().getItem()).doKeyBindingAction(entityPlayer, entityPlayer.getHeldItemMainhand(), Key.SCROLLDN, false);
	            }
	        	else if (message.keyPressed == 5)
	            {
	                ((IMouseWheel) entityPlayer.getHeldItemMainhand().getItem()).doKeyBindingAction(entityPlayer, entityPlayer.getHeldItemMainhand(), Key.SCROLLDN, true);
	            }
        	}
        }

        return null;
    }
}