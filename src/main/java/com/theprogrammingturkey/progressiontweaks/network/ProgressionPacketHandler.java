package com.theprogrammingturkey.progressiontweaks.network;

import com.theprogrammingturkey.progressiontweaks.ProgressionCore;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ProgressionPacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ProgressionCore.MODID);
	private static int id = 0;
	
	public static void init()
	{
		INSTANCE.registerMessage(PacketUdateFirePit.Handler.class, PacketUdateFirePit.class, id++, Side.CLIENT);
	}
}
