
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.icurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;

import com.vikkivuk.icurrency.block.CashRegisterBlock;
import com.vikkivuk.icurrency.block.ATMBlock;
import com.vikkivuk.icurrency.block.ACMBlock;
import com.vikkivuk.icurrency.IcurrencyMod;

public class IcurrencyModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(IcurrencyMod.MODID);
	public static final DeferredHolder<Block, Block> ATM = REGISTRY.register("atm", ATMBlock::new);
	public static final DeferredHolder<Block, Block> CASH_REGISTER = REGISTRY.register("cash_register", CashRegisterBlock::new);
	public static final DeferredHolder<Block, Block> ACM = REGISTRY.register("acm", ACMBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
