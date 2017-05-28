package com.theprogrammingturkey.progressiontweaks.items;

import java.util.List;

import com.theprogrammingturkey.progressiontweaks.util.ProgressionAchievements;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLime extends ItemFood
{
	public ItemLime()
	{
		super(1, 0.2f, false);
		this.setMaxStackSize(16);
		this.setUnlocalizedName("lime");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool)
	{
		list.add("Green tacos live on!");
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if(entityLiving instanceof EntityPlayer)
		{
			((EntityPlayer) entityLiving).addStat(ProgressionAchievements.toTheCore);
		}
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
}
