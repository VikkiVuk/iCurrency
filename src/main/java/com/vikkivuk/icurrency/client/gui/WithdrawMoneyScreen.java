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

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.WithdrawMoneyMenu;
import com.vikkivuk.icurrency.network.WithdrawMoneyButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class WithdrawMoneyScreen extends AbstractContainerScreen<WithdrawMoneyMenu> {
	private final static HashMap<String, Object> guistate = WithdrawMoneyMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final static HashMap<String, String> textstate = new HashMap<>();
	public static EditBox amount;
	Button button_empty;

	public WithdrawMoneyScreen(WithdrawMoneyMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/withdraw_money.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (amount.isFocused())
			return amount.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.withdraw_money.label_withdraw"), 7, 6, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		amount = new EditBox(this.font, this.leftPos + 8, this.topPos + 17, 118, 18, Component.translatable("gui.icurrency.withdraw_money.amount")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.withdraw_money.amount").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.withdraw_money.amount").getString());
				else
					setSuggestion(null);
			}
		};
		amount.setMaxLength(32767);
		amount.setSuggestion(Component.translatable("gui.icurrency.withdraw_money.amount").getString());
		guistate.put("text:amount", amount);
		this.addWidget(this.amount);
		button_empty = Button.builder(Component.translatable("gui.icurrency.withdraw_money.button_empty"), e -> {
			if (true) {
				textstate.put("textin:amount", amount.getValue());
				PacketDistributor.SERVER.noArg().send(new WithdrawMoneyButtonMessage(0, x, y, z, textstate));
				WithdrawMoneyButtonMessage.handleButtonAction(entity, 0, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 7, this.topPos + 38, 161, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
	}
}