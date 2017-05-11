package com.theprogrammingturkey.progressiontweaks.config;

import java.io.File;

import com.theprogrammingturkey.gobblecore.config.ConfigErrorReporter;
import com.theprogrammingturkey.gobblecore.util.GameUtil;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;
import com.theprogrammingturkey.progressiontweaks.registries.FirePitRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Configuration;

public class ProgressionConfigLoader
{
	public static Configuration config;

	public static final String firePitCat = "Firepit Settings";

	public static void loadConfigSettings(File file)
	{
		config = new Configuration(file);

		loadFromConfig();
	}

	public static void loadFromConfig()
	{
		config.load();
		config.setCategoryComment(firePitCat, "Fire Pit Settings");

		String[] recipes = config.getStringList("Recipes", firePitCat, new String[0], "Recipes for the fire pit to be able to cook. Format -> \"ModID:ItemName-ModID:ItemName\" With the first item being the input and the second being the cooked output.");
		for(String s : recipes)
		{
			String[] items = s.split("-");
			if(items.length == 2)
			{
				String[] item1 = items[0].split(":");
				String[] item2 = items[1].split(":");
				if(item1.length == 2 && item2.length == 2)
				{
					ItemStack stack1 = GameUtil.getItemStack(item1[0], item1[1], 1);
					ItemStack stack2 = GameUtil.getItemStack(item2[0], item2[1], 1);

					if(stack1 == null || stack2 == null)
						error("Failed to parse fire pit recipe item \"" + items[0] + "\" and/or \"" + items[1] + "\"");
					else
						FirePitRegistry.INSTANCE.registerCookingRecipe(stack1, stack2);
				}
				else
				{
					error("Failed to parse fire pit recipe item \"" + items[0] + "\" and/or \"" + items[1] + "\"");
				}
			}
			else
			{
				error("Failed to parse fire pit recipe with the input of \"" + s + "\"");
			}
		}

		String[] fuels = config.getStringList("Fuels", firePitCat, new String[0], "Fuel items for the fire pit to be able to accept. Format -> \"ModID:ItemName-duration\" With duration in ticks.");
		for(String s : fuels)
		{
			String[] items = s.split("-");
			if(items.length == 2)
			{
				String[] item = items[0].split(":");
				if(item.length == 2)
				{
					ItemStack stack = GameUtil.getItemStack(item[0], item[1], 1); // getItem\
					if(stack == null)
					{
						error("Failed to parse fire pit fuel item \"" + items[0] + "\"");
					}
					else
					{
						try
						{
							int duration = Integer.parseInt(items[1]);
							FirePitRegistry.INSTANCE.registerFuel(stack, duration);

						} catch(NumberFormatException e)
						{
							error(items[1] + " is not a valid fire pit fuel duration");
						}
					}
				}
				else
				{
					error("Failed to parse fire pit fuel item \"" + items[0] + "\"");
				}
			}
			else
			{
				error("Failed to parse fire pit fuel with the input of \"" + s + "\"");
			}
		}

		config.save();
	}

	private static void error(String message)
	{
		ConfigErrorReporter.queueErrorMessage(TextFormatting.RED, ProgressionCore.NAME, message);
	}
}
