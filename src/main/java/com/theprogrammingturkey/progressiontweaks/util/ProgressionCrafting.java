package com.theprogrammingturkey.progressiontweaks.util;

import com.theprogrammingturkey.progressiontweaks.blocks.ProgressionBlocks;
import com.theprogrammingturkey.progressiontweaks.items.ProgressionItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProgressionCrafting
{

	public static void initCrafting()
	{
		// Spear
		GameRegistry.addShapedRecipe(new ItemStack(ProgressionItems.SPEAR), "S  ", " S ", "  F", 'S', new ItemStack(Items.STICK), 'F', new ItemStack(Items.FLINT));
		GameRegistry.addShapelessRecipe(new ItemStack(ProgressionItems.SPEAR), new ItemStack(ProgressionItems.BROKEN_SPEAR_SHAFT), new ItemStack(Items.STICK), new ItemStack(Items.STICK));
		GameRegistry.addShapelessRecipe(new ItemStack(ProgressionItems.SPEAR), new ItemStack(ProgressionItems.BROKEN_SPEAR_TIP), new ItemStack(Items.FLINT));

		// Fire Pit
		GameRegistry.addShapedRecipe(new ItemStack(ProgressionBlocks.FIRE_PIT_UNLIT), "CCC", "S S", " S ", 'S', new ItemStack(Items.STICK), 'C', new ItemStack(Blocks.STONE_SLAB, 1, 3));
	}
}
