package com.vikkivuk.icurrency.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import java.util.HashMap;

import com.vikkivuk.icurrency.network.IcurrencyModVariables;
import com.vikkivuk.icurrency.init.IcurrencyModItems;

public class SetCardPinConfirmProcedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		boolean found_item = false;
		ItemStack item = ItemStack.EMPTY;
		double item_index = 0;
		if (!world.isClientSide()) {
			if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandlerForEach) {
				for (int _idx = 0; _idx < _modHandlerForEach.getSlots(); _idx++) {
					ItemStack itemstackiterator = _modHandlerForEach.getStackInSlot(_idx).copy();
					item_index = item_index + 1;
					if (itemstackiterator.getItem() == IcurrencyModItems.DEBIT_CARD.get()) {
						if ((new Vec3((itemstackiterator.getOrCreateTag().getDouble("h1n")), (itemstackiterator.getOrCreateTag().getDouble("h2n")), (itemstackiterator.getOrCreateTag().getDouble("cvc"))))
								.equals(entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).card_selected)) {
							if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsValue(entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).card_selected)) {
								itemstackiterator.getOrCreateTag().putString("pin", (guistate.containsKey("textin:pin") ? (String) guistate.get("textin:pin") : ""));
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal("Success!"), true);
								if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandlerEntSetSlot) {
									ItemStack _setstack = itemstackiterator.copy();
									_setstack.setCount(1);
									_modHandlerEntSetSlot.setStackInSlot((int) (item_index - 1), _setstack);
								}
								found_item = true;
							}
						}
					}
				}
			}
			if (!found_item) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Invalid card"), true);
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
	}
}
