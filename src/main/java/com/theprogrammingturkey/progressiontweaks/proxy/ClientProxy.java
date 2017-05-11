package com.theprogrammingturkey.progressiontweaks.proxy;

import com.theprogrammingturkey.progressiontweaks.blocks.tileentities.TileFirePit;
import com.theprogrammingturkey.progressiontweaks.renderer.TileFirePitRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

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
