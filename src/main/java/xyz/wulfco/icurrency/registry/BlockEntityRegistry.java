package xyz.wulfco.icurrency.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.objects.blocks.atm.ATMBlockEntity;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, iCurrency.MOD_ID);
    public static final RegistryObject<BlockEntityType<ATMBlockEntity>> ATM_BLOCK = BLOCK_ENTITIES.register("atm_block_entity", () -> BlockEntityType.Builder.of(ATMBlockEntity::new, BlockRegistry.ATM_BLOCK.get()).build(null));
}
