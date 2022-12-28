package xyz.wulfco.icurrency;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import xyz.wulfco.icurrency.client.screen.CrackedMinecraftScreen;
import xyz.wulfco.icurrency.client.screen.ErrorScreen;
import xyz.wulfco.icurrency.registry.BlockRegistry;
import xyz.wulfco.icurrency.registry.ItemRegistry;
import xyz.wulfco.icurrency.util.FileHandler;
import xyz.wulfco.icurrency.util.NetworkHandler;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod("icurrency")
public class iCurrency {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "icurrency";
    public static boolean cracked = Objects.isNull(Minecraft.getInstance().getGame().getCurrentSession());

    private static final String PROTOCOL_VERSION = "1";
    private static int messageID = 0;

    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public iCurrency() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.CARD.get());
        }
    };

    private void setup(final FMLCommonSetupEvent event) {
        NetworkHandler.get("https://icurrency.wulfco.xyz");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    boolean shownCrackedScreen = false;

    @SubscribeEvent
    public void screenEvent(ScreenEvent event) {
        if(event.getScreen() instanceof TitleScreen) {
            if (shownCrackedScreen) return;

            JsonObject response = NetworkHandler.get("https://icurrency.wulfco.xyz");

            assert response != null;
            if (!Objects.equals(response.getString("status"), "ok")) {
                LOGGER.warn("iCurrency >>> API is offline!");
                Minecraft.getInstance().setScreen(new ErrorScreen(new TextComponent(ChatFormatting.RED + "iCurrency API is offline!"), new TextComponent("Please try again later."), null));
                return;
            }

            boolean cracked = Objects.isNull(Minecraft.getInstance().getGame().getCurrentSession());
            if (!cracked) return;

            LOGGER.info("iCurrency >>> Cracked Minecraft detected! Checking for session file...");
            LOGGER.info("iCurrency >>> If you are not using a cracked version of Minecraft, please report this to the iCurrency developers.");
            if(FileHandler.exists("_ic-session.json") && !Objects.equals(FileHandler.read("_ic-session.json"), "{}")) {
                LOGGER.info("iCurrency >>> Found session file, logging in...");
                JsonObject session = NetworkHandler.decodeJson(FileHandler.read("_ic-session.json"));
                JsonObject loginResponse = NetworkHandler.post("https://icurrency.wulfco.xyz/login/session", Json.createObjectBuilder().add("icid", session.getString("icid")).add("session", session.getString("session")).build());
                assert loginResponse != null;
                if (Objects.equals(loginResponse.getString("status"), "ok")) {
                    LOGGER.info("iCurrency >>> Logged in successfully!");
                    FileHandler.write("_ic-session.json", Json.createObjectBuilder().add("icid", loginResponse.getString("icid")).add("session", loginResponse.getString("session")).build().toString());
                    shownCrackedScreen = true;
                    Minecraft.getInstance().setScreen(new TitleScreen());
                    return;
                } else {
                    LOGGER.info("iCurrency >>> Session is invalid, deleting session...");
                    FileHandler.write("_ic-session.json", "{}");
                }
            } else {
                LOGGER.info("iCurrency >>> Session file not found, redirecting to login...");
            }

            Minecraft.getInstance().setScreen(new CrackedMinecraftScreen());
            shownCrackedScreen = true;
        }
    }
}
