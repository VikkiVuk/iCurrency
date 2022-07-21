package xyz.vikkivuk.icurrency.procedures;

import xyz.vikkivuk.icurrency.network.IcurrencyModVariables;
import xyz.vikkivuk.icurrency.init.IcurrencyModItems;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

public class PayProcedureProcedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		double namount = 0;
		{
			List<? extends Player> _players = new ArrayList<>(world.players());
			for (Entity entityiterator : _players) {
				if ((entityiterator.getDisplayName().getString())
						.equals(guistate.containsKey("text:player_username") ? ((EditBox) guistate.get("text:player_username")).getValue() : "")) {
					if (IcurrencyModVariables.MapVariables.get(world).account_balance > new Object() {
						double convert(String s) {
							try {
								return Double.parseDouble(s.trim());
							} catch (Exception e) {
							}
							return 0;
						}
					}.convert(guistate.containsKey("text:amount") ? ((EditBox) guistate.get("text:amount")).getValue() : "")) {
						IcurrencyModVariables.MapVariables.get(world).account_balance = IcurrencyModVariables.MapVariables.get(world).account_balance
								- new Object() {
									double convert(String s) {
										try {
											return Double.parseDouble(s.trim());
										} catch (Exception e) {
										}
										return 0;
									}
								}.convert(guistate.containsKey("text:amount") ? ((EditBox) guistate.get("text:amount")).getValue() : "");
						IcurrencyModVariables.MapVariables.get(world).syncData(world);
						entityiterator.getPersistentData().putDouble("account_balance_tag",
								(entityiterator.getPersistentData().getDouble("account_balance_tag") + new Object() {
									double convert(String s) {
										try {
											return Double.parseDouble(s.trim());
										} catch (Exception e) {
										}
										return 0;
									}
								}.convert(guistate.containsKey("text:amount") ? ((EditBox) guistate.get("text:amount")).getValue() : "")));
						namount = new Object() {
							double convert(String s) {
								try {
									return Double.parseDouble(s.trim());
								} catch (Exception e) {
								}
								return 0;
							}
						}.convert(guistate.containsKey("text:amount") ? ((EditBox) guistate.get("text:amount")).getValue() : "");
						if (entityiterator instanceof Player _player) {
							ItemStack _setstack = new ItemStack(IcurrencyModItems.HUNDRED_DOLLAR_BILL.get());
							_setstack.setCount((int) Math.floor(namount / 100));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						namount = namount - Math.floor(namount / 100) * 100;
						if (entityiterator instanceof Player _player) {
							ItemStack _setstack = new ItemStack(IcurrencyModItems.FIFTY_DOLLAR_BILL.get());
							_setstack.setCount((int) Math.floor(namount / 50));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						namount = namount - Math.floor(namount / 50) * 50;
						if (entityiterator instanceof Player _player) {
							ItemStack _setstack = new ItemStack(IcurrencyModItems.TWENTY_DOLLAR_BILL.get());
							_setstack.setCount((int) Math.floor(namount / 20));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						namount = namount - Math.floor(namount / 20) * 20;
						if (entityiterator instanceof Player _player) {
							ItemStack _setstack = new ItemStack(IcurrencyModItems.DOLLAR_TEN.get());
							_setstack.setCount((int) Math.floor(namount / 10));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						namount = namount - Math.floor(namount / 10) * 10;
						if (entityiterator instanceof Player _player) {
							ItemStack _setstack = new ItemStack(IcurrencyModItems.FIVE_DOLLAR_BILL.get());
							_setstack.setCount((int) Math.floor(namount / 5));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						namount = namount - Math.floor(namount / 5) * 5;
						if (entityiterator instanceof Player _player) {
							ItemStack _setstack = new ItemStack(IcurrencyModItems.ONE_DOLLAR_BILL.get());
							_setstack.setCount((int) Math.floor(namount / 1));
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(new TextComponent(("Sent " + new Object() {
								double convert(String s) {
									try {
										return Double.parseDouble(s.trim());
									} catch (Exception e) {
									}
									return 0;
								}
							}.convert(guistate.containsKey("text:amount") ? ((EditBox) guistate.get("text:amount")).getValue() : "") + " to "
									+ entityiterator.getDisplayName().getString())), (false));
						if (entityiterator instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(new TextComponent((entity.getDisplayName().getString() + " has sent you " + new Object() {
								double convert(String s) {
									try {
										return Double.parseDouble(s.trim());
									} catch (Exception e) {
									}
									return 0;
								}
							}.convert(guistate.containsKey("text:amount") ? ((EditBox) guistate.get("text:amount")).getValue() : "")
									+ ". The money has been stored into your inventory.")), (false));
						if (entity instanceof ServerPlayer _player) {
							Advancement _adv = _player.server.getAdvancements()
									.getAdvancement(new ResourceLocation("icurrency:generous_advancement"));
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								Iterator _iterator = _ap.getRemainingCriteria().iterator();
								while (_iterator.hasNext())
									_player.getAdvancements().award(_adv, (String) _iterator.next());
							}
						}
						if (entity instanceof Player _player)
							_player.closeContainer();
					} else {
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(new TextComponent("Not enough funds in account!"), (false));
					}
				}
			}
		}
	}
}
