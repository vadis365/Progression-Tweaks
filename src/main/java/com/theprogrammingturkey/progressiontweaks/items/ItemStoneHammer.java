package com.theprogrammingturkey.progressiontweaks.items;

import java.util.Set;

import com.google.common.collect.Sets;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemStoneHammer extends ItemTool
{
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE });

	public ItemStoneHammer()
	{
		super(Item.ToolMaterial.WOOD, EFFECTIVE_ON);
		this.maxStackSize = 1;
		this.setMaxDamage(25);
		this.attackSpeed = -3.2F;
		this.damageVsEntity = 3.0F;
		this.setUnlocalizedName("stone_hammer");
		this.setCreativeTab(ProgressionCore.modTab);
	}

	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();

		if(block == Blocks.WEB)
		{
			return 15.0F;
		}
		else
		{
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
		}
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		return true;
	}

}
