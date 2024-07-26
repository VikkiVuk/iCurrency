package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.Map;

import io.netty.buffer.Unpooled;

import com.vikkivuk.icurrency.world.inventory.EnterPinMenu;
import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class SwipeCardCashRegisterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean found_player = false;
		if (entity instanceof Player _player) {
			IcurrencyModVariables.PlayerVariables _vars = _player.getData(IcurrencyModVariables.PLAYER_VARIABLES);
			_vars.card_selected = new Vec3(
					((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
					((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")),
					((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")));
			_vars.syncPlayerVariables(_player);
		}
		{
			IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
			_vars.selected_card_holder = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder");
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof ServerPlayer _ent) {
			BlockPos _bpos = BlockPos.containing(x, y, z);
			_ent.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("EnterPin");
				}

				@Override
				public boolean shouldTriggerClientSideContainerClosingOnOpen() {
					return false;
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					return new EnterPinMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
				}
			}, _bpos);
		}
	}
}
