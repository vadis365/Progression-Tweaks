package com.theprogrammingturkey.progressiontweaks.registries;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FirePitRegistry
{
	public static final FirePitRegistry INSTANCE = new FirePitRegistry();
	private Map<ItemStack, ItemStack> cookingRegistry = new HashMap<ItemStack, ItemStack>();
	private Map<ItemStack, Integer> cookingFuelRegistry = new HashMap<ItemStack, Integer>();

	public FirePitRegistry()
	{
		cookingRegistry.put(new ItemStack(Items.FISH), new ItemStack(Items.COOKED_FISH));
		cookingFuelRegistry.put(new ItemStack(Items.COAL), 10000);
	}

	public void registerCookingRecipe(ItemStack input, ItemStack result)
	{
		cookingRegistry.put(input, result);
	}

	public void registerFuel(ItemStack fuel, int burnTime)
	{
		cookingFuelRegistry.put(fuel, burnTime);
	}

	public ItemStack getResultFromInput(ItemStack input)
	{
		if(input == null)
			return null;

		ItemStack result = null;
		for(ItemStack stack : cookingRegistry.keySet())
			if(stack.getItem().equals(input.getItem()) && stack.getItemDamage() == input.getItemDamage())
				result = cookingRegistry.get(stack);
		return result;
	}

	public int getBurnTimeFromFuel(ItemStack input)
	{
		if(input == null)
			return -1;
		
		int result = -1;
		for(ItemStack stack : cookingFuelRegistry.keySet())
			if(stack.getItem().equals(input.getItem()) && stack.getItemDamage() == input.getItemDamage())
				result = cookingFuelRegistry.get(stack);
		return result;
	}
}
