
/*
*    MCreator note: This file will be REGENERATED on each build.
*/
package xyz.vikkivuk.icurrency.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.common.BasicItemListing;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IcurrencyModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
		if (event.getType() == VillagerProfession.ARMORER) {
			trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 2),

					new ItemStack(IcurrencyModItems.ONE_DOLLAR_BILL.get()), 50, 5, 0.05f));
			trades.get(2).add(new BasicItemListing(new ItemStack(Items.NETHERITE_INGOT, 2),

					new ItemStack(IcurrencyModItems.HUNDRED_DOLLAR_BILL.get()), 10, 15, 0.05f));
		}
	}
}
