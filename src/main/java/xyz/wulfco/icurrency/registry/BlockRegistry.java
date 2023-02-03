package xyz.wulfco.icurrency.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.objects.blocks.atm.ATMBlock;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, iCurrency.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, iCurrency.MOD_ID);

    public static final RegistryObject<Block> ATM_BLOCK = BLOCKS.register("atm_block", ATMBlock::new);
    public static final RegistryObject<Item> ATM_BLOCK_ITEM = block(iCurrency.TAB);

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ATMBlock.registerRenderLayer();
        }
    }

    private static RegistryObject<Item> block(CreativeModeTab tab) {
        return ITEMS.register(BlockRegistry.ATM_BLOCK.getId().getPath(), () -> new BlockItem(BlockRegistry.ATM_BLOCK.get(), new Item.Properties().tab(tab)));
    }
}
