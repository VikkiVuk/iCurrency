package xyz.wulfco.icurrency.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.world.inventory.DepositMenu;
import xyz.wulfco.icurrency.world.inventory.ATMMenu;
import xyz.wulfco.icurrency.world.inventory.TransferMenu;
import xyz.wulfco.icurrency.world.inventory.WithdrawMenu;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ATMButtonMessage {
	private final int buttonID, x, y, z;

	public ATMButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public ATMButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(ATMButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(ATMButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		if (entity instanceof ServerPlayer player) {
			BlockPos blockPos = new BlockPos(x, y, z);

			if (buttonID == 0) {
				NetworkHooks.openGui(player, new MenuProvider() {
					@Override
					public @NotNull Component getDisplayName() {
						return new TextComponent("Deposit");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
						return new DepositMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(blockPos));
					}
				}, blockPos);
			} else if (buttonID == 1) {
				NetworkHooks.openGui(player, new MenuProvider() {
					@Override
					public @NotNull Component getDisplayName() {
						return new TextComponent("Withdraw");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
						return new WithdrawMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(blockPos));
					}
				}, blockPos);
			} else if (buttonID == 2) {
				NetworkHooks.openGui(player, new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return new TextComponent("Transfer Money");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new TransferMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(blockPos));
					}
				}, blockPos);
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		iCurrency.addNetworkMessage(ATMButtonMessage.class, ATMButtonMessage::buffer, ATMButtonMessage::new, ATMButtonMessage::handler);
	}
}
