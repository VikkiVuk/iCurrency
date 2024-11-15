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
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.vikkivuk.mcurrency.world.inventory.CardOverviewMenu;
import com.vikkivuk.mcurrency.procedures.COShowCardTwoProcedure;
import com.vikkivuk.mcurrency.procedures.COShowCardThreeProcedure;
import com.vikkivuk.mcurrency.procedures.COShowCardOneProcedure;
import com.vikkivuk.mcurrency.procedures.COShowCardFourProcedure;
import com.vikkivuk.mcurrency.procedures.COCardTwoNumberProcedure;
import com.vikkivuk.mcurrency.procedures.COCardTwoCVCProcedure;
import com.vikkivuk.mcurrency.procedures.COCardThreeNumberProcedure;
import com.vikkivuk.mcurrency.procedures.COCardThreeCVCProcedure;
import com.vikkivuk.mcurrency.procedures.COCardOneNumberProcedure;
import com.vikkivuk.mcurrency.procedures.COCardOneCVCProcedure;
import com.vikkivuk.mcurrency.procedures.COCardFourNumberProcedure;
import com.vikkivuk.mcurrency.procedures.COCardFourCVCProcedure;
import com.vikkivuk.mcurrency.network.CardOverviewButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class CardOverviewScreen extends AbstractContainerScreen<CardOverviewMenu> {
	private final static HashMap<String, Object> guistate = CardOverviewMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_empty;
	ImageButton imagebutton_trash;
	ImageButton imagebutton_trash1;
	ImageButton imagebutton_trash2;
	ImageButton imagebutton_trash3;

	public CardOverviewScreen(CardOverviewMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 170;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof CardOverviewScreen sc) {

		}
		return textstate;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("mcurrency:textures/screens/card_overview.png");

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
		if (COShowCardOneProcedure.execute(entity)) {
			guiGraphics.blit(ResourceLocation.parse("mcurrency:textures/screens/card_tile.png"), this.leftPos + 6, this.topPos + 13, 0, 0, 80, 64, 80, 64);
		}
		if (COShowCardTwoProcedure.execute(entity)) {
			guiGraphics.blit(ResourceLocation.parse("mcurrency:textures/screens/card_tile.png"), this.leftPos + 87, this.topPos + 13, 0, 0, 80, 64, 80, 64);
		}
		if (COShowCardThreeProcedure.execute(entity)) {
			guiGraphics.blit(ResourceLocation.parse("mcurrency:textures/screens/card_tile.png"), this.leftPos + 6, this.topPos + 69, 0, 0, 80, 64, 80, 64);
		}
		if (COShowCardFourProcedure.execute(entity)) {
			guiGraphics.blit(ResourceLocation.parse("mcurrency:textures/screens/card_tile.png"), this.leftPos + 87, this.topPos + 69, 0, 0, 80, 64, 80, 64);
		}
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
		guiGraphics.drawString(this.font, Component.translatable("gui.mcurrency.card_overview.label_your_cards"), 8, 7, -12829636, false);
		if (COShowCardOneProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardOneNumberProcedure.execute(entity), 21, 28, -12829636, false);
		if (COShowCardTwoProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardTwoNumberProcedure.execute(entity), 102, 28, -12829636, false);
		if (COShowCardThreeProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardThreeNumberProcedure.execute(entity), 21, 84, -12829636, false);
		if (COShowCardFourProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardFourNumberProcedure.execute(entity), 102, 84, -12829636, false);
		if (COShowCardOneProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardOneCVCProcedure.execute(entity), 22, 40, -12829636, false);
		if (COShowCardTwoProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardTwoCVCProcedure.execute(entity), 103, 40, -12829636, false);
		if (COShowCardThreeProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardThreeCVCProcedure.execute(entity), 22, 96, -12829636, false);
		if (COShowCardFourProcedure.execute(entity))
			guiGraphics.drawString(this.font,

					COCardFourCVCProcedure.execute(entity), 103, 96, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_empty = Button.builder(Component.translatable("gui.mcurrency.card_overview.button_empty"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new CardOverviewButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				CardOverviewButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 9, this.topPos + 137, 156, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		imagebutton_trash = new ImageButton(this.leftPos + 64, this.topPos + 52, 16, 16, new WidgetSprites(ResourceLocation.parse("mcurrency:textures/screens/trash.png"), ResourceLocation.parse("mcurrency:textures/screens/trash_hover.png")), e -> {
			if (COShowCardOneProcedure.execute(entity)) {
				PacketDistributor.sendToServer(new CardOverviewButtonMessage(1, x, y, z, getEditBoxAndCheckBoxValues()));
				CardOverviewButtonMessage.handleButtonAction(entity, 1, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (COShowCardOneProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_trash", imagebutton_trash);
		this.addRenderableWidget(imagebutton_trash);
		imagebutton_trash1 = new ImageButton(this.leftPos + 145, this.topPos + 52, 16, 16, new WidgetSprites(ResourceLocation.parse("mcurrency:textures/screens/trash.png"), ResourceLocation.parse("mcurrency:textures/screens/trash_hover.png")), e -> {
			if (COShowCardTwoProcedure.execute(entity)) {
				PacketDistributor.sendToServer(new CardOverviewButtonMessage(2, x, y, z, getEditBoxAndCheckBoxValues()));
				CardOverviewButtonMessage.handleButtonAction(entity, 2, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (COShowCardTwoProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_trash1", imagebutton_trash1);
		this.addRenderableWidget(imagebutton_trash1);
		imagebutton_trash2 = new ImageButton(this.leftPos + 64, this.topPos + 108, 16, 16, new WidgetSprites(ResourceLocation.parse("mcurrency:textures/screens/trash.png"), ResourceLocation.parse("mcurrency:textures/screens/trash_hover.png")), e -> {
			if (COShowCardThreeProcedure.execute(entity)) {
				PacketDistributor.sendToServer(new CardOverviewButtonMessage(3, x, y, z, getEditBoxAndCheckBoxValues()));
				CardOverviewButtonMessage.handleButtonAction(entity, 3, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (COShowCardThreeProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_trash2", imagebutton_trash2);
		this.addRenderableWidget(imagebutton_trash2);
		imagebutton_trash3 = new ImageButton(this.leftPos + 145, this.topPos + 108, 16, 16, new WidgetSprites(ResourceLocation.parse("mcurrency:textures/screens/trash.png"), ResourceLocation.parse("mcurrency:textures/screens/trash_hover.png")),
				e -> {
					if (COShowCardFourProcedure.execute(entity)) {
						PacketDistributor.sendToServer(new CardOverviewButtonMessage(4, x, y, z, getEditBoxAndCheckBoxValues()));
						CardOverviewButtonMessage.handleButtonAction(entity, 4, x, y, z, getEditBoxAndCheckBoxValues());
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				if (COShowCardFourProcedure.execute(entity))
					guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_trash3", imagebutton_trash3);
		this.addRenderableWidget(imagebutton_trash3);
	}
}
