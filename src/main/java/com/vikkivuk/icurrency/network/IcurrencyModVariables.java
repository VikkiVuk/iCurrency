package com.vikkivuk.icurrency.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.vikkivuk.icurrency.IcurrencyMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IcurrencyModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, IcurrencyMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		IcurrencyMod.addNetworkMessage(PlayerVariablesSyncMessage.ID, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handleData);
	}

	@Mod.EventBusSubscriber
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
				clone.set_pin = original.set_pin;
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
		public boolean load_more_disabled = true;
		public boolean card_disabled = false;
		public boolean do_crtick = false;
		public Vec3 card_selected = Vec3.ZERO;
		public String selected_card_holder = "\"\"";
		public double set_pin = 0;

		@Override
		public CompoundTag serializeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("money", money);
			nbt.put("cards", (new Function<HashMap<String, Vec3>, CompoundTag>() {
				@Override
				public CompoundTag apply(HashMap<String, Vec3> hashMap) {
					CompoundTag compoundTag = new CompoundTag();
					for (Map.Entry<String, Vec3> entry : hashMap.entrySet()) {
						Vec3 vec3 = entry.getValue();
						ListTag listTag = new ListTag();
						listTag.addTag(0, DoubleTag.valueOf(vec3.x()));
						listTag.addTag(1, DoubleTag.valueOf(vec3.y()));
						listTag.addTag(2, DoubleTag.valueOf(vec3.z()));
						compoundTag.put(entry.getKey(), listTag);
					}
					return compoundTag;
				}
			}).apply(cards));
			nbt.put("transactions", (new Function<ArrayList<Vec3>, ListTag>() {
				@Override
				public ListTag apply(ArrayList<Vec3> arrayList) {
					ListTag parentListTag = new ListTag();
					for (int i = 0; i < arrayList.size(); ++i) {
						Vec3 vec3 = arrayList.get(i);
						ListTag childListTag = new ListTag();
						childListTag.addTag(0, DoubleTag.valueOf(vec3.x()));
						childListTag.addTag(1, DoubleTag.valueOf(vec3.y()));
						childListTag.addTag(2, DoubleTag.valueOf(vec3.z()));
						parentListTag.addTag(i, childListTag);
					}
					return parentListTag;
				}
			}).apply(transactions));
			nbt.putBoolean("refresh_cards", refresh_cards);
			nbt.putBoolean("cash_disabled", cash_disabled);
			nbt.putBoolean("load_more_disabled", load_more_disabled);
			nbt.putBoolean("card_disabled", card_disabled);
			nbt.putBoolean("do_crtick", do_crtick);
			nbt.put("card_selected", (new Function<Vec3, ListTag>() {
				@Override
				public ListTag apply(Vec3 vec3) {
					ListTag listTag = new ListTag();
					listTag.addTag(0, DoubleTag.valueOf(vec3.x()));
					listTag.addTag(1, DoubleTag.valueOf(vec3.y()));
					listTag.addTag(2, DoubleTag.valueOf(vec3.z()));
					return listTag;
				}
			}).apply(card_selected));
			nbt.putString("selected_card_holder", selected_card_holder);
			nbt.putDouble("set_pin", set_pin);
			return nbt;
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			money = nbt.getDouble("money");
			cards = (new Function<CompoundTag, HashMap<String, Vec3>>() {
				@Override
				public HashMap<String, Vec3> apply(CompoundTag compoundTag) {
					HashMap<String, Vec3> hashMap = new HashMap<>();
					for (String name : compoundTag.getAllKeys()) {
						ListTag listTag = compoundTag.getList(name, 6);
						hashMap.put(name, new Vec3(listTag.getDouble(0), listTag.getDouble(1), listTag.getDouble(2)));
					}
					return hashMap;
				}
			}).apply(nbt.getCompound("cards"));
			transactions = (new Function<ListTag, ArrayList<Vec3>>() {
				@Override
				public ArrayList<Vec3> apply(ListTag parentListTag) {
					ArrayList<Vec3> arrayList = new ArrayList<>();
					for (Tag childTag : parentListTag)
						if (childTag instanceof ListTag childListTag)
							arrayList.add(new Vec3(childListTag.getDouble(0), childListTag.getDouble(1), childListTag.getDouble(2)));
					return arrayList;
				}
			}).apply(nbt.getList("transactions", 9));
			refresh_cards = nbt.getBoolean("refresh_cards");
			cash_disabled = nbt.getBoolean("cash_disabled");
			load_more_disabled = nbt.getBoolean("load_more_disabled");
			card_disabled = nbt.getBoolean("card_disabled");
			do_crtick = nbt.getBoolean("do_crtick");
			card_selected = (new Function<ListTag, Vec3>() {
				@Override
				public Vec3 apply(ListTag listTag) {
					return new Vec3(listTag.getDouble(0), listTag.getDouble(1), listTag.getDouble(2));
				}
			}).apply(nbt.getList("card_selected", 6));
			selected_card_holder = nbt.getString("selected_card_holder");
			set_pin = nbt.getDouble("set_pin");
		}

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PacketDistributor.PLAYER.with(serverPlayer).send(new PlayerVariablesSyncMessage(this));
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final ResourceLocation ID = new ResourceLocation(IcurrencyMod.MODID, "player_variables_sync");

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this(new PlayerVariables());
			this.data.deserializeNBT(buffer.readNbt());
		}

		@Override
		public void write(final FriendlyByteBuf buffer) {
			buffer.writeNbt(data.serializeNBT());
		}

		@Override
		public ResourceLocation id() {
			return ID;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final PlayPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.workHandler().submitAsync(() -> Minecraft.getInstance().player.getData(PLAYER_VARIABLES).deserializeNBT(message.data.serializeNBT())).exceptionally(e -> {
					context.packetHandler().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}
