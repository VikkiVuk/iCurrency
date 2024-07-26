package com.vikkivuk.icurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.TransferMoneyMenu;
import com.vikkivuk.icurrency.network.TransferMoneyButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class TransferMoneyScreen extends AbstractContainerScreen<TransferMoneyMenu> {
	private final static HashMap<String, Object> guistate = TransferMoneyMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox receiver;
	EditBox amount;
	Button button_empty;

	public TransferMoneyScreen(TransferMoneyMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 150;
		this.imageHeight = 120;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof TransferMoneyScreen sc) {
			textstate.put("textin:receiver", sc.receiver.getValue());
			textstate.put("textin:amount", sc.amount.getValue());

		}
		return textstate;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/transfer_money.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		receiver.render(guiGraphics, mouseX, mouseY, partialTicks);
		amount.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (receiver.isFocused())
			return receiver.keyPressed(key, b, c);
		if (amount.isFocused())
			return amount.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String receiverValue = receiver.getValue();
		String amountValue = amount.getValue();
		super.resize(minecraft, width, height);
		receiver.setValue(receiverValue);
		amount.setValue(amountValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.transfer_money.label_amount"), 59, 11, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.transfer_money.label_receiver"), 55, 47, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		receiver = new EditBox(this.font, this.leftPos + 16, this.topPos + 57, 118, 18, Component.translatable("gui.icurrency.transfer_money.receiver"));
		receiver.setMaxLength(32767);
		guistate.put("text:receiver", receiver);
		this.addWidget(this.receiver);
		amount = new EditBox(this.font, this.leftPos + 16, this.topPos + 21, 118, 18, Component.translatable("gui.icurrency.transfer_money.amount"));
		amount.setMaxLength(32767);
		guistate.put("text:amount", amount);
		this.addWidget(this.amount);
		button_empty = Button.builder(Component.translatable("gui.icurrency.transfer_money.button_empty"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new TransferMoneyButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				TransferMoneyButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 15, this.topPos + 88, 119, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
	}
}
