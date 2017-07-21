package com.theprogrammingturkey.progressiontweaks.blocks;

import com.theprogrammingturkey.gobblecore.blocks.BaseBlock;
import com.theprogrammingturkey.gobblecore.blocks.BlockLoader;
import com.theprogrammingturkey.gobblecore.blocks.IBlockHandler;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;
import com.theprogrammingturkey.progressiontweaks.blocks.tileentities.TileFirePit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;

public class ProgressionBlocks implements IBlockHandler
{
	public static BaseBlock FIRE_PIT_LIT;
	public static BaseBlock FIRE_PIT_UNLIT;
	public static BaseBlock BLANK_TELEPORTER;

	@Override
	public void registerBlocks(BlockLoader loader)
	{
		loader.registerBlock(FIRE_PIT_LIT = new BlockFirePit(true));

		loader.setCreativeTab(ProgressionCore.modTab);

		loader.registerBlock(FIRE_PIT_UNLIT = new BlockFirePit(false), TileFirePit.class);
		loader.registerBlock(BLANK_TELEPORTER = new BaseBlock("blank_teleporter"));
	}

	@Override
	public void registerModels(BlockLoader loader)
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		loader.registerBlockModel(mesher, FIRE_PIT_UNLIT, 0);
		loader.registerBlockModel(mesher, BLANK_TELEPORTER, 0);
	}

}
