package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.client.gui.screens.Screen;

import io.netty.buffer.Unpooled;

import com.vikkivuk.icurrency.world.inventory.SetCardPinMenu;
import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class DebitCardRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		Vec3 card = Vec3.ZERO;
		if (Screen.hasShiftDown()) {
			if (itemstack.getOrCreateTag().getBoolean("activated")) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("This card is owned by: " + itemstack.getOrCreateTag().getString("holder"))), false);
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("----------------------"), false);
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("If this is not your card, kindly return it to its rightful owner or dispose of it."), false);
			} else {
				if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.size() >= 4) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("Sorry, you cant have more than 4 cards!"), false);
				} else {
					card = new Vec3(Math.round(Mth.nextInt(RandomSource.create(), 1111, 9999)), Math.round(Mth.nextInt(RandomSource.create(), 1111, 9999)), Math.round(Mth.nextInt(RandomSource.create(), 111, 999)));
					if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("one")) {
						try {
							entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("one", card);
						} catch (Exception e) {
						}
					} else if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("two")) {
						try {
							entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("two", card);
						} catch (Exception e) {
						}
					} else if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("three")) {
						try {
							entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("three", card);
						} catch (Exception e) {
						}
					} else if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("four")) {
						try {
							entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("four", card);
						} catch (Exception e) {
						}
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("Card has been successfully activated! You can see the card details when you hover over it in your inventory."), false);
					itemstack.getOrCreateTag().putDouble("h1n", (card.x()));
					itemstack.getOrCreateTag().putDouble("h2n", (card.y()));
					itemstack.getOrCreateTag().putDouble("cvc", (card.z()));
					itemstack.getOrCreateTag().putString("holder", (entity.getDisplayName().getString()));
					itemstack.getOrCreateTag().putBoolean("activated", true);
					{
						IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
						_vars.card_selected = card;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof ServerPlayer _ent) {
						BlockPos _bpos = BlockPos.containing(x, y, z);
						_ent.openMenu(new MenuProvider() {
							@Override
							public Component getDisplayName() {
								return Component.literal("SetCardPin");
							}

							@Override
							public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
								return new SetCardPinMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						}, _bpos);
					}
					{
						IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
						_vars.refresh_cards = true;
						_vars.syncPlayerVariables(entity);
					}
				}
			}
		}
	}
}
