package com.nhave.nhc.network;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBinds
{
    public static KeyBinding toggle = new KeyBinding("key.nhc.toggle", Keyboard.KEY_G, "key.nhc.category");
}