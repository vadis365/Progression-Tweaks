package com.theprogrammingturkey.progressiontweaks.util;

import com.theprogrammingturkey.progressiontweaks.ProgressionCore;
import com.theprogrammingturkey.progressiontweaks.items.ProgressionItems;

public class ProgressionAchievements
{
	public static AchievementPage page;
	public static Achievement toTheCore;

	public static void loadAchievements()
	{
		String id = ProgressionCore.MODID;
		toTheCore = new Achievement(id + ".toTheCore", id + ".toTheCore", 0, 0, ProgressionItems.LIME, null).registerStat();
		page = new AchievementPage(ProgressionCore.NAME, toTheCore);
		AchievementPage.registerAchievementPage(page);
	}
}