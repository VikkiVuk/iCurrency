
package xyz.wulfco.icurrency.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapabilityProvider;

@Mod.EventBusSubscriber
public class GiveMoneyCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher()
				.register(Commands.literal("add-balance").requires(s -> s.hasPermission(4))
						.then(Commands.argument("amount", DoubleArgumentType.doubleArg(1.0,999999.99))
								.then(Commands.argument("player", EntityArgument.player()).executes(GiveMoneyCommand::execute))));
	}

	private static int execute(CommandContext<CommandSourceStack> ctx)  {
		Entity entity = ctx.getSource().getEntity();

		if (entity == null)
			return 0;

		if (entity instanceof Player player) {
			try {
				if (ctx.getSource().getLevel().isClientSide) {
					player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: " + "This command can only be run on the server."), false);
					return 0;
				}

				double amount = DoubleArgumentType.getDouble(ctx, "amount");
				Player target = EntityArgument.getPlayer(ctx, "player");

				target.getCapability(WalletCapabilityProvider.WALLET_CAPABILITY).ifPresent((cap) -> {
					cap.updateWalletAmount(amount);
					if (player.getDisplayName().getString().equals(target.getDisplayName().getString())) {
						player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Added " + ChatFormatting.GOLD + "$" + amount + ChatFormatting.GREEN + " to your balance."), false);
					} else {
						player.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Added " + ChatFormatting.GOLD + "$" + amount + ChatFormatting.GREEN + " to " + ChatFormatting.GOLD + target.getName().getString() + ChatFormatting.GREEN + "'s balance."), false);
						target.displayClientMessage(new TextComponent(ChatFormatting.GREEN + "Added " + ChatFormatting.GOLD + "$" + amount + ChatFormatting.GREEN + " to your balance."), false);
					}
				});

				return 0;
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
				player.displayClientMessage(new TextComponent(ChatFormatting.RED + "Error: " + e.getMessage()), false);
				return 0;
			}
		} else {
			return 0;
		}
	}
}
