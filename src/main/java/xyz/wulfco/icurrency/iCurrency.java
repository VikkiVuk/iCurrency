package xyz.wulfco.icurrency;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.wulfco.icurrency.client.screens.CrackedMinecraftScreen;
import xyz.wulfco.icurrency.client.screens.ErrorScreen;
import xyz.wulfco.icurrency.util.NetworkUtils;

import javax.json.JsonObject;
import java.util.Objects;

@Mod("icurrency")
public class iCurrency {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "icurrency";

    public iCurrency() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {}

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    Boolean shownCrackedScreen = false;

    @SubscribeEvent
    public void screenEvent(ScreenEvent event) {
        if(event.getScreen() instanceof TitleScreen) {
            if (shownCrackedScreen) return;

            JsonObject response = NetworkUtils.get("https://icurrency.wulfco.xyz");

            if (!Objects.equals(response.getString("status"), "ok")) {
                LOGGER.warn("iCurrency >>> API is offline!");
                Minecraft.getInstance().setScreen(new ErrorScreen(new TextComponent(ChatFormatting.RED + "iCurrency API is offline!"), new TextComponent("Please try again later."), null));
                return;
            }

            boolean cracked = Objects.isNull(Minecraft.getInstance().getGame().getCurrentSession());
            if (!cracked) return;

            LOGGER.info("iCurrency >>> Cracked Minecraft detected! Replacing TitleScreen with CrackedMinecraftScreen...");
            Minecraft.getInstance().setScreen(new CrackedMinecraftScreen());
            shownCrackedScreen = true;
        }
    }
}
