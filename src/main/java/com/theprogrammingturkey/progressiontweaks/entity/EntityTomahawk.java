package com.theprogrammingturkey.progressiontweaks.entity;

import com.theprogrammingturkey.progressiontweaks.items.ProgressionItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTomahawk extends EntityCustomThrowable
{
	public float rot = 0;

	public EntityTomahawk(World worldIn)
	{
		super(worldIn);
	}

	public EntityTomahawk(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}

	public EntityTomahawk(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	@Override
	protected ItemStack getEntityStack()
	{
		return new ItemStack(ProgressionItems.TOMAHAWK);
	}
}