package xyz.wulfco.icurrency.network;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.registry.ItemRegistry;
import xyz.wulfco.icurrency.util.FileHandler;
import xyz.wulfco.icurrency.util.NetworkHandler;
import xyz.wulfco.icurrency.world.inventory.WithdrawMenu;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Objects;
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

	public static void handleButtonAction(Player player, int buttonID, int x, int y, int z) {
		HashMap<String, Object> guistate = WithdrawMenu.guistate;
		
		if (buttonID == 0) {
			double namount = 0;
			if (player == null)
				return;

			if (iCurrency.cracked) {
				JsonObject response;
				final JsonObject session = NetworkHandler.decodeJson(FileHandler.read("_ic-session.json"));
				final EditBox amount = (EditBox) guistate.get("text:amount");

				if (Integer.parseInt(amount.getValue()) < 10) {
					player.displayClientMessage(new TextComponent("You can't withdraw less than 10 Zlatniks!").withStyle(ChatFormatting.RED), false);
					return;
				}

				if (Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer()).isSingleplayer()) {
					response = NetworkHandler.post("https://icurrency.wulfco.xyz/withdraw/cracked", Json.createObjectBuilder()
							.add("icid", session.getString("icid"))
							.add("session", session.getString("session"))
							.add("amount", amount.getValue())
							.add("singleplayer", true)
							.build());
				} else {
					response = NetworkHandler.post("https://icurrency.wulfco.xyz/withdraw/cracked", Json.createObjectBuilder()
							.add("icid", session.getString("icid"))
							.add("session", session.getString("session"))
							.add("amount", amount.getValue())
							.add("singleplayer", false)
							.add("server", Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).ip)
							.build());
				}

				if (response != null) {
					if (response.getString("status").equals("ok")) {
						namount = response.getString("amountwithdrawn").equals("null") ? 0 : Double.parseDouble(response.getString("amountwithdrawn"));

						// Thousand bills
						ItemStack setstack1 = new ItemStack(ItemRegistry.DOLLAR_THOUSAND.get());
						setstack1.setCount((int) Math.floor(namount / 1000));
						ItemHandlerHelper.giveItemToPlayer(player, setstack1);
						namount -= setstack1.getCount() * 1000;
						// Five Hundred bills
						ItemStack setstack2 = new ItemStack(ItemRegistry.DOLLAR_FIVE_HUNDRED.get());
						setstack2.setCount((int) Math.floor(namount / 500));
						ItemHandlerHelper.giveItemToPlayer(player, setstack2);
						namount -= setstack2.getCount() * 500;
						// Hundred bills
						ItemStack setstack3 = new ItemStack(ItemRegistry.DOLLAR_HUNDRED.get());
						setstack3.setCount((int) Math.floor(namount / 100));
						ItemHandlerHelper.giveItemToPlayer(player, setstack3);
						namount -= setstack3.getCount() * 100;
						// Fifty bills
						ItemStack setstack4 = new ItemStack(ItemRegistry.DOLLAR_FIFTY.get());
						setstack4.setCount((int) Math.floor(namount / 50));
						ItemHandlerHelper.giveItemToPlayer(player, setstack4);
						namount -= setstack4.getCount() * 50;
						// Twenty bills
						ItemStack setstack5 = new ItemStack(ItemRegistry.DOLLAR_TWENTY.get());
						setstack5.setCount((int) Math.floor(namount / 20));
						ItemHandlerHelper.giveItemToPlayer(player, setstack5);
						namount -= setstack5.getCount() * 20;
						// Ten bills
						ItemStack setstack6 = new ItemStack(ItemRegistry.DOLLAR_TEN.get());
						setstack6.setCount((int) Math.floor(namount / 10));
						ItemHandlerHelper.giveItemToPlayer(player, setstack6);
						namount -= setstack6.getCount() * 10;

						player.displayClientMessage(new TextComponent("Withdrawal successful.").withStyle(ChatFormatting.GREEN), false);
					} else {
						player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: " + response.getString("error")), false);
					}
				} else {
					player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: Could not connect to server"), false);
				}
			} else {
				player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: Premium isn't supported yet."), false);
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		iCurrency.addNetworkMessage(WithdrawButtonMessage.class, WithdrawButtonMessage::buffer, WithdrawButtonMessage::new, WithdrawButtonMessage::handler);
	}
}
