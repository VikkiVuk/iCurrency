package com.vikkivuk.icurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.CashRegisterOverviewMenu;
import com.vikkivuk.icurrency.network.CashRegisterOverviewButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class CashRegisterOverviewScreen extends AbstractContainerScreen<CashRegisterOverviewMenu> {
	private final static HashMap<String, Object> guistate = CashRegisterOverviewMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final static HashMap<String, String> textstate = new HashMap<>();
	Button button_empty;
	Button button_empty1;

	public CashRegisterOverviewScreen(CashRegisterOverviewMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 208;
		this.imageHeight = 86;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/cash_register_overview.png");

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
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_overview.label_welcome"), 16, 12, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_empty = Button.builder(Component.translatable("gui.icurrency.cash_register_overview.button_empty"), e -> {
			if (true) {
				PacketDistributor.SERVER.noArg().send(new CashRegisterOverviewButtonMessage(0, x, y, z, textstate));
				CashRegisterOverviewButtonMessage.handleButtonAction(entity, 0, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 7, this.topPos + 29, 193, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.icurrency.cash_register_overview.button_empty1"), e -> {
			if (true) {
				PacketDistributor.SERVER.noArg().send(new CashRegisterOverviewButtonMessage(1, x, y, z, textstate));
				CashRegisterOverviewButtonMessage.handleButtonAction(entity, 1, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 7, this.topPos + 55, 193, 20).build();
		guistate.put("button:button_empty1", button_empty1);
		this.addRenderableWidget(button_empty1);
	}
}
