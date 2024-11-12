package com.vikkivuk.mcurrency.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.vikkivuk.mcurrency.McurrencyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class McurrencyModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, McurrencyMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		McurrencyMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
			PlayerVariables clone = new PlayerVariables();
			clone.money = original.money;
			clone.cards = original.cards;
			clone.transactions = original.transactions;
			if (!event.isWasDeath()) {
				clone.refresh_cards = original.refresh_cards;
				clone.cash_disabled = original.cash_disabled;
				clone.load_more_disabled = original.load_more_disabled;
				clone.card_disabled = original.card_disabled;
				clone.do_crtick = original.do_crtick;
				clone.card_selected = original.card_selected;
				clone.selected_card_holder = original.selected_card_holder;
				clone.current_slot_cr = original.current_slot_cr;
				clone.max_slots_cr = original.max_slots_cr;
			}
			event.getEntity().setData(PLAYER_VARIABLES, clone);
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		public double money = 0;
		public HashMap<String, Vec3> cards = new HashMap<String, Vec3>();
		public ArrayList<Vec3> transactions = new ArrayList<Vec3>();
		public boolean refresh_cards = false;
		public boolean cash_disabled = false;
		public boolean load_more_disabled = false;
		public boolean card_disabled = false;
		public boolean do_crtick = false;
		public Vec3 card_selected = Vec3.ZERO;
		public String selected_card_holder = "\"\"";
		public double current_slot_cr = 0.0;
		public double max_slots_cr = 8.0;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("money", money);
			{
				CompoundTag compoundTag = new CompoundTag();
				for (Map.Entry<String, Vec3> entry : this.cards.entrySet()) {
					Vec3 vec3 = entry.getValue();
					vec3 = vec3 == null ? Vec3.ZERO : vec3;
					ListTag listTag = new ListTag();
					listTag.addTag(0, DoubleTag.valueOf(vec3.x()));
					listTag.addTag(1, DoubleTag.valueOf(vec3.y()));
					listTag.addTag(2, DoubleTag.valueOf(vec3.z()));
					compoundTag.put(entry.getKey(), listTag);
				}
				nbt.put("cards", compoundTag);
			}
			{
				ListTag parentListTag = new ListTag();
				int size = this.transactions.size();
				for (int i = 0; i < size; ++i) {
					Vec3 vec3 = this.transactions.get(i);
					vec3 = vec3 == null ? Vec3.ZERO : vec3;
					ListTag childListTag = new ListTag();
					childListTag.addTag(0, DoubleTag.valueOf(vec3.x()));
					childListTag.addTag(1, DoubleTag.valueOf(vec3.y()));
					childListTag.addTag(2, DoubleTag.valueOf(vec3.z()));
					parentListTag.addTag(i, childListTag);
				}
				nbt.put("transactions", parentListTag);
			}
			nbt.putBoolean("refresh_cards", refresh_cards);
			nbt.putBoolean("cash_disabled", cash_disabled);
			nbt.putBoolean("load_more_disabled", load_more_disabled);
			nbt.putBoolean("card_disabled", card_disabled);
			nbt.putBoolean("do_crtick", do_crtick);
			{
				this.card_selected = this.card_selected == null ? Vec3.ZERO : this.card_selected;
				ListTag listTag = new ListTag();
				listTag.addTag(0, DoubleTag.valueOf(this.card_selected.x()));
				listTag.addTag(1, DoubleTag.valueOf(this.card_selected.y()));
				listTag.addTag(2, DoubleTag.valueOf(this.card_selected.z()));
				nbt.put("card_selected", listTag);
			}
			nbt.putString("selected_card_holder", selected_card_holder);
			nbt.putDouble("current_slot_cr", current_slot_cr);
			nbt.putDouble("max_slots_cr", max_slots_cr);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			money = nbt.getDouble("money");
			{
				this.cards = new HashMap<>();
				CompoundTag compoundTag = nbt.getCompound("cards");
				for (String name : compoundTag.getAllKeys()) {
					ListTag listTag = compoundTag.getList(name, 6);
					this.cards.put(name, new Vec3(listTag.getDouble(0), listTag.getDouble(1), listTag.getDouble(2)));
				}
			}
			{
				this.transactions = new ArrayList<>();
				for (Tag childTag : nbt.getList("transactions", 9))
					if (childTag instanceof ListTag childListTag)
						this.transactions.add(new Vec3(childListTag.getDouble(0), childListTag.getDouble(1), childListTag.getDouble(2)));
			}
			refresh_cards = nbt.getBoolean("refresh_cards");
			cash_disabled = nbt.getBoolean("cash_disabled");
			load_more_disabled = nbt.getBoolean("load_more_disabled");
			card_disabled = nbt.getBoolean("card_disabled");
			do_crtick = nbt.getBoolean("do_crtick");
			{
				ListTag listTag = nbt.getList("card_selected", 6);
				this.card_selected = new Vec3(listTag.getDouble(0), listTag.getDouble(1), listTag.getDouble(2));
			}
			selected_card_holder = nbt.getString("selected_card_holder");
			current_slot_cr = nbt.getDouble("current_slot_cr");
			max_slots_cr = nbt.getDouble("max_slots_cr");
		}

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(McurrencyMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}
