package xyz.wulfco.icurrency;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.wulfco.icurrency.capabilities.ATM.ATMCapabilityProvider;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapabilityProvider;
import xyz.wulfco.icurrency.objects.blocks.atm.ATMBlock;
import xyz.wulfco.icurrency.objects.blocks.atm.ATMBlockEntity;

@Mod.EventBusSubscriber(modid = iCurrency.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void attachCapabilityEntity(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(iCurrency.MOD_ID, "wallet"), new WalletCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void attachCapabilityBlock(final AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof ATMBlockEntity) {
            if (!event.getObject().getCapability(ATMCapabilityProvider.ATM_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(iCurrency.MOD_ID, "atm"), new ATMCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent(oldStore -> {
                event.getPlayer().getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(WalletCapabilityProvider.class);
    }
}
