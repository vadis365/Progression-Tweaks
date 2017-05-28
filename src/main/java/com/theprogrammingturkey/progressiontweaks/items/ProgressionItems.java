package com.theprogrammingturkey.progressiontweaks.items;

import com.theprogrammingturkey.gobblecore.items.BaseItem;
import com.theprogrammingturkey.gobblecore.items.IItemHandler;
import com.theprogrammingturkey.gobblecore.items.ItemLoader;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;

public class ProgressionItems implements IItemHandler
{
	public static Item LIME;
	public static BaseItem SPEAR;
	public static BaseItem BROKEN_SPEAR_TIP;
	public static BaseItem BROKEN_SPEAR_SHAFT;

	@Override
	public void registerItems(ItemLoader loader)
	{

		loader.setCreativeTab(ProgressionCore.modTab);

		loader.registerItem(LIME = new ItemLime(), "lime");
		loader.registerItem(SPEAR = new ItemSpear(), SPEAR.getItemName());
		loader.registerItem(BROKEN_SPEAR_TIP = new ItemBrokenSpear("tip"), BROKEN_SPEAR_TIP.getItemName());
		loader.registerItem(BROKEN_SPEAR_SHAFT = new ItemBrokenSpear("shaft"), BROKEN_SPEAR_SHAFT.getItemName());
	}

	@Override
	public void registerModels(ItemLoader loader)
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		loader.registerItemModel(mesher, LIME, 0, "lime");
		loader.registerItemModel(mesher, SPEAR, 0, SPEAR.getItemName());
		loader.registerItemModel(mesher, BROKEN_SPEAR_TIP, 0, BROKEN_SPEAR_TIP.getItemName());
		loader.registerItemModel(mesher, BROKEN_SPEAR_SHAFT, 0, BROKEN_SPEAR_SHAFT.getItemName());
	}

}
