package com.theprogrammingturkey.progressiontweaks.blocks.tileentities;

import com.theprogrammingturkey.progressiontweaks.blocks.ProgressionBlocks;
import com.theprogrammingturkey.progressiontweaks.network.PacketUdateFirePit;
import com.theprogrammingturkey.progressiontweaks.network.ProgressionPacketHandler;
import com.theprogrammingturkey.progressiontweaks.registries.FirePitRegistry;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileFirePit extends TileEntity implements ITickable
{
	private ItemStack cooking;
	private int burnTimeLeft = -1;
	private int cookTimeLeft = -1;

	@Override
	public void update()
	{
		if(!this.worldObj.isRemote)
		{
			if(burnTimeLeft > 0)
			{
				burnTimeLeft--;
				if(cookTimeLeft > 0)
					cookTimeLeft--;
			}

			if(burnTimeLeft == 0)
			{
				TileEntity tileentity = this.worldObj.getTileEntity(pos);
				this.worldObj.setBlockState(pos, ProgressionBlocks.FIRE_PIT_UNLIT.getDefaultState());
				this.worldObj.setBlockState(pos, ProgressionBlocks.FIRE_PIT_UNLIT.getDefaultState());
				tileentity.validate();
				this.worldObj.setTileEntity(pos, tileentity);
				burnTimeLeft = -1;
			}

			if(cookTimeLeft == 0)
			{
				this.setCooking(FirePitRegistry.INSTANCE.getResultFromInput(cooking));
				cookTimeLeft = -1;
				ProgressionPacketHandler.INSTANCE.sendToAll(new PacketUdateFirePit(getItemCooking(), getBurnTimeLeft(), getCookTimeLeft(), getPos().getX(), getPos().getY(), getPos().getZ()));
			}
		}
	}

	public boolean isCooking()
	{
		return burnTimeLeft > 0;
	}

	public ItemStack getItemCooking()
	{
		return this.cooking == null ? null : this.cooking.copy();
	}

	public void startCooking(ItemStack stack)
	{
		this.cooking = stack.copy();
		this.cooking.func_190920_e(1);
		this.cookTimeLeft = 200;
	}

	public void setCooking(ItemStack stack)
	{
		if(stack != null)
		{
			this.cooking = stack.copy();
			this.cooking.func_190920_e(1);
		}
		else
		{
			this.cooking = null;
		}
	}

	public void startBurnTime(int burnTime)
	{
		this.burnTimeLeft = burnTime;
		TileEntity tileentity = this.worldObj.getTileEntity(pos);
		this.worldObj.setBlockState(pos, ProgressionBlocks.FIRE_PIT_LIT.getDefaultState());
		this.worldObj.setBlockState(pos, ProgressionBlocks.FIRE_PIT_LIT.getDefaultState());
		tileentity.validate();
		this.worldObj.setTileEntity(pos, tileentity);
	}

	public void setBurnTimeLeft(int burnTime)
	{
		this.burnTimeLeft = burnTime;
	}

	public int getBurnTimeLeft()
	{
		return this.burnTimeLeft;
	}

	public void setCookTimeLeft(int cookTimeLeft)
	{
		this.cookTimeLeft = cookTimeLeft;
	}

	public int getCookTimeLeft()
	{
		return this.cookTimeLeft;
	}

	public void dropCurrentItem(EntityPlayer player)
	{
		if(this.cooking != null)
		{
			EntityItem entityitem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, this.cooking.copy());
			entityitem.setPickupDelay(5);
			player.worldObj.spawnEntityInWorld(entityitem);
			this.cooking = null;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("burnTime", this.burnTimeLeft);
		NBTTagCompound cookingNBT = new NBTTagCompound();
		if(this.cooking != null)
			this.cooking.writeToNBT(cookingNBT);
		nbt.setTag("cookingItem", cookingNBT);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.burnTimeLeft = nbt.getInteger("burnTime");
		this.cooking = new ItemStack((NBTTagCompound) nbt.getTag("cookingItem"));
	}
}