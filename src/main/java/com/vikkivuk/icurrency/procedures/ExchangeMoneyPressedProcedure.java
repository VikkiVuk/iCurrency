package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import java.util.function.Supplier;
import java.util.Map;

import com.vikkivuk.icurrency.init.IcurrencyModItems;

public class ExchangeMoneyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double total_money = 0;
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(0);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(1)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(1);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(2)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(2);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(3)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(3);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(4)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(4);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(5)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(5);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(6)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(6);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(7)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(7);
		total_money = total_money + (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(8)).getItem() : ItemStack.EMPTY)
				.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("value") * new Object() {
					public int getAmount(int sltid) {
						if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
							ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
							if (stack != null)
								return stack.getCount();
						}
						return 0;
					}
				}.getAmount(8);
		if (total_money >= 0) {
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_THOUSAND.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 1000));
				((Slot) _slots.get(9)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 1000 * (Math.floor(total_money / 1000) >= 64 ? 64 : Math.floor(total_money / 1000));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_FIVE_HUNDRED.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 500));
				((Slot) _slots.get(10)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 500 * (Math.floor(total_money / 500) >= 64 ? 64 : Math.floor(total_money / 500));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_TWO_HUNDRED.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 200));
				((Slot) _slots.get(11)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 200 * (Math.floor(total_money / 200) >= 64 ? 64 : Math.floor(total_money / 200));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_HUNDRED.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 100));
				((Slot) _slots.get(12)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 100 * (Math.floor(total_money / 100) >= 64 ? 64 : Math.floor(total_money / 100));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_FIFTY.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 50));
				((Slot) _slots.get(13)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 50 * (Math.floor(total_money / 50) >= 64 ? 64 : Math.floor(total_money / 50));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_TWENTY.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 20));
				((Slot) _slots.get(14)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 20 * (Math.floor(total_money / 20) >= 64 ? 64 : Math.round(total_money / 20));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_TEN.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 10));
				((Slot) _slots.get(15)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 10 * (Math.floor(total_money / 10) >= 64 ? 64 : Math.round(total_money / 10));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_THOUSAND.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 1000));
				((Slot) _slots.get(16)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 1000 * (Math.floor(total_money / 1000) >= 64 ? 64 : Math.floor(total_money / 1000));
			if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
				ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_FIVE_HUNDRED.get()).copy();
				_setstack.setCount((int) Math.floor(total_money / 500));
				((Slot) _slots.get(17)).set(_setstack);
				_player.containerMenu.broadcastChanges();
			}
			total_money = total_money - 500 * (Math.floor(total_money / 500) >= 64 ? 64 : Math.floor(total_money / 500));
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(0)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(1)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(2)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(3)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(4)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(5)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(6)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(7)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
		if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
			((Slot) _slots.get(8)).set(ItemStack.EMPTY);
			_player.containerMenu.broadcastChanges();
		}
	}
}
