package com.nhave.nhc.eventhandlers;

import com.nhave.nhc.api.items.IChromaAcceptor;
import com.nhave.nhc.api.items.IChromaAcceptorAdv;
import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.items.ItemChroma;
import com.nhave.nhc.registry.ModItems;
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
		/*else if (evt.input.getItem() instanceof IShadeAble && evt.mod.getItem() instanceof IItemShader)
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
		}*/
		else if (evt.input.getItem() instanceof IChromaAcceptor && evt.mod.getItem() == ModItems.itemChroma)
		{
			if (evt.input.getItem() instanceof IChromaAcceptorAdv && !((IChromaAcceptorAdv) evt.input.getItem()).supportsChroma(evt.input)) return;
			ItemStack stackChroma = ItemUtil.getItemFromStack(evt.input, "CHROMA");
			if (stackChroma == null) stackChroma = ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "white");
			ItemChroma itemChroma = (ItemChroma) stackChroma.getItem();
			if (itemChroma.getChroma(stackChroma) == itemChroma.getChroma(evt.mod)) return;
			
			ItemStack stack = evt.input.copy();
			ItemStack chroma = evt.mod.copy();
			chroma.setCount(1);
			ItemUtil.addItemToStack(stack, chroma, "CHROMA");
			evt.materialCost=1;
			evt.output=stack;
		}
		/*else if (evt.input.getItem() instanceof ItemDataglass && evt.mod.getItem() instanceof ItemToken)
		{
			ItemToken token = (ItemToken) evt.mod.getItem();
			boolean active = token.isActive(evt.mod);
			if (active)
			{
				ItemStack tokenStack = ItemUtil.getItemFromStack(evt.input, "TOKEN");
				ItemStack output = evt.input.copy();
				if (tokenStack != null && !tokenStack.isEmpty())
				{
					ItemUtil.removeAllItemFromStack(output, "TOKEN");
				}
				else
				{
					ItemUtil.addItemToStack(output, evt.mod.copy(), "TOKEN");
				}
				evt.materialCost=0;
				evt.output=output;
			}
		}*/
	}
}