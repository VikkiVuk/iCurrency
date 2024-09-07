package com.vikkivuk.mcurrency.procedures;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

import javax.annotation.Nullable;

import java.util.List;

import com.vikkivuk.mcurrency.init.McurrencyModItems;

@EventBusSubscriber(value = {Dist.CLIENT})
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
		if (itemstack.getItem() == McurrencyModItems.DEBIT_CARD.get()) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("activated")) {
				tooltip.add(Component.literal(("\u00A77Card Holder: " + "\u00A7f" + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder"))));
				tooltip.add(Component.literal(("\u00A77Card Number: " + "\u00A7f" + Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")) + " "
						+ Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")))));
				tooltip.add(Component.literal(("\u00A77Card CVC: " + "\u00A7f" + Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))));
			} else {
				tooltip.add(Component.literal("\u00A77Right click to activate card"));
			}
		} else if (itemstack.getItem() == McurrencyModItems.RECEIPT.get()) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("valid")) {
				tooltip.add(Component.literal(("\u00A77Cashier: " + "\u00A7f" + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("cashier"))));
				tooltip.add(Component.literal(("\u00A77Cash register: " + "\u00A7f" + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("cr_name"))));
				tooltip.add(Component.literal("\u00A77-------------------"));
				tooltip.add(Component.literal(("\u00A77Product: " + "\u00A7f" + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("product"))));
				tooltip.add(Component.literal(("\u00A77Price: " + "\u00A7f\u01B5" + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("price"))));
				tooltip.add(Component.literal(("\u00A77Tip: " + "\u00A7f" + Math.round(itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("tip")) + "%")));
				tooltip.add(Component.literal("\u00A77-------------------"));
				tooltip.add(Component.literal(("\u00A77Total: " + "\u00A7f\u01B5" + itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("total"))));
			} else {
				tooltip.add(Component.literal("\u00A77This receipt is missing data"));
			}
		}
	}
}
