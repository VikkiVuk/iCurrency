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
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.CashRegisterPayCashMenu;
import com.vikkivuk.icurrency.procedures.CashRegisterPayCardValueProcedure;
import com.vikkivuk.icurrency.network.CashRegisterPayCashButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class CashRegisterPayCashScreen extends AbstractContainerScreen<CashRegisterPayCashMenu> {
	private final static HashMap<String, Object> guistate = CashRegisterPayCashMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_empty;
	ImageButton imagebutton_trash;

	public CashRegisterPayCashScreen(CashRegisterPayCashMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof CashRegisterPayCashScreen sc) {

		}
		return textstate;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/cash_register_pay_cash.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 147 && mouseX < leftPos + 171 && mouseY > topPos + 4 && mouseY < topPos + 28)
			guiGraphics.renderTooltip(font, Component.translatable("gui.icurrency.cash_register_pay_cash.tooltip_void_the_transaction"), mouseX, mouseY);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_pay_cash.label_cash_payment"), 7, 8, -12829636, false);
		guiGraphics.drawString(this.font,

				CashRegisterPayCardValueProcedure.execute(world, x, y, z), 8, 46, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_empty = Button.builder(Component.translatable("gui.icurrency.cash_register_pay_cash.button_empty"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CashRegisterPayCashButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				CashRegisterPayCashButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 7, this.topPos + 59, 161, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		imagebutton_trash = new ImageButton(this.leftPos + 151, this.topPos + 7, 16, 16, new WidgetSprites(new ResourceLocation("icurrency:textures/screens/trash.png"), new ResourceLocation("icurrency:textures/screens/trash_hover.png")), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CashRegisterPayCashButtonMessage(1, x, y, z, getEditBoxAndCheckBoxValues()));
				CashRegisterPayCashButtonMessage.handleButtonAction(entity, 1, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_trash", imagebutton_trash);
		this.addRenderableWidget(imagebutton_trash);
	}
}
