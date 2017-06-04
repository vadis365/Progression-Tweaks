package com.theprogrammingturkey.progressiontweaks.entity;

import com.theprogrammingturkey.progressiontweaks.config.ProgressionSettings;
import com.theprogrammingturkey.progressiontweaks.items.ProgressionItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpear extends EntityCustomThrowable
{
	private boolean broken = false;

	public EntitySpear(World worldIn)
	{
		super(worldIn);
	}

	public EntitySpear(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}

	public EntitySpear(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	@Override
	protected ItemStack getEntityStack()
	{
		if(broken)
			return rand.nextBoolean() ? new ItemStack(ProgressionItems.BROKEN_SPEAR_SHAFT) : new ItemStack(ProgressionItems.BROKEN_SPEAR_TIP);
		return new ItemStack(ProgressionItems.SPEAR);
	}

	protected void onHit(RayTraceResult raytraceResultIn)
	{
		if(ProgressionSettings.SpearBreakChance != 0)
			broken = this.rand.nextInt(ProgressionSettings.SpearBreakChance) == 0;

		Entity entity = raytraceResultIn.entityHit;

		if(entity != null)
		{
			if(entity.width <= 0.75f && entity.height <= 0.75f)
				this.setDamage(10);
			else
				this.setDamage(4);
		}

		super.onHit(raytraceResultIn);
	}

}