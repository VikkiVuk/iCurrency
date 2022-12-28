package xyz.wulfco.icurrency.network;

import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.objects.items.Card;
import xyz.wulfco.icurrency.world.inventory.*;

import java.util.HashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnterCVCMessage {
    private final int buttonID, x, y, z;

    public EnterCVCMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public EnterCVCMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(EnterCVCMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(EnterCVCMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
        HashMap<String, Object> guistate = EnterCVCMenu.guistate;

        if (entity instanceof ServerPlayer player) {
            final EditBox cvc = (EditBox) guistate.get("text:cvc");

            if (!Card.checkCVC(player.getMainHandItem(), cvc.getValue())) { player.displayClientMessage(new TextComponent(ChatFormatting.RED + "CVC is invalid!"), true); return; }

            NetworkHooks.openGui(player, new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return new TextComponent("ATM");
                }

                @Override
                public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
                    return new ATMMenu(i, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
                }
            }, new BlockPos(x, y, z));
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        iCurrency.addNetworkMessage(EnterCVCMessage.class, EnterCVCMessage::buffer, EnterCVCMessage::new, EnterCVCMessage::handler);
    }
}
