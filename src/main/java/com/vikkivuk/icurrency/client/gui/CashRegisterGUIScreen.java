package com.vikkivuk.icurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.CashRegisterGUIMenu;
import com.vikkivuk.icurrency.procedures.ShowCashEnabledCRPProcedure;
import com.vikkivuk.icurrency.procedures.ShowCashDisabledCRPProcedure;
import com.vikkivuk.icurrency.procedures.ShowCardEnabledCRPProcedure;
import com.vikkivuk.icurrency.procedures.ShowCardDisabledCRPProcedure;
import com.vikkivuk.icurrency.network.CashRegisterGUIButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class CashRegisterGUIScreen extends AbstractContainerScreen<CashRegisterGUIMenu> {
	private final static HashMap<String, Object> guistate = CashRegisterGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final static HashMap<String, String> textstate = new HashMap<>();
	public static EditBox product;
	public static EditBox price;
	public static EditBox tip;
	Button button_cash;
	Button button_card;
	ImageButton imagebutton_cash_disabled;
	ImageButton imagebutton_cash_disabled1;

	public CashRegisterGUIScreen(CashRegisterGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 149;
		this.imageHeight = 159;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/cash_register_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		product.render(guiGraphics, mouseX, mouseY, partialTicks);
		price.render(guiGraphics, mouseX, mouseY, partialTicks);
		tip.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 61 && mouseX < leftPos + 85 && mouseY > topPos + 47 && mouseY < topPos + 71)
			guiGraphics.renderTooltip(font, Component.translatable("gui.icurrency.cash_register_gui.tooltip_anything_over_z570000_can_only_b"), mouseX, mouseY);
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
		if (product.isFocused())
			return product.keyPressed(key, b, c);
		if (price.isFocused())
			return price.keyPressed(key, b, c);
		if (tip.isFocused())
			return tip.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_gui.label_checkout"), 56, 11, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_gui.label_tip_max_50"), 43, 86, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_gui.label_price"), 61, 50, -12829636, false);
		if (ShowCashDisabledCRPProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_gui.label_cash"), 25, 132, -3355444, false);
		if (ShowCardDisabledCRPProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_gui.label_card"), 98, 132, -3355444, false);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		textstate.put("textin:product", product.getValue());
		textstate.put("textin:price", price.getValue());
		textstate.put("textin:tip", tip.getValue());
		PacketDistributor.SERVER.noArg().send(new CashRegisterGUIMenu.CashRegisterGUIOtherMessage(0, x, y, z, textstate));
		CashRegisterGUIMenu.CashRegisterGUIOtherMessage.handleOtherAction(entity, 0, x, y, z, textstate);
	}

	@Override
	public void init() {
		super.init();
		product = new EditBox(this.font, this.leftPos + 15, this.topPos + 24, 118, 18, Component.translatable("gui.icurrency.cash_register_gui.product")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.product").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.product").getString());
				else
					setSuggestion(null);
			}
		};
		product.setMaxLength(32767);
		product.setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.product").getString());
		guistate.put("text:product", product);
		this.addWidget(this.product);
		price = new EditBox(this.font, this.leftPos + 15, this.topPos + 61, 118, 18, Component.translatable("gui.icurrency.cash_register_gui.price")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.price").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.price").getString());
				else
					setSuggestion(null);
			}
		};
		price.setMaxLength(32767);
		price.setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.price").getString());
		guistate.put("text:price", price);
		this.addWidget(this.price);
		tip = new EditBox(this.font, this.leftPos + 15, this.topPos + 97, 118, 18, Component.translatable("gui.icurrency.cash_register_gui.tip")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.tip").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.tip").getString());
				else
					setSuggestion(null);
			}
		};
		tip.setMaxLength(32767);
		tip.setSuggestion(Component.translatable("gui.icurrency.cash_register_gui.tip").getString());
		guistate.put("text:tip", tip);
		this.addWidget(this.tip);
		button_cash = Button.builder(Component.translatable("gui.icurrency.cash_register_gui.button_cash"), e -> {
			if (ShowCashEnabledCRPProcedure.execute(entity)) {
				textstate.put("textin:product", product.getValue());
				textstate.put("textin:price", price.getValue());
				textstate.put("textin:tip", tip.getValue());
				PacketDistributor.SERVER.noArg().send(new CashRegisterGUIButtonMessage(0, x, y, z, textstate));
				CashRegisterGUIButtonMessage.handleButtonAction(entity, 0, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 14, this.topPos + 126, 46, 20).build(builder -> new Button(builder) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				if (ShowCashEnabledCRPProcedure.execute(entity))
					super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		});
		guistate.put("button:button_cash", button_cash);
		this.addRenderableWidget(button_cash);
		button_card = Button.builder(Component.translatable("gui.icurrency.cash_register_gui.button_card"), e -> {
			if (ShowCardEnabledCRPProcedure.execute(entity)) {
				textstate.put("textin:product", product.getValue());
				textstate.put("textin:price", price.getValue());
				textstate.put("textin:tip", tip.getValue());
				PacketDistributor.SERVER.noArg().send(new CashRegisterGUIButtonMessage(1, x, y, z, textstate));
				CashRegisterGUIButtonMessage.handleButtonAction(entity, 1, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 87, this.topPos + 126, 46, 20).build(builder -> new Button(builder) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				if (ShowCardEnabledCRPProcedure.execute(entity))
					super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		});
		guistate.put("button:button_card", button_card);
		this.addRenderableWidget(button_card);
		imagebutton_cash_disabled = new ImageButton(this.leftPos + 14, this.topPos + 126, 46, 20,
				new WidgetSprites(new ResourceLocation("icurrency:textures/screens/cash_disabled.png"), new ResourceLocation("icurrency:textures/screens/cash_disabled.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (ShowCashDisabledCRPProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_cash_disabled", imagebutton_cash_disabled);
		this.addRenderableWidget(imagebutton_cash_disabled);
		imagebutton_cash_disabled1 = new ImageButton(this.leftPos + 87, this.topPos + 126, 46, 20,
				new WidgetSprites(new ResourceLocation("icurrency:textures/screens/cash_disabled.png"), new ResourceLocation("icurrency:textures/screens/cash_disabled.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (ShowCardDisabledCRPProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_cash_disabled1", imagebutton_cash_disabled1);
		this.addRenderableWidget(imagebutton_cash_disabled1);
	}
}
