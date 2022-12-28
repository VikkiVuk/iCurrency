
package xyz.wulfco.icurrency.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.network.ATMButtonMessage;
import xyz.wulfco.icurrency.network.EnterCVCMessage;
import xyz.wulfco.icurrency.world.inventory.EnterCVCMenu;

import java.util.HashMap;

public class EnterCVCScreen extends AbstractContainerScreen<EnterCVCMenu> {
	private final static HashMap<String, Object> guistate = EnterCVCMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox cvc;

	public EnterCVCScreen(EnterCVCMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		world = container.world;
		x = container.x;
		y = container.y;
		z = container.z;
		entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 100;
	}

	private static final ResourceLocation texture = new ResourceLocation("icurrency:textures/screens/enter_cvc.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
		cvc.render(ms, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			assert this.minecraft != null;
			assert this.minecraft.player != null;
			this.minecraft.player.closeContainer();
			return true;
		}
		if (cvc.isFocused())
			return cvc.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		cvc.tick();
	}

	@Override
	protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, "Please enter your CVC", 28, 18, -12829636);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init() {
		super.init();
		assert this.minecraft != null;
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		cvc = new EditBox(this.font, this.leftPos + 26, this.topPos + 38, 120, 20, new TextComponent("123")) {
			{
				setSuggestion("123");
				setMaxLength(3);
			}

			@Override
			public void insertText(String text) {
				super.insertText(text);
				if (getValue().isEmpty())
					setSuggestion("123");
				else
					setSuggestion(null);
			}

			@Override
			public void moveCursorTo(int pos) {
				super.moveCursorTo(pos);
				if (getValue().isEmpty())
					setSuggestion("123");
				else
					setSuggestion(null);
			}
		};
		guistate.put("text:cvc", cvc);
		cvc.setMaxLength(32767);
		this.addWidget(this.cvc);
		this.addRenderableWidget(new Button(this.leftPos + 57, this.topPos + 67, 56, 20, new TextComponent("Submit"), e -> {
			iCurrency.PACKET_HANDLER.sendToServer(new EnterCVCMessage(0, x, y, z));
			EnterCVCMessage.handleButtonAction(entity, 0, x, y, z);
		}));
	}
}
