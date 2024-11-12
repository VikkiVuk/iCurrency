
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.mcurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import com.vikkivuk.mcurrency.block.entity.CashRegisterBlockEntity;
import com.vikkivuk.mcurrency.block.entity.ATMBlockEntity;
import com.vikkivuk.mcurrency.McurrencyMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class McurrencyModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, McurrencyMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> ATM = register("atm", McurrencyModBlocks.ATM, ATMBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> CASH_REGISTER = register("cash_register", McurrencyModBlocks.CASH_REGISTER, CashRegisterBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ATM.get(), (blockEntity, side) -> ((ATMBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CASH_REGISTER.get(), (blockEntity, side) -> ((CashRegisterBlockEntity) blockEntity).getItemHandler());
	}
}
