
package xyz.wulfco.icurrency.network;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.util.FileHandler;
import xyz.wulfco.icurrency.util.NetworkHandler;
import xyz.wulfco.icurrency.world.inventory.TransferMenu;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TransferButtonMessage {
	private final int buttonID, x, y, z;

	public TransferButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public TransferButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(TransferButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(TransferButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap<String, Object> guistate = TransferMenu.guistate;

		if (buttonID == 0) {
			final EditBox receiver = (EditBox) guistate.get("text:receiver");
			final EditBox amount = (EditBox) guistate.get("text:amount");
			final JsonObject session = NetworkHandler.decodeJson(FileHandler.read("_ic-session.json"));
			JsonObject response;

			if (session == null) {
				entity.displayClientMessage(new TextComponent("You are not logged in!").withStyle(ChatFormatting.RED), false);
				return;
			}

			if (receiver.getValue().isEmpty()) {
				entity.displayClientMessage(new TextComponent("Please enter a receiver!").withStyle(ChatFormatting.RED), false);
				return;
			}

			boolean cracked = Objects.isNull(Minecraft.getInstance().getGame().getCurrentSession());

			if (cracked) {
				if (Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer()).isSingleplayer()) {
					entity.displayClientMessage(new TextComponent(ChatFormatting.RED + "You can't use this feature in singleplayer!"), false);
					return;
				} else {
					for (Component component : Minecraft.getInstance().getCurrentServer().playerList) {
						System.out.println("iCurrency >>> " + component.getString());
					}

					response = NetworkHandler.post("https://icurrency.wulfco.xyz/transfer/cracked", Json.createObjectBuilder()
							.add("sender", Json.createObjectBuilder()
									.add("icid", session.getString("icid"))
									.add("session", session.getString("session"))
									.add("name", entity.getName().getString())
							)
							.add("", session.getString("session"))
							.add("amount", amount.getValue())
							.add("server", Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).ip)
							.build());
				}

				if (response != null) {
					if (response.getBoolean("success")) {
						entity.displayClientMessage(new TextComponent("Successfully transferred " + amount.getValue() + " money to " + receiver.getValue() + "!").withStyle(ChatFormatting.GREEN), false);
					} else {
						entity.displayClientMessage(new TextComponent(response.getString("message")).withStyle(ChatFormatting.RED), false);
					}
				} else {
					entity.displayClientMessage(new TextComponent("An error occurred!").withStyle(ChatFormatting.RED), false);
				}
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		iCurrency.addNetworkMessage(TransferButtonMessage.class, TransferButtonMessage::buffer, TransferButtonMessage::new, TransferButtonMessage::handler);
	}
}
