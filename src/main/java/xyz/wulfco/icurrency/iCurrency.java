package xyz.wulfco.icurrency;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import xyz.wulfco.icurrency.client.screen.ErrorScreen;
import xyz.wulfco.icurrency.registry.BlockEntityRegistry;
import xyz.wulfco.icurrency.registry.BlockRegistry;
import xyz.wulfco.icurrency.registry.ItemRegistry;
import xyz.wulfco.icurrency.util.NetworkHandler;

import javax.json.JsonObject;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod("icurrency")
public class iCurrency {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "icurrency";
    public static final String MOD_NAME = "iCurrency";
    public static final String MOD_VERSION = "1.0.0";

    private static final String PROTOCOL_VERSION = "1";
    public static boolean isCracked = false;
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
        BlockEntityRegistry.BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.CARD.get());
        }
    };

    private void setup(final FMLCommonSetupEvent event) {}

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    boolean shownScreen = false;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void screenEvent(ScreenEvent event) {
        if(event.getScreen() instanceof TitleScreen) {
            if (shownScreen) return;
            shownScreen = true;

            JsonObject updateJson = NetworkHandler.get("https://gist.githubusercontent.com/VikkiVuk/a8a781b2bedec54534ca3e25191f41b1/raw");
            if (updateJson != null) {
                if (!updateJson.getJsonObject("promos").getString("1.18.2-latest").equals(MOD_VERSION)) {
                    Minecraft.getInstance().setScreen(new ErrorScreen(new TextComponent(ChatFormatting.YELLOW + "iCurrency is outdated!"), new TextComponent("Please update iCurrency to get the latest features and bug fixes!"), null));
                }
            }
        }
    }
}
