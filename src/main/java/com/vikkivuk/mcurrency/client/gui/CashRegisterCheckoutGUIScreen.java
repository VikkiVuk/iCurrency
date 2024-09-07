package com.vikkivuk.mcurrency.client.gui;

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
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.vikkivuk.mcurrency.world.inventory.CashRegisterCheckoutGUIMenu;
import com.vikkivuk.mcurrency.procedures.ShowCashEnabledCRPProcedure;
import com.vikkivuk.mcurrency.procedures.ShowCashDisabledCRPProcedure;
import com.vikkivuk.mcurrency.procedures.ShowCardEnabledCRPProcedure;
import com.vikkivuk.mcurrency.procedures.ShowCardDisabledCRPProcedure;
import com.vikkivuk.mcurrency.network.CashRegisterCheckoutGUIButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class CashRegisterCheckoutGUIScreen extends AbstractContainerScreen<CashRegisterCheckoutGUIMenu> {
	private final static HashMap<String, Object> guistate = CashRegisterCheckoutGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox product;
	EditBox price;
	EditBox tip;
	Button button_card;
	Button button_cash;
	ImageButton imagebutton_cash_disabled;
	ImageButton imagebutton_cash_disabled1;

	public CashRegisterCheckoutGUIScreen(CashRegisterCheckoutGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 146;
		this.imageHeight = 123;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof CashRegisterCheckoutGUIScreen sc) {
			textstate.put("textin:product", sc.product.getValue());
			textstate.put("textin:price", sc.price.getValue());
			textstate.put("textin:tip", sc.tip.getValue());

		}
		return textstate;
	}

	@Override
	public void containerTick() {
		super.containerTick();
		PacketDistributor.sendToServer(new CashRegisterCheckoutGUIButtonMessage(-1, x, y, z, getEditBoxAndCheckBoxValues()));
		CashRegisterCheckoutGUIButtonMessage.handleButtonAction(entity, -1, x, y, z, getEditBoxAndCheckBoxValues());
	}

	private static final ResourceLocation texture = new ResourceLocation("mcurrency:textures/screens/cash_register_checkout_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		product.render(guiGraphics, mouseX, mouseY, partialTicks);
		price.render(guiGraphics, mouseX, mouseY, partialTicks);
		tip.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 13 && mouseX < leftPos + 37 && mouseY > topPos + 36 && mouseY < topPos + 60)
			guiGraphics.renderTooltip(font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.tooltip_anything_over_z500000_can_only_b"), mouseX, mouseY);
		if (mouseX > leftPos + 37 && mouseX < leftPos + 61 && mouseY > topPos + 36 && mouseY < topPos + 60)
			guiGraphics.renderTooltip(font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.tooltip_anything_over_z500000_can_only_b1"), mouseX, mouseY);
		if (mouseX > leftPos + 61 && mouseX < leftPos + 85 && mouseY > topPos + 36 && mouseY < topPos + 60)
			guiGraphics.renderTooltip(font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.tooltip_anything_over_z500000_can_only_b2"), mouseX, mouseY);
		if (mouseX > leftPos + 85 && mouseX < leftPos + 109 && mouseY > topPos + 36 && mouseY < topPos + 60)
			guiGraphics.renderTooltip(font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.tooltip_anything_over_z500000_can_only_b3"), mouseX, mouseY);
		if (mouseX > leftPos + 109 && mouseX < leftPos + 133 && mouseY > topPos + 36 && mouseY < topPos + 60)
			guiGraphics.renderTooltip(font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.tooltip_anything_over_z500000_can_only_b4"), mouseX, mouseY);
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
	public void resize(Minecraft minecraft, int width, int height) {
		String productValue = product.getValue();
		String priceValue = price.getValue();
		String tipValue = tip.getValue();
		super.resize(minecraft, width, height);
		product.setValue(productValue);
		price.setValue(priceValue);
		tip.setValue(tipValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		if (ShowCashDisabledCRPProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.label_cash"), 25, 99, -3355444, false);
		if (ShowCardDisabledCRPProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.cash_register_checkout_gui.label_card"), 98, 99, -3355444, false);
	}

	@Override
	public void init() {
		super.init();
		product = new EditBox(this.font, this.leftPos + 14, this.topPos + 11, 118, 18, Component.translatable("gui.mcurrency.cash_register_checkout_gui.product")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.product").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.product").getString());
				else
					setSuggestion(null);
			}
		};
		product.setMaxLength(32767);
		product.setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.product").getString());
		guistate.put("text:product", product);
		this.addWidget(this.product);
		price = new EditBox(this.font, this.leftPos + 14, this.topPos + 39, 118, 18, Component.translatable("gui.mcurrency.cash_register_checkout_gui.price")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.price").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.price").getString());
				else
					setSuggestion(null);
			}
		};
		price.setMaxLength(32767);
		price.setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.price").getString());
		guistate.put("text:price", price);
		this.addWidget(this.price);
		tip = new EditBox(this.font, this.leftPos + 14, this.topPos + 67, 118, 18, Component.translatable("gui.mcurrency.cash_register_checkout_gui.tip")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.tip").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.tip").getString());
				else
					setSuggestion(null);
			}
		};
		tip.setMaxLength(32767);
		tip.setSuggestion(Component.translatable("gui.mcurrency.cash_register_checkout_gui.tip").getString());
		guistate.put("text:tip", tip);
		this.addWidget(this.tip);
		button_card = Button.builder(Component.translatable("gui.mcurrency.cash_register_checkout_gui.button_card"), e -> {
			if (ShowCardEnabledCRPProcedure.execute(entity)) {
				PacketDistributor.sendToServer(new CashRegisterCheckoutGUIButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				CashRegisterCheckoutGUIButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 87, this.topPos + 93, 46, 20).build(builder -> new Button(builder) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				if (ShowCardEnabledCRPProcedure.execute(entity))
					super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		});
		guistate.put("button:button_card", button_card);
		this.addRenderableWidget(button_card);
		button_cash = Button.builder(Component.translatable("gui.mcurrency.cash_register_checkout_gui.button_cash"), e -> {
			if (ShowCashEnabledCRPProcedure.execute(entity)) {
				PacketDistributor.sendToServer(new CashRegisterCheckoutGUIButtonMessage(1, x, y, z, getEditBoxAndCheckBoxValues()));
				CashRegisterCheckoutGUIButtonMessage.handleButtonAction(entity, 1, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 14, this.topPos + 93, 46, 20).build(builder -> new Button(builder) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				if (ShowCashEnabledCRPProcedure.execute(entity))
					super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		});
		guistate.put("button:button_cash", button_cash);
		this.addRenderableWidget(button_cash);
		imagebutton_cash_disabled = new ImageButton(this.leftPos + 14, this.topPos + 93, 46, 20,
				new WidgetSprites(new ResourceLocation("mcurrency:textures/screens/cash_disabled.png"), new ResourceLocation("mcurrency:textures/screens/cash_disabled.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (ShowCashDisabledCRPProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_cash_disabled", imagebutton_cash_disabled);
		this.addRenderableWidget(imagebutton_cash_disabled);
		imagebutton_cash_disabled1 = new ImageButton(this.leftPos + 87, this.topPos + 93, 46, 20,
				new WidgetSprites(new ResourceLocation("mcurrency:textures/screens/cash_disabled.png"), new ResourceLocation("mcurrency:textures/screens/cash_disabled.png")), e -> {
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
