package com.nhave.nhc.client.render;

import org.lwjgl.opengl.GL11;

import com.nhave.nhc.tiles.TileEntityToolStation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderTileToolStation extends TileEntitySpecialRenderer
{
	private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		renderTileEntityAt(te, x, y, z);
	}
	
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z)
	{
		TileEntityToolStation tileEntity = (TileEntityToolStation) tile;
		if (tileEntity.getItemStack() != null)
		{
			int meta = tileEntity.getBlockMetadata();
			ItemStack stack = tileEntity.getItemStack();
			
			GL11.glPushMatrix();
			
			if (stack.getItem() instanceof ItemBlock)
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 1.12F, (float)z + 0.5F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);

				if (meta == 5) GL11.glRotatef(270, 0, 1, 0);
				else if (meta == 4) GL11.glRotatef(90, 0, 1, 0);
				else if (meta == 3) GL11.glRotatef(180, 0, 1, 0);
			}
			else
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 1.02F, (float)z + 0.5F);
				GL11.glScalef(0.8F, 0.8F, 0.8F);

				if (meta == 4)
				{
					GL11.glRotatef(90, 0, 1, 0);
				}
				else if (meta == 5)
				{
					GL11.glRotatef(270, 0, 1, 0);
				}
				else if (meta == 3)
				{
					GL11.glRotatef(180, 0, 1, 0);
				}
				
				GL11.glRotatef(90, 1, 0, 0);
			}
			
			this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
			GL11.glPopMatrix();
		}
	}
}