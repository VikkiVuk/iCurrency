
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.vikkivuk.mcurrency.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;

import com.vikkivuk.mcurrency.block.CashRegisterBlock;
import com.vikkivuk.mcurrency.block.ATMBlock;
import com.vikkivuk.mcurrency.block.ACMBlock;
import com.vikkivuk.mcurrency.McurrencyMod;

public class McurrencyModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(McurrencyMod.MODID);
	public static final DeferredHolder<Block, Block> ATM = REGISTRY.register("atm", ATMBlock::new);
	public static final DeferredHolder<Block, Block> CASH_REGISTER = REGISTRY.register("cash_register", CashRegisterBlock::new);
	public static final DeferredHolder<Block, Block> ACM = REGISTRY.register("acm", ACMBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
