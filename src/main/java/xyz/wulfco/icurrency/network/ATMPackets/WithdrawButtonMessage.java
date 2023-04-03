package xyz.wulfco.icurrency.network.ATMPackets;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapabilityProvider;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.registry.ItemRegistry;
import xyz.wulfco.icurrency.world.inventory.WithdrawMenu;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WithdrawButtonMessage {
	private final int buttonID, x, y, z;

	public WithdrawButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public WithdrawButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(WithdrawButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(WithdrawButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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

	static boolean debounce = false;

	public static void handleButtonAction(Player player, int buttonID, int x, int y, int z) {
		HashMap<String, Object> guistate = WithdrawMenu.guistate;

		if (buttonID == 0) {
			if (player == null)
				return;

			if (debounce)
				return;

			final EditBox amount = (EditBox) guistate.get("text:amount");

			if (Integer.parseInt(amount.getValue()) < 10) {
				player.displayClientMessage(new TextComponent("You can't withdraw less than 10 Zlatniks!").withStyle(ChatFormatting.RED), false);
				return;
			}

			player.getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent((wallet) -> {
				debounce = true;

				if (wallet.getWalletAmount() < Double.parseDouble(amount.getValue())) {
					player.displayClientMessage(new TextComponent("You don't have enough Zlatniks!").withStyle(ChatFormatting.RED), false);
					debounce = false;
					return;
				}

				wallet.updateWalletAmount(-Double.parseDouble(amount.getValue()));
				int namount = Integer.parseInt(amount.getValue());
				if (namount > 1000000) {
					player.displayClientMessage(new TextComponent("You can't withdraw more than 1,000,000 Zlatniks at a time!").withStyle(ChatFormatting.RED), false);
					debounce = false;
					return;
				}

				// Thousand bills
				ItemStack stack1 = new ItemStack(ItemRegistry.DOLLAR_THOUSAND.get());
				stack1.setCount((int) Math.floor(namount / 1000));
				ItemHandlerHelper.giveItemToPlayer(player, stack1);
				namount -= stack1.getCount() * 1000;
				// Five Hundred bills
				ItemStack stack2 = new ItemStack(ItemRegistry.DOLLAR_FIVE_HUNDRED.get());
				stack2.setCount((int) Math.floor(namount / 500));
				ItemHandlerHelper.giveItemToPlayer(player, stack2);
				namount -= stack2.getCount() * 500;
				// Hundred bills
				ItemStack stack3 = new ItemStack(ItemRegistry.DOLLAR_HUNDRED.get());
				stack3.setCount((int) Math.floor(namount / 100));
				ItemHandlerHelper.giveItemToPlayer(player, stack3);
				namount -= stack3.getCount() * 100;
				// Fifty bills
				ItemStack stack4 = new ItemStack(ItemRegistry.DOLLAR_FIFTY.get());
				stack4.setCount((int) Math.floor(namount / 50));
				ItemHandlerHelper.giveItemToPlayer(player, stack4);
				namount -= stack4.getCount() * 50;
				// Twenty bills
				ItemStack stack5 = new ItemStack(ItemRegistry.DOLLAR_TWENTY.get());
				stack5.setCount((int) Math.floor(namount / 20));
				ItemHandlerHelper.giveItemToPlayer(player, stack5);
				namount -= stack5.getCount() * 20;
				// Ten bills
				ItemStack stack6 = new ItemStack(ItemRegistry.DOLLAR_TEN.get());
				stack6.setCount((int) Math.floor(namount / 10));
				ItemHandlerHelper.giveItemToPlayer(player, stack6);
				namount -= stack6.getCount() * 10;

				player.displayClientMessage(new TextComponent("Withdrawal successful. Money left over: " + ChatFormatting.GOLD + "$" + namount).withStyle(ChatFormatting.GREEN), false);
				debounce = false;
			});
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		iCurrency.addNetworkMessage(WithdrawButtonMessage.class, WithdrawButtonMessage::buffer, WithdrawButtonMessage::new, WithdrawButtonMessage::handler);
	}
}
