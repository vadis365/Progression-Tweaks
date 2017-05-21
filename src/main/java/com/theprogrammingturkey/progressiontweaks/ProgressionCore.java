package com.theprogrammingturkey.progressiontweaks;

import org.apache.logging.log4j.Logger;

import com.theprogrammingturkey.gobblecore.IModCore;
import com.theprogrammingturkey.gobblecore.blocks.BlockManager;
import com.theprogrammingturkey.gobblecore.items.ItemManager;
import com.theprogrammingturkey.gobblecore.managers.ProxyManager;
import com.theprogrammingturkey.gobblecore.proxy.IBaseProxy;
import com.theprogrammingturkey.progressiontweaks.blocks.ProgressionBlocks;
import com.theprogrammingturkey.progressiontweaks.commands.ProgressionCommands;
import com.theprogrammingturkey.progressiontweaks.config.ProgressionConfigLoader;
import com.theprogrammingturkey.progressiontweaks.items.ProgressionItems;
import com.theprogrammingturkey.progressiontweaks.network.ProgressionPacketHandler;
import com.theprogrammingturkey.progressiontweaks.util.ProgressionAchievements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ProgressionCore.MODID, version = ProgressionCore.VERSION, name = ProgressionCore.NAME, dependencies = "after:gobblecore")
public class ProgressionCore implements IModCore
{
	public static final String MODID = "progressiontweaks";
	public static final String NAME = "Progression Tweaks";
	public static final String VERSION = "@Version@";

	@Instance(value = MODID)
	public static ProgressionCore instance;

	@SidedProxy(clientSide = "com.theprogrammingturkey.progressiontweaks.proxy.ClientProxy", serverSide = "com.theprogrammingturkey.progressiontweaks.proxy.CommonProxy")
	public static IBaseProxy proxy;

	public static CreativeTabs modTab = new CreativeTabs(MODID)
	{
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ProgressionBlocks.FIRE_PIT_UNLIT);
		}
	};

	public static Logger logger;

	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		ProgressionConfigLoader.loadConfigSettings(event.getSuggestedConfigurationFile());
		
		BlockManager.registerBlockHandler(new ProgressionBlocks(), this);
		ItemManager.registerItemHandler(new ProgressionItems(), this);
		
		ProxyManager.registerModProxy(proxy);
		
		ProgressionPacketHandler.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ProgressionAchievements.loadAchievements();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		ProgressionCommands.loadCommands();
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}
}
