
package xyz.wulfco.icurrency.network;

import net.minecraft.client.gui.components.EditBox;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.world.inventory.TransferMenu;

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

		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		iCurrency.addNetworkMessage(TransferButtonMessage.class, TransferButtonMessage::buffer, TransferButtonMessage::new, TransferButtonMessage::handler);
	}
}
