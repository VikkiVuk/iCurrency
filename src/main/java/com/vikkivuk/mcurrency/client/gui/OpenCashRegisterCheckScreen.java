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

import com.vikkivuk.mcurrency.world.inventory.OpenCashRegisterCheckMenu;
import com.vikkivuk.mcurrency.network.OpenCashRegisterCheckButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class OpenCashRegisterCheckScreen extends AbstractContainerScreen<OpenCashRegisterCheckMenu> {
	private final static HashMap<String, Object> guistate = OpenCashRegisterCheckMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox pass_check;
	Button button_open_cash_register;

	public OpenCashRegisterCheckScreen(OpenCashRegisterCheckMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 152;
		this.imageHeight = 63;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof OpenCashRegisterCheckScreen sc) {
			textstate.put("textin:pass_check", sc.pass_check.getValue());

		}
		return textstate;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("mcurrency:textures/screens/open_cash_register_check.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		pass_check.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (pass_check.isFocused())
			return pass_check.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String pass_checkValue = pass_check.getValue();
		super.resize(minecraft, width, height);
		pass_check.setValue(pass_checkValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		pass_check = new EditBox(this.font, this.leftPos + 17, this.topPos + 11, 118, 18, Component.translatable("gui.mcurrency.open_cash_register_check.pass_check")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.open_cash_register_check.pass_check").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.open_cash_register_check.pass_check").getString());
				else
					setSuggestion(null);
			}
		};
		pass_check.setMaxLength(32767);
		pass_check.setSuggestion(Component.translatable("gui.mcurrency.open_cash_register_check.pass_check").getString());
		guistate.put("text:pass_check", pass_check);
		this.addWidget(this.pass_check);
		button_open_cash_register = Button.builder(Component.translatable("gui.mcurrency.open_cash_register_check.button_open_cash_register"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new OpenCashRegisterCheckButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				OpenCashRegisterCheckButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 17, this.topPos + 33, 119, 20).build();
		guistate.put("button:button_open_cash_register", button_open_cash_register);
		this.addRenderableWidget(button_open_cash_register);
	}
}
