package com.vikkivuk.icurrency.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.vikkivuk.icurrency.world.inventory.CashRegisterSetupMenu;
import com.vikkivuk.icurrency.network.CashRegisterSetupButtonMessage;

import com.mojang.blaze3d.systems.RenderSystem;

public class CashRegisterSetupScreen extends AbstractContainerScreen<CashRegisterSetupMenu> {
	private final static HashMap<String, Object> guistate = CashRegisterSetupMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final static HashMap<String, String> textstate = new HashMap<>();
	public static EditBox password;
	public static EditBox name;
	public static Checkbox only_cards;
	public static Checkbox deposit_account;
	Button button_empty;

	public CashRegisterSetupScreen(CashRegisterSetupMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 208;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/cash_register_setup.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		password.render(guiGraphics, mouseX, mouseY, partialTicks);
		name.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 9 && mouseX < leftPos + 33 && mouseY > topPos + 59 && mouseY < topPos + 83)
			guiGraphics.renderTooltip(font, Component.translatable("gui.icurrency.cash_register_setup.tooltip_to_access_the_cash_registers_ea"), mouseX, mouseY);
		if (mouseX > leftPos + 9 && mouseX < leftPos + 33 && mouseY > topPos + 86 && mouseY < topPos + 110)
			guiGraphics.renderTooltip(font, Component.translatable("gui.icurrency.cash_register_setup.tooltip_this_will_make_it_so_that_custom"), mouseX, mouseY);
		if (mouseX > leftPos + 9 && mouseX < leftPos + 33 && mouseY > topPos + 109 && mouseY < topPos + 133)
			guiGraphics.renderTooltip(font, Component.translatable("gui.icurrency.cash_register_setup.tooltip_note_this_will_only_work_if_you"), mouseX, mouseY);
		if (mouseX > leftPos + 9 && mouseX < leftPos + 33 && mouseY > topPos + 32 && mouseY < topPos + 56)
			guiGraphics.renderTooltip(font, Component.translatable("gui.icurrency.cash_register_setup.tooltip_this_will_be_shown_on_all_receip"), mouseX, mouseY);
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
		if (password.isFocused())
			return password.keyPressed(key, b, c);
		if (name.isFocused())
			return name.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_setup.label_hi_to_start_serving_your_custom"), 9, 10, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.icurrency.cash_register_setup.label_please_setup_the_cash_register"), 9, 19, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		password = new EditBox(this.font, this.leftPos + 9, this.topPos + 60, 118, 18, Component.translatable("gui.icurrency.cash_register_setup.password")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_setup.password").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_setup.password").getString());
				else
					setSuggestion(null);
			}
		};
		password.setMaxLength(32767);
		password.setSuggestion(Component.translatable("gui.icurrency.cash_register_setup.password").getString());
		guistate.put("text:password", password);
		this.addWidget(this.password);
		name = new EditBox(this.font, this.leftPos + 9, this.topPos + 38, 118, 18, Component.translatable("gui.icurrency.cash_register_setup.name")) {
			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_setup.name").getString());
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos, boolean flag) {
				super.moveCursorTo(pos, flag);
				if (getValue().isEmpty())
					setSuggestion(Component.translatable("gui.icurrency.cash_register_setup.name").getString());
				else
					setSuggestion(null);
			}
		};
		name.setMaxLength(32767);
		name.setSuggestion(Component.translatable("gui.icurrency.cash_register_setup.name").getString());
		guistate.put("text:name", name);
		this.addWidget(this.name);
		button_empty = Button.builder(Component.translatable("gui.icurrency.cash_register_setup.button_empty"), e -> {
			if (true) {
				textstate.put("textin:password", password.getValue());
				textstate.put("textin:name", name.getValue());
				textstate.put("checkboxin:only_cards", only_cards.selected() ? "true" : "false");
				textstate.put("checkboxin:deposit_account", deposit_account.selected() ? "true" : "false");
				PacketDistributor.SERVER.noArg().send(new CashRegisterSetupButtonMessage(0, x, y, z, textstate));
				CashRegisterSetupButtonMessage.handleButtonAction(entity, 0, x, y, z, textstate);
			}
		}).bounds(this.leftPos + 9, this.topPos + 136, 187, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		only_cards = Checkbox.builder(Component.translatable("gui.icurrency.cash_register_setup.only_cards"), this.font).pos(this.leftPos + 9, this.topPos + 86)

				.build();
		guistate.put("checkbox:only_cards", only_cards);
		this.addRenderableWidget(only_cards);
		deposit_account = Checkbox.builder(Component.translatable("gui.icurrency.cash_register_setup.deposit_account"), this.font).pos(this.leftPos + 9, this.topPos + 109)

				.build();
		guistate.put("checkbox:deposit_account", deposit_account);
		this.addRenderableWidget(deposit_account);
	}
}
