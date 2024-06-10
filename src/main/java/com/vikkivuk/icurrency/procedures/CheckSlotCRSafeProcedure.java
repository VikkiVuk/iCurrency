package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;
import java.util.Map;

import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class CheckSlotCRSafeProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).current_slot_cr >= entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).max_slots_cr) {
				{
					IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
					_vars.current_slot_cr = -1;
					_vars.syncPlayerVariables(entity);
				}
			} else {
				while (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).current_slot_cr < entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).max_slots_cr) {
					if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).current_slot_cr == -1) {
						break;
					} else {
						if (new Object() {
							public int getAmount(int sltid) {
								if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
									ItemStack stack = ((Slot) _slots.get(sltid)).getItem();
									if (stack != null)
										return stack.getCount();
								}
								return 0;
							}
						}.getAmount((int) entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) > 0) {
							if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).current_slot_cr < entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).max_slots_cr) {
								{
									IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
									_vars.current_slot_cr = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).current_slot_cr + 1;
									_vars.syncPlayerVariables(entity);
								}
							} else {
								{
									IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
									_vars.current_slot_cr = -1;
									_vars.syncPlayerVariables(entity);
								}
								break;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}
}
