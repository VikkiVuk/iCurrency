package com.vikkivuk.mcurrency.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

import java.util.HashMap;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;
import com.vikkivuk.mcurrency.init.McurrencyModItems;

public class SetCardPinConfirmProcedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		boolean found_item = false;
		ItemStack item = ItemStack.EMPTY;
		double item_index = 0;
		if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler) {
			for (int _idx = 0; _idx < _modHandler.getSlots(); _idx++) {
				ItemStack itemstackiterator = _modHandler.getStackInSlot(_idx).copy();
				item_index = item_index + 1;
				if (itemstackiterator.getItem() == McurrencyModItems.DEBIT_CARD.get()) {
					if ((new Vec3((itemstackiterator.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")), (itemstackiterator.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")),
							(itemstackiterator.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))).equals(entity.getData(McurrencyModVariables.PLAYER_VARIABLES).card_selected)) {
						if (entity.getData(McurrencyModVariables.PLAYER_VARIABLES).cards.containsValue(entity.getData(McurrencyModVariables.PLAYER_VARIABLES).card_selected)) {
							item = (itemstackiterator.copy());
							{
								final String _tagName = "pin";
								final String _tagValue = (guistate.containsKey("textin:pin") ? (String) guistate.get("textin:pin") : "");
								CustomData.update(DataComponents.CUSTOM_DATA, item, tag -> tag.putString(_tagName, _tagValue));
							}
							found_item = true;
						}
					}
				}
			}
		}
		if (found_item) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Success! [OK]"), true);
			if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler) {
				ItemStack _setstack = item.copy();
				_setstack.setCount(1);
				_modHandler.setStackInSlot((int) item_index, _setstack);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("pin: " + ((new Object() {
					public ItemStack getItemStack(int sltid, Entity entity) {
						if (entity.getCapability(Capabilities.ItemHandler.ENTITY, null) instanceof IItemHandlerModifiable _modHandler) {
							return _modHandler.getStackInSlot(sltid).copy();
						}
						return ItemStack.EMPTY;
					}
				}.getItemStack((int) item_index, entity)).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("pin")))), false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Invalid card [CNF]"), true);
		}
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}
