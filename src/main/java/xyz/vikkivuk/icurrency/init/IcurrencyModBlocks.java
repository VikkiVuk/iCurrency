
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package xyz.vikkivuk.icurrency.init;

import xyz.vikkivuk.icurrency.block.AtmBlockBlock;
import xyz.vikkivuk.icurrency.IcurrencyMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;

public class IcurrencyModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, IcurrencyMod.MODID);
	public static final RegistryObject<Block> ATM_BLOCK = REGISTRY.register("atm_block", () -> new AtmBlockBlock());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			AtmBlockBlock.registerRenderLayer();
		}
	}
}
