package com.nhave.nhc.eventhandlers;

import com.nhave.nhc.api.items.IItemShader;
import com.nhave.nhc.api.items.IShadeAble;
import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.items.ItemChroma;
import com.nhave.nhc.registry.ModItems;
import com.nhave.nhc.shaders.ShaderManager;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolStationEventHandler
{
	@SubscribeEvent
	public void handleToolStationEvent(ToolStationUpdateEvent evt)
	{
		if (evt.input == null || evt.mod == null)
		{
			return;
		}
		else if (evt.input.getItem() instanceof IShadeAble && evt.mod.getItem() instanceof IItemShader)
		{
			if (ShaderManager.hasShader(evt.input) && ItemHelper.areItemsEqual(ShaderManager.getShader(evt.input), evt.mod)) return;
			if (ShaderManager.canApplyShader(evt.input, evt.mod))
			{
				ItemStack shadeable = evt.input.copy();
				ShaderManager.setShader(shadeable, evt.mod.copy());
				evt.materialCost=0;
				evt.output=shadeable;
			}
		}
		else if (evt.input.getItem() instanceof IShadeAble && evt.mod.getItem() == ModItems.itemShaderRemover)
		{
			if (!ShaderManager.hasShader(evt.input)) return;
			ItemStack shadeable = evt.input.copy();
			ShaderManager.removeShader(shadeable);
			evt.materialCost=0;
			evt.output=shadeable;
		}
		else if (evt.input.getItem() == ModItems.itemDataGlass && evt.mod.getItem() == ModItems.itemChroma)
		{
			if (ItemUtil.getItemFromStack(evt.input, "CHROMA") != null && ItemUtil.getItemFromStack(evt.input, "CHROMA").getItem() == ModItems.itemChroma)
			{
				ItemStack stackChroma = ItemUtil.getItemFromStack(evt.input, "CHROMA");
				ItemChroma itemChroma = (ItemChroma) stackChroma.getItem();
				if (itemChroma.getChroma(stackChroma) == itemChroma.getChroma(evt.mod)) return;
			}
			ItemStack stack = evt.input.copy();
			ItemStack chroma = evt.mod.copy();
			chroma.setCount(1);
			ItemUtil.addItemToStack(stack, chroma, "CHROMA");
			evt.materialCost=1;
			evt.output=stack;
		}
	}
}