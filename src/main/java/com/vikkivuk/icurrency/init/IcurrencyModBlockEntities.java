
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import com.vikkivuk.icurrency.block.entity.CashRegisterBlockEntity;
import com.vikkivuk.icurrency.block.entity.ATMBlockEntity;
import com.vikkivuk.icurrency.IcurrencyMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IcurrencyModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IcurrencyMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> ATM = register("atm", IcurrencyModBlocks.ATM, ATMBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> CASH_REGISTER = register("cash_register", IcurrencyModBlocks.CASH_REGISTER, CashRegisterBlockEntity::new);

	private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ATM.get(), (blockEntity, side) -> ((ATMBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CASH_REGISTER.get(), (blockEntity, side) -> ((CashRegisterBlockEntity) blockEntity).getItemHandler());
	}
}