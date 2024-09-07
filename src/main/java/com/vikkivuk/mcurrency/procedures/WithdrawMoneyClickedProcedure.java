package com.vikkivuk.mcurrency.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;
import com.vikkivuk.mcurrency.init.McurrencyModItems;

public class WithdrawMoneyClickedProcedure {
	public static void execute(Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		double total_amount = 0;
		if (Math.round(new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : "")) <= 120000) {
			if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).money >= Math.round(new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : ""))) {
				total_amount = Math.round(new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : ""));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_THOUSAND.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 1000));
					((Slot) _slots.get(0)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 1000 * (Math.floor(total_amount / 1000) >= 64 ? 64 : Math.floor(total_amount / 1000));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_FIVE_HUNDRED.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 500));
					((Slot) _slots.get(1)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 500 * (Math.floor(total_amount / 500) >= 64 ? 64 : Math.floor(total_amount / 500));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWO_HUNDRED.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 200));
					((Slot) _slots.get(2)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 200 * (Math.floor(total_amount / 200) >= 64 ? 64 : Math.floor(total_amount / 200));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_HUNDRED.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 100));
					((Slot) _slots.get(3)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 100 * (Math.floor(total_amount / 100) >= 64 ? 64 : Math.floor(total_amount / 100));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_FIFTY.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 50));
					((Slot) _slots.get(4)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 50 * (Math.floor(total_amount / 100) >= 64 ? 64 : Math.floor(total_amount / 100));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWENTY.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 20));
					((Slot) _slots.get(5)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 20 * (Math.floor(total_amount / 100) >= 64 ? 64 : Math.floor(total_amount / 100));
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TEN.get()).copy();
					_setstack.setCount((int) Math.floor(total_amount / 10));
					((Slot) _slots.get(6)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				total_amount = total_amount - 10 * (Math.floor(total_amount / 100) >= 64 ? 64 : Math.floor(total_amount / 100));
				{
					McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.money = entity.getData(McurrencyModVariables.PLAYER_VARIABLES).money - Math.round(new Object() {
						double convert(String s) {
							try {
								return Double.parseDouble(s.trim());
							} catch (Exception e) {
							}
							return 0;
						}
					}.convert(guistate.containsKey("textin:amount") ? (String) guistate.get("textin:amount") : ""));
					_vars.syncPlayerVariables(entity);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Sorry, you cant withdraw more money than you have."), false);
				if (entity instanceof Player _player)
					_player.closeContainer();
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Sorry, you can only withdraw \u01B5120000 at a time!"), false);
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
	}
}
