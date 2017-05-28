package com.theprogrammingturkey.progressiontweaks.proxy;

import com.theprogrammingturkey.progressiontweaks.blocks.tileentities.TileFirePit;
import com.theprogrammingturkey.progressiontweaks.client.renderer.RenderSpear;
import com.theprogrammingturkey.progressiontweaks.client.renderer.TileFirePitRenderer;
import com.theprogrammingturkey.progressiontweaks.entity.EntitySpear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

	@Override
	public boolean isClient()
	{
		return true;
	}

	public void registerRenderings()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileFirePit.class, new TileFirePitRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new IRenderFactory<EntitySpear>()
		{
			@Override
			public Render<? super EntitySpear> createRenderFor(RenderManager manager)
			{
				return new RenderSpear(manager);
			}
		});
	}

	public void registerEvents()
	{

	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
