package com.vikkivuk.mcurrency.client.gui;

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

import com.vikkivuk.mcurrency.world.inventory.EnterPinMenu;
import com.vikkivuk.mcurrency.network.EnterPinButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class EnterPinScreen extends AbstractContainerScreen<EnterPinMenu> {
	private final static HashMap<String, Object> guistate = EnterPinMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox pin;
	Button button_empty;

	public EnterPinScreen(EnterPinMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 142;
		this.imageHeight = 77;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof EnterPinScreen sc) {
			textstate.put("textin:pin", sc.pin.getValue());

		}
		return textstate;
	}

	private static final ResourceLocation texture = new ResourceLocation("mcurrency:textures/screens/enter_pin.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		pin.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (pin.isFocused())
			return pin.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String pinValue = pin.getValue();
		super.resize(minecraft, width, height);
		pin.setValue(pinValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.enter_pin.label_set_card_pin"), 33, 9, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		pin = new EditBox(this.font, this.leftPos + 12, this.topPos + 24, 118, 18, Component.translatable("gui.mcurrency.enter_pin.pin")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.enter_pin.pin").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.enter_pin.pin").getString());
				else
					setSuggestion(null);
			}
		};
		pin.setMaxLength(32767);
		pin.setSuggestion(Component.translatable("gui.mcurrency.enter_pin.pin").getString());
		guistate.put("text:pin", pin);
		this.addWidget(this.pin);
		button_empty = Button.builder(Component.translatable("gui.mcurrency.enter_pin.button_empty"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new EnterPinButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				EnterPinButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 12, this.topPos + 48, 119, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
	}
}
