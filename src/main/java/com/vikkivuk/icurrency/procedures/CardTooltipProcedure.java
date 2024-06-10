package com.vikkivuk.icurrency.procedures;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

import java.util.List;

import com.vikkivuk.icurrency.init.IcurrencyModItems;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class CardTooltipProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getItemStack(), event.getToolTip());
	}

	public static void execute(ItemStack itemstack, List<Component> tooltip) {
		execute(null, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
		if (tooltip == null)
			return;
		if (itemstack.getItem() == IcurrencyModItems.DEBIT_CARD.get()) {
			if (itemstack.getOrCreateTag().getBoolean("activated")) {
				tooltip.add(Component.literal(("\u00A77Card Holder: " + "\u00A7f" + itemstack.getOrCreateTag().getString("holder"))));
				tooltip.add(Component.literal(("\u00A77Card Number: " + "\u00A7f" + Math.round(itemstack.getOrCreateTag().getDouble("h1n")) + " " + Math.round(itemstack.getOrCreateTag().getDouble("h2n")))));
				tooltip.add(Component.literal(("\u00A77Card CVC: " + "\u00A7f" + Math.round(itemstack.getOrCreateTag().getDouble("cvc")))));
			} else {
				tooltip.add(Component.literal("\u00A77This card hasnt been activated yet"));
			}
		} else if (itemstack.getItem() == IcurrencyModItems.RECEIPT.get()) {
			if (itemstack.getOrCreateTag().getBoolean("valid")) {
				tooltip.add(Component.literal(("\u00A77Cashier: " + "\u00A7f" + itemstack.getOrCreateTag().getString("cashier"))));
				tooltip.add(Component.literal(("\u00A77Cash register: " + "\u00A7f" + itemstack.getOrCreateTag().getString("cr_name"))));
				tooltip.add(Component.literal("\u00A77-------------------"));
				tooltip.add(Component.literal(("\u00A77Product: " + "\u00A7f" + itemstack.getOrCreateTag().getString("product"))));
				tooltip.add(Component.literal(("\u00A77Price: " + "\u00A7f" + itemstack.getOrCreateTag().getDouble("price"))));
				tooltip.add(Component.literal(("\u00A77Tip: " + "\u00A7f" + Math.round(itemstack.getOrCreateTag().getDouble("tip")) + "%")));
				tooltip.add(Component.literal("\u00A77-------------------"));
				tooltip.add(Component.literal(("\u00A77Total: " + "\u00A7f\u01B5" + itemstack.getOrCreateTag().getDouble("total"))));
			} else {
				tooltip.add(Component.literal("\u00A77This receipt is missing data"));
			}
		}
	}
}
