
package xyz.wulfco.icurrency.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Objects;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.util.NetworkHandler;

import javax.json.JsonObject;

@Mod.EventBusSubscriber
public class GiveMoneyCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher()
				.register(Commands.literal("add-balance").requires(s -> s.hasPermission(4))
						.then(Commands.argument("amount", IntegerArgumentType.integer(1,999999)).executes(GiveMoneyCommand::execute))
						.executes(GiveMoneyCommand::execute));
	}

	private static int execute(CommandContext<CommandSourceStack> ctx) {
		Entity entity = ctx.getSource().getEntity();

		if (entity == null)
			return 0;

		if (entity instanceof Player player) {
			final int amount = IntegerArgumentType.getInteger(ctx, "amount");
			if (iCurrency.cracked) {
				JsonObject response;
				if (Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer()).isSingleplayer()) {
					response = NetworkHandler.get("https://icurrency.wulfco.xyz/add-balance/cracked/" + player.getName().getString() + "?amount=" + amount);
				} else {
					player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: This operation isn't allowed on a server."), false);
					return 0;
				}

				if (response != null) {
					if (response.getString("status").equals("ok")) {
						player.displayClientMessage(new TextComponent("Added " + response.getString("amountadded") + " to your singleplayer account."), false);
					} else {
						player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: " + response.getString("error")), false);
					}
				} else {
					player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: Could not connect to server"), false);
				}
			} else {
				player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: Premium isn't supported yet."), false);
			}
		}

		return 0;
	}
}
