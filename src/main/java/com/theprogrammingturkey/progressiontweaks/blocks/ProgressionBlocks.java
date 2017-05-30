package com.theprogrammingturkey.progressiontweaks.blocks;

import com.theprogrammingturkey.gobblecore.blocks.BaseBlock;
import com.theprogrammingturkey.gobblecore.blocks.BlockLoader;
import com.theprogrammingturkey.gobblecore.blocks.IBlockHandler;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;
import com.theprogrammingturkey.progressiontweaks.blocks.tileentities.TileFirePit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProgressionBlocks implements IBlockHandler
{
	public static BaseBlock FIRE_PIT_LIT;
	public static BaseBlock FIRE_PIT_UNLIT;

	@Override
	public void registerBlocks(BlockLoader loader)
	{
		loader.setCreativeTab(ProgressionCore.modTab);

		loader.registerBlock(FIRE_PIT_LIT = new BlockFirePit(true), FIRE_PIT_LIT.getBlockName());
		FIRE_PIT_LIT.setCreativeTab(null);
		loader.registerBlock(FIRE_PIT_UNLIT = new BlockFirePit(false), FIRE_PIT_UNLIT.getBlockName());

		GameRegistry.registerTileEntity(TileFirePit.class, "tile_fire_pit");
	}

	@Override
	public void registerModels(BlockLoader loader)
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		loader.registerBlockModel(mesher, FIRE_PIT_UNLIT, 0, FIRE_PIT_UNLIT.getBlockName());
	}

}