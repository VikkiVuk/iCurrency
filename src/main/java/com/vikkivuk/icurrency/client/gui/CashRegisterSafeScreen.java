package com.vikkivuk.icurrency.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.CashRegisterSafeMenu;
import com.vikkivuk.icurrency.procedures.ShowLoadMoreEnabledProcedure;
import com.vikkivuk.icurrency.procedures.ShowLoadMoreDisabledProcedure;

import com.mojang.blaze3d.systems.RenderSystem;

public class CashRegisterSafeScreen extends AbstractContainerScreen<CashRegisterSafeMenu> {
	private final static HashMap<String, Object> guistate = CashRegisterSafeMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final static HashMap<String, String> textstate = new HashMap<>();
	Button button_withdraw_all;
	Button button_load_more;
	Button button_empty;
	ImageButton imagebutton_load_more_disabled;

	public CashRegisterSafeScreen(CashRegisterSafeMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 165;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/cash_register_safe.png");

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
		if (ShowLoadMoreDisabledProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_safe.label_load_more"), 107, 41, -3355444, false);
	}

	@Override
	public void init() {
		super.init();
		button_withdraw_all = Button.builder(Component.translatable("gui.icurrency.cash_register_safe.button_withdraw_all"), e -> {
		}).bounds(this.leftPos + 7, this.topPos + 35, 87, 20).build();
		guistate.put("button:button_withdraw_all", button_withdraw_all);
		this.addRenderableWidget(button_withdraw_all);
		button_load_more = Button.builder(Component.translatable("gui.icurrency.cash_register_safe.button_load_more"), e -> {
		}).bounds(this.leftPos + 96, this.topPos + 35, 72, 20).build(builder -> new Button(builder) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				if (ShowLoadMoreEnabledProcedure.execute(entity))
					super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		});
		guistate.put("button:button_load_more", button_load_more);
		this.addRenderableWidget(button_load_more);
		button_empty = Button.builder(Component.translatable("gui.icurrency.cash_register_safe.button_empty"), e -> {
		}).bounds(this.leftPos + 7, this.topPos + 58, 161, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		imagebutton_load_more_disabled = new ImageButton(this.leftPos + 96, this.topPos + 35, 72, 20,
				new WidgetSprites(new ResourceLocation("icurrency:textures/screens/load_more_disabled.png"), new ResourceLocation("icurrency:textures/screens/load_more_disabled.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (ShowLoadMoreDisabledProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_load_more_disabled", imagebutton_load_more_disabled);
		this.addRenderableWidget(imagebutton_load_more_disabled);
	}
}
