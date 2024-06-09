
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import com.vikkivuk.icurrency.block.CashRegisterBlock;
import com.vikkivuk.icurrency.block.ATMBlock;
import com.vikkivuk.icurrency.IcurrencyMod;

public class IcurrencyModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK, IcurrencyMod.MODID);
	public static final DeferredHolder<Block, Block> ATM = REGISTRY.register("atm", () -> new ATMBlock());
	public static final DeferredHolder<Block, Block> CASH_REGISTER = REGISTRY.register("cash_register", () -> new CashRegisterBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
