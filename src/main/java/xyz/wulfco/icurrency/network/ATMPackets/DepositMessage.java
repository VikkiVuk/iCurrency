
package xyz.wulfco.icurrency.network.ATMPackets;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapabilityProvider;
import xyz.wulfco.icurrency.iCurrency;

import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DepositMessage {
	private final int buttonID, x, y, z;

	public DepositMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public DepositMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(DepositMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(DepositMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		if (buttonID == 0) {
			if (entity instanceof ServerPlayer player && player.containerMenu instanceof Supplier current && current.get() instanceof Map slots) {
				final Slot slot1 = (Slot) slots.get(0);
				final Slot slot2 = (Slot) slots.get(1);
				final Slot slot3 = (Slot) slots.get(2);

				final double slot1Amount = slot1.getItem().getCount() * slot1.getItem().getOrCreateTag().getDouble("money_value");
				final double slot2Amount = slot2.getItem().getCount() * slot2.getItem().getOrCreateTag().getDouble("money_value");
				final double slot3Amount = slot3.getItem().getCount() * slot3.getItem().getOrCreateTag().getDouble("money_value");

				final int totalAmount = (int) (slot1Amount + slot2Amount + slot3Amount);
				if (totalAmount < 1) {
					player.displayClientMessage(new TextComponent(ChatFormatting.RED + "You must deposit at least 1 zlatnik!"), false);
					return;
				}

				entity.getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent(wallet -> {
					wallet.updateWalletAmount((double) totalAmount);
					player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "You have deposited " + totalAmount + " zlatniks into your wallet!"), false);
				});

				// Cleanup
				slot1.set(ItemStack.EMPTY);
				slot2.set(ItemStack.EMPTY);
				slot3.set(ItemStack.EMPTY);
				player.containerMenu.broadcastChanges();
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		iCurrency.addNetworkMessage(DepositMessage.class, DepositMessage::buffer, DepositMessage::new, DepositMessage::handler);
	}
}
