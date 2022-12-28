
package xyz.wulfco.icurrency.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.util.NetworkHandler;

import javax.json.JsonObject;
import java.util.Objects;

@Mod.EventBusSubscriber
public class BalanceCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("balance").executes(BalanceCommand::execute));
	}

	private static int execute(CommandContext<CommandSourceStack> ctx) {
		Entity entity = ctx.getSource().getEntity();
		if (entity == null)
			return 0;

		if (entity instanceof Player player) {
			if (iCurrency.cracked) {
				JsonObject response;
				if (Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer()).isSingleplayer()) {
					response = NetworkHandler.get("https://icurrency.wulfco.xyz/balance/cracked?username=" + player.getName().getString() + "&singleplayer=true");
				} else {
					response = NetworkHandler.get("https://icurrency.wulfco.xyz/balance/cracked?username=" + player.getName().getString() + "&singleplayer=false" + "&server=" + Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).ip);
				}

				if (response != null) {
					if (response.getString("status").equals("ok")) {
						player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Balance: " + response.getString("balance")), false);
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
