package com.vikkivuk.mcurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.vikkivuk.mcurrency.world.inventory.ATMEntryPointMenu;
import com.vikkivuk.mcurrency.network.ATMEntryPointButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class ATMEntryPointScreen extends AbstractContainerScreen<ATMEntryPointMenu> {
	private final static HashMap<String, Object> guistate = ATMEntryPointMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_empty;
	Button button_empty1;
	Button button_empty2;
	Button button_empty3;
	Button button_empty4;
	Button button_empty5;

	public ATMEntryPointScreen(ATMEntryPointMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 166;
		this.imageHeight = 166;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof ATMEntryPointScreen sc) {

		}
		return textstate;
	}

	private static final ResourceLocation texture = new ResourceLocation("mcurrency:textures/screens/atm_entry_point.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		button_empty = Button.builder(Component.translatable("gui.mcurrency.atm_entry_point.button_empty"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMEntryPointButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMEntryPointButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 10, 145, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.mcurrency.atm_entry_point.button_empty1"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMEntryPointButtonMessage(1, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMEntryPointButtonMessage.handleButtonAction(entity, 1, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 35, 145, 20).build();
		guistate.put("button:button_empty1", button_empty1);
		this.addRenderableWidget(button_empty1);
		button_empty2 = Button.builder(Component.translatable("gui.mcurrency.atm_entry_point.button_empty2"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMEntryPointButtonMessage(2, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMEntryPointButtonMessage.handleButtonAction(entity, 2, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 60, 145, 20).build();
		guistate.put("button:button_empty2", button_empty2);
		this.addRenderableWidget(button_empty2);
		button_empty3 = Button.builder(Component.translatable("gui.mcurrency.atm_entry_point.button_empty3"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMEntryPointButtonMessage(3, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMEntryPointButtonMessage.handleButtonAction(entity, 3, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 85, 145, 20).build();
		guistate.put("button:button_empty3", button_empty3);
		this.addRenderableWidget(button_empty3);
		button_empty4 = Button.builder(Component.translatable("gui.mcurrency.atm_entry_point.button_empty4"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMEntryPointButtonMessage(4, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMEntryPointButtonMessage.handleButtonAction(entity, 4, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 110, 145, 20).build();
		guistate.put("button:button_empty4", button_empty4);
		this.addRenderableWidget(button_empty4);
		button_empty5 = Button.builder(Component.translatable("gui.mcurrency.atm_entry_point.button_empty5"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ATMEntryPointButtonMessage(5, x, y, z, getEditBoxAndCheckBoxValues()));
				ATMEntryPointButtonMessage.handleButtonAction(entity, 5, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 135, 145, 20).build();
		guistate.put("button:button_empty5", button_empty5);
		this.addRenderableWidget(button_empty5);
	}
}
