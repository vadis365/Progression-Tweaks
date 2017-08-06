package com.theprogrammingturkey.progressiontweaks.client.renderer;

import com.theprogrammingturkey.progressiontweaks.blocks.ProgressionBlocks;
import com.theprogrammingturkey.progressiontweaks.blocks.tileentities.TileFirePit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;

public class TileFirePitRenderer extends TileEntitySpecialRenderer<TileFirePit>
{
	private EntityItem cooking;

	public TileFirePitRenderer()
	{

	}

	@Override
	public void render(TileFirePit te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		World world = te.getWorld();

		if(!world.getBlockState(te.getPos()).getBlock().equals(ProgressionBlocks.FIRE_PIT_LIT) && !world.getBlockState(te.getPos()).getBlock().equals(ProgressionBlocks.FIRE_PIT_UNLIT))
			return;

		if(!te.getItemCooking().isEmpty())
		{
			if(cooking == null)
			{
				cooking = new EntityItem(world);
				cooking.setNoDespawn();
			}

			if(!cooking.getItem().getItem().equals(te.getItemCooking().getItem()) || cooking.getItem().getItemDamage() != te.getItemCooking().getItemDamage())
				cooking.setItem(te.getItemCooking());

			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y - 0.1, z + 0.5);
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderManager().doRenderEntity(cooking, 0.0D, 0.0D, 0.0D, 0.0F, 0, false);
			RenderHelper.enableStandardItemLighting();
			GlStateManager.popMatrix();
		}
	}
}