package com.theprogrammingturkey.progressiontweaks.blocks.tileentities;

import java.util.List;

import com.theprogrammingturkey.gobblecore.network.NetworkManager;
import com.theprogrammingturkey.gobblecore.util.Scheduler;
import com.theprogrammingturkey.gobblecore.util.Task;
import com.theprogrammingturkey.progressiontweaks.ProgressionCore;
import com.theprogrammingturkey.progressiontweaks.blocks.ProgressionBlocks;
import com.theprogrammingturkey.progressiontweaks.config.ProgressionSettings;
import com.theprogrammingturkey.progressiontweaks.network.PacketUdateFirePit;
import com.theprogrammingturkey.progressiontweaks.registries.FirePitRegistry;
import com.theprogrammingturkey.progressiontweaks.registries.FirePitRegistry.CookingResult;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public class TileFirePit extends TileEntity implements ITickable
{
	private ItemStack cooking;
	private int burnTimeLeft = -1;
	private int cookTimeLeft = -1;

	public TileFirePit()
	{
		Scheduler.scheduleTask(new Task("Fire_Pit_Mob_Attract", -1, 20)
		{
			@Override
			public void callback()
			{

			}

			@Override
			public void update()
			{
				if(!getWorld().isRemote)
				{
					if(getWorld() == null)
					{
						Scheduler.removeTask(this);
						return;
					}

					TileEntity tileentity = getWorld().getTileEntity(pos);
					if(tileentity != null && tileentity.equals(TileFirePit.this))
					{
						EntityPlayer toAttack = null;
						for(EntityPlayer player : getWorld().playerEntities)
						{
							if(player.getPosition().distanceSq(getPos()) < 10)
							{
								toAttack = player;
								break;
							}
						}
						if(toAttack != null)
						{
							int radius = ProgressionSettings.firePitAttractionRadius;
							List<EntityMob> entities = getWorld().getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(getPos().add(radius, radius, radius), getPos().add(-radius, -radius, -radius)));
							for(EntityMob ent : entities)
								if(ent.getAttackTarget() == null)
									ent.setAttackTarget(toAttack);
						}
					}
					else
					{
						Scheduler.removeTask(this);
					}
				}
			}

		});
	}

	@Override
	public void update()
	{
		if(!this.getWorld().isRemote)
		{
			if(this.getWorld().isRainingAt(pos.add(0, 1, 0)))
			{
				if(this.burnTimeLeft > 0)
				{
					burnTimeLeft = -1;
					cookTimeLeft = -1;
					TileEntity tileentity = this.getWorld().getTileEntity(pos);
					this.getWorld().setBlockState(pos, ProgressionBlocks.FIRE_PIT_UNLIT.getDefaultState());
					this.getWorld().setBlockState(pos, ProgressionBlocks.FIRE_PIT_UNLIT.getDefaultState());
					tileentity.validate();
					this.getWorld().setTileEntity(pos, tileentity);
					getWorld().playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
					NetworkManager.getSimpleNetwork(ProgressionCore.instance).sendToAll(new PacketUdateFirePit(getItemCooking(), getBurnTimeLeft(), getCookTimeLeft(), getPos().getX(), getPos().getY(), getPos().getZ()));
				}
				return;
			}

			if(burnTimeLeft > 0)
			{
				burnTimeLeft--;
				if(cookTimeLeft > 0)
					cookTimeLeft--;
			}

			if(burnTimeLeft == 0)
			{
				TileEntity tileentity = this.getWorld().getTileEntity(pos);
				this.getWorld().setBlockState(pos, ProgressionBlocks.FIRE_PIT_UNLIT.getDefaultState());
				this.getWorld().setBlockState(pos, ProgressionBlocks.FIRE_PIT_UNLIT.getDefaultState());
				tileentity.validate();
				this.getWorld().setTileEntity(pos, tileentity);
				burnTimeLeft = -1;
				NetworkManager.getSimpleNetwork(ProgressionCore.instance).sendToAll(new PacketUdateFirePit(getItemCooking(), getBurnTimeLeft(), getCookTimeLeft(), getPos().getX(), getPos().getY(), getPos().getZ()));
			}

			if(cookTimeLeft == 0)
			{
				CookingResult result = FirePitRegistry.INSTANCE.getResultFromInput(cooking);
				if(result != null)
				{
					this.setCooking(result.getResult());
					this.getWorld().spawnEntity(new EntityXPOrb(getWorld(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), result.getXp()));
					cookTimeLeft = -1;
					NetworkManager.getSimpleNetwork(ProgressionCore.instance).sendToAll(new PacketUdateFirePit(getItemCooking(), getBurnTimeLeft(), getCookTimeLeft(), getPos().getX(), getPos().getY(), getPos().getZ()));
				}
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

	public void startCooking(ItemStack stack, int duration)
	{
		this.cooking = stack.copy();
		this.cooking.setCount(1);
		this.cookTimeLeft = duration;
	}

	public void setCooking(ItemStack stack)
	{
		if(stack != null)
		{
			this.cooking = stack.copy();
			this.cooking.setCount(1);
		}
		else
		{
			this.cooking = null;
		}
	}

	public void startBurnTime(int burnTime)
	{
		this.burnTimeLeft = burnTime;
		TileEntity tileentity = this.getWorld().getTileEntity(pos);
		this.getWorld().setBlockState(pos, ProgressionBlocks.FIRE_PIT_LIT.getDefaultState());
		this.getWorld().setBlockState(pos, ProgressionBlocks.FIRE_PIT_LIT.getDefaultState());
		tileentity.validate();
		this.getWorld().setTileEntity(pos, tileentity);
		NetworkManager.getSimpleNetwork(ProgressionCore.instance).sendToAll(new PacketUdateFirePit(getItemCooking(), getBurnTimeLeft(), getCookTimeLeft(), getPos().getX(), getPos().getY(), getPos().getZ()));
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
			EntityItem entityitem = new EntityItem(player.getEntityWorld(), player.posX, player.posY, player.posZ, this.cooking.copy());
			entityitem.setPickupDelay(5);
			player.getEntityWorld().spawnEntity(entityitem);
			this.cooking = null;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("burnTime", this.burnTimeLeft);
		nbt.setInteger("cookTimeLeft", this.cookTimeLeft);
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
		this.cookTimeLeft = nbt.getInteger("cookTimeLeft");
		this.cooking = new ItemStack((NBTTagCompound) nbt.getTag("cookingItem"));
	}
}