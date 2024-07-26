package com.vikkivuk.icurrency.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import io.netty.buffer.Unpooled;

import com.vikkivuk.icurrency.world.inventory.SetCardPinMenu;
import com.vikkivuk.icurrency.network.IcurrencyModVariables;

public class DebitCardRightClickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (!itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("activated")) {
			if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.size() > 4) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("You can only have 4 cards!"), true);
			} else {
				{
					final String _tagName = "activated";
					final boolean _tagValue = true;
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
				}
				{
					final String _tagName = "h1n";
					final double _tagValue = (Mth.nextInt(RandomSource.create(), 1111, 9999));
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
				}
				{
					final String _tagName = "h2n";
					final double _tagValue = (Mth.nextInt(RandomSource.create(), 1111, 9999));
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
				}
				{
					final String _tagName = "cvc";
					final double _tagValue = (Mth.nextInt(RandomSource.create(), 111, 999));
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
				}
				{
					final String _tagName = "holder";
					final String _tagValue = (entity.getDisplayName().getString());
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				{
					final String _tagName = "holder_uuid";
					final String _tagValue = (entity.getStringUUID());
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				{
					IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
					_vars.refresh_cards = true;
					_vars.syncPlayerVariables(entity);
				}
				if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("one")) {
					entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("one", (new Vec3((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
							(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")), (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))));
				} else if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("two")) {
					entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("two", (new Vec3((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
							(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")), (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))));
				} else if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("three")) {
					entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("three", (new Vec3((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
							(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")), (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))));
				} else if (!entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsKey("four")) {
					entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.put("four", (new Vec3((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
							(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")), (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))));
				}
				if (entity instanceof Player _player) {
					IcurrencyModVariables.PlayerVariables _vars = _player.getData(IcurrencyModVariables.PLAYER_VARIABLES);
					_vars.card_selected = new Vec3((itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")), (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")),
							(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")));
					_vars.syncPlayerVariables(_player);
				}
				if (entity instanceof ServerPlayer _ent) {
					BlockPos _bpos = BlockPos.containing(x, y, z);
					_ent.openMenu(new MenuProvider() {
						@Override
						public Component getDisplayName() {
							return Component.literal("SetCardPin");
						}

						@Override
						public boolean shouldTriggerClientSideContainerClosingOnOpen() {
							return false;
						}

						@Override
						public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
							return new SetCardPinMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
						}
					}, _bpos);
				}
			}
		}
	}
}
