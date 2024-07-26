
package com.vikkivuk.icurrency.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.Map;
import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.AcknowledgeTransactionResultCRMenu;
import com.vikkivuk.icurrency.procedures.AcknowledgeTransactionPressedProcedure;
import com.vikkivuk.icurrency.IcurrencyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record AcknowledgeTransactionResultCRButtonMessage(int buttonID, int x, int y, int z, HashMap<String, String> textstate) implements CustomPacketPayload {

	public static final Type<AcknowledgeTransactionResultCRButtonMessage> TYPE = new Type<>(new ResourceLocation(IcurrencyMod.MODID, "acknowledge_transaction_result_cr_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, AcknowledgeTransactionResultCRButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, AcknowledgeTransactionResultCRButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
		writeTextState(message.textstate, buffer);
	}, (RegistryFriendlyByteBuf buffer) -> new AcknowledgeTransactionResultCRButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), readTextState(buffer)));
	@Override
	public Type<AcknowledgeTransactionResultCRButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final AcknowledgeTransactionResultCRButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				Player entity = context.player();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				HashMap<String, String> textstate = message.textstate;
				handleButtonAction(entity, buttonID, x, y, z, textstate);
			}).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z, HashMap<String, String> textstate) {
		Level world = entity.level();
		HashMap guistate = AcknowledgeTransactionResultCRMenu.guistate;
		// connect EditBox and CheckBox to guistate
		for (Map.Entry<String, String> entry : textstate.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			guistate.put(key, value);
		}
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			AcknowledgeTransactionPressedProcedure.execute(world, x, y, z, entity);
		}
	}

	private static void writeTextState(HashMap<String, String> map, RegistryFriendlyByteBuf buffer) {
		buffer.writeInt(map.size());
		for (Map.Entry<String, String> entry : map.entrySet()) {
			writeComponent(buffer, Component.literal(entry.getKey()));
			writeComponent(buffer, Component.literal(entry.getValue()));
		}
	}

	private static HashMap<String, String> readTextState(RegistryFriendlyByteBuf buffer) {
		int size = buffer.readInt();
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < size; i++) {
			String key = readComponent(buffer).getString();
			String value = readComponent(buffer).getString();
			map.put(key, value);
		}
		return map;
	}

	private static Component readComponent(RegistryFriendlyByteBuf buffer) {
		return ComponentSerialization.TRUSTED_STREAM_CODEC.decode(buffer);
	}

	private static void writeComponent(RegistryFriendlyByteBuf buffer, Component component) {
		ComponentSerialization.TRUSTED_STREAM_CODEC.encode(buffer, component);
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		IcurrencyMod.addNetworkMessage(AcknowledgeTransactionResultCRButtonMessage.TYPE, AcknowledgeTransactionResultCRButtonMessage.STREAM_CODEC, AcknowledgeTransactionResultCRButtonMessage::handleData);
	}
}
