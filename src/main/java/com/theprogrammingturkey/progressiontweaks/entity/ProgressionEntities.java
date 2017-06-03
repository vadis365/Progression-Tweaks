package com.theprogrammingturkey.progressiontweaks.entity;

import com.theprogrammingturkey.gobblecore.entity.EntityLoader;
import com.theprogrammingturkey.gobblecore.entity.IEntityHandler;
import com.theprogrammingturkey.progressiontweaks.client.renderer.RenderSpear;
import com.theprogrammingturkey.progressiontweaks.client.renderer.RenderTomahawk;

public class ProgressionEntities implements IEntityHandler
{

	@Override
	public void registerEntities(EntityLoader loader)
	{
		loader.registerEntity("spear", EntitySpear.class, 120, 1, true);
		loader.registerEntity("tomahawk", EntityTomahawk.class, 120, 1, true);
	}

	@Override
	public void registerRenderings(EntityLoader loader)
	{
		loader.registerEntityRendering(EntitySpear.class, RenderSpear.class);
		loader.registerEntityRendering(EntityTomahawk.class, RenderTomahawk.class);
	}
}
