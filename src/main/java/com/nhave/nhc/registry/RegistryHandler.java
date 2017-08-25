package com.nhave.nhc.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryHandler
{
	@SubscribeEvent
    public void onBlockRegistry(Register<Block> event)
	{
        ModBlocks.init();
        ModBlocks.register(event);
    }
	
    @SubscribeEvent
    public void onItemRegistry(Register<Item> event)
    {
        ModItems.init();
        ModItems.register(event);
        ModBlocks.registerItemBlocks(event);
    }
    
    @SubscribeEvent
    public void onCraftingRegistry(Register<IRecipe> event)
    {
        //ModCrafting.init();
    	ModCrafting.register(event);
    }
}