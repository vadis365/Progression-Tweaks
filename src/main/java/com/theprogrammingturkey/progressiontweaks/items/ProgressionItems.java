package com.theprogrammingturkey.progressiontweaks.items;

import com.theprogrammingturkey.gobblecore.items.IItemHandler;
import com.theprogrammingturkey.gobblecore.items.ItemLoader;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;

public class ProgressionItems implements IItemHandler
{
	public static Item LIME;

	@Override
	public void registerItems(ItemLoader loader)
	{
		
		loader.setCreativeTab(ProgressionCore.modTab);

		loader.registerItem(LIME = new Lime(), "lime");
	}

	@Override
	public void registerModels(ItemLoader loader)
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		loader.registerItemModel(mesher, LIME, 0, "lime");
	}

}
