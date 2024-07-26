package com.vikkivuk.icurrency.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

import com.vikkivuk.icurrency.world.inventory.CashRegisterPayCardMenu;
import com.vikkivuk.icurrency.network.IcurrencyModVariables;
import com.vikkivuk.icurrency.IcurrencyMod;

public class CheckCardPinProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		IcurrencyMod.queueServerWork(1, () -> {
			if (entity instanceof Player _player)
				_player.closeContainer();
			if (entity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(x, y, z);
				_ent.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("CashRegisterPayCard");
					}

					@Override
					public boolean shouldTriggerClientSideContainerClosingOnOpen() {
						return false;
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new CashRegisterPayCardMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
			if ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
					.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("activated")) {
				if (((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
						.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("pin")).equals(guistate.containsKey("textin:pin") ? (String) guistate.get("textin:pin") : "")) {
					if ((entity.getDisplayName().getString()).equals((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
							.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder"))) {
						if (entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards.containsValue((new Vec3(
								((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
										.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
								((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
										.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")),
								((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
										.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))))) {
							if (new Object() {
								public double getValue(LevelAccessor world, BlockPos pos, String tag) {
									BlockEntity blockEntity = world.getBlockEntity(pos);
									if (blockEntity != null)
										return blockEntity.getPersistentData().getDouble(tag);
									return -1;
								}
							}.getValue(world, BlockPos.containing(x, y, z), "total") <= entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).money) {
								if (!(((new BiFunction<LevelAccessor, String, Entity>() {
									@Override
									public Entity apply(LevelAccessor levelAccessor, String uuid) {
										if (levelAccessor instanceof ServerLevel serverLevel) {
											try {
												return serverLevel.getEntity(UUID.fromString(uuid));
											} catch (Exception e) {
											}
										}
										return null;
									}
								}).apply(world, (new Object() {
									public String getValue(LevelAccessor world, BlockPos pos, String tag) {
										BlockEntity blockEntity = world.getBlockEntity(pos);
										if (blockEntity != null)
											return blockEntity.getPersistentData().getString(tag);
										return "";
									}
								}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))) == null)) {
									{
										IcurrencyModVariables.PlayerVariables _vars = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES);
										_vars.money = entity.getData(IcurrencyModVariables.PLAYER_VARIABLES).money - (new Object() {
											public double getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getDouble(tag);
												return -1;
											}
										}.getValue(world, BlockPos.containing(x, y, z), "total"));
										_vars.syncPlayerVariables(entity);
									}
									if (entity instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal("Success!"), false);
									if (!world.isClientSide()) {
										BlockPos _bp = BlockPos.containing(x, y, z);
										BlockEntity _blockEntity = world.getBlockEntity(_bp);
										BlockState _bs = world.getBlockState(_bp);
										if (_blockEntity != null)
											_blockEntity.getPersistentData().putString("transaction_result", "SUCCESS");
										if (world instanceof Level _level)
											_level.sendBlockUpdated(_bp, _bs, _bs, 3);
									}
									CashRegisterGiveReceiptProcedure.execute(world, x, y, z, entity);
									if (!world.isClientSide()) {
										BlockPos _bp = BlockPos.containing(x, y, z);
										BlockEntity _blockEntity = world.getBlockEntity(_bp);
										BlockState _bs = world.getBlockState(_bp);
										if (_blockEntity != null)
											_blockEntity.getPersistentData().putDouble("money", (new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "price") + new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "money")));
										if (world instanceof Level _level)
											_level.sendBlockUpdated(_bp, _bs, _bs, 3);
									}
									{
										IcurrencyModVariables.PlayerVariables _vars = ((new BiFunction<LevelAccessor, String, Entity>() {
											@Override
											public Entity apply(LevelAccessor levelAccessor, String uuid) {
												if (levelAccessor instanceof ServerLevel serverLevel) {
													try {
														return serverLevel.getEntity(UUID.fromString(uuid));
													} catch (Exception e) {
													}
												}
												return null;
											}
										}).apply(world, (new Object() {
											public String getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getString(tag);
												return "";
											}
										}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES);
										_vars.money = ((new BiFunction<LevelAccessor, String, Entity>() {
											@Override
											public Entity apply(LevelAccessor levelAccessor, String uuid) {
												if (levelAccessor instanceof ServerLevel serverLevel) {
													try {
														return serverLevel.getEntity(UUID.fromString(uuid));
													} catch (Exception e) {
													}
												}
												return null;
											}
										}).apply(world, (new Object() {
											public String getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getString(tag);
												return "";
											}
										}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES).money + (new Object() {
											public double getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getDouble(tag);
												return -1;
											}
										}.getValue(world, BlockPos.containing(x, y, z), "total")) - (new Object() {
											public double getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getDouble(tag);
												return -1;
											}
										}.getValue(world, BlockPos.containing(x, y, z), "price"));
										_vars.syncPlayerVariables(((new BiFunction<LevelAccessor, String, Entity>() {
											@Override
											public Entity apply(LevelAccessor levelAccessor, String uuid) {
												if (levelAccessor instanceof ServerLevel serverLevel) {
													try {
														return serverLevel.getEntity(UUID.fromString(uuid));
													} catch (Exception e) {
													}
												}
												return null;
											}
										}).apply(world, (new Object() {
											public String getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getString(tag);
												return "";
											}
										}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))));
									}
									if (((new BiFunction<LevelAccessor, String, Entity>() {
										@Override
										public Entity apply(LevelAccessor levelAccessor, String uuid) {
											if (levelAccessor instanceof ServerLevel serverLevel) {
												try {
													return serverLevel.getEntity(UUID.fromString(uuid));
												} catch (Exception e) {
												}
											}
											return null;
										}
									}).apply(world, (new Object() {
										public String getValue(LevelAccessor world, BlockPos pos, String tag) {
											BlockEntity blockEntity = world.getBlockEntity(pos);
											if (blockEntity != null)
												return blockEntity.getPersistentData().getString(tag);
											return "";
										}
									}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))) instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal(("You received a tip of \u01B5" + ((new Object() {
											public double getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getDouble(tag);
												return -1;
											}
										}.getValue(world, BlockPos.containing(x, y, z), "total")) - (new Object() {
											public double getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getDouble(tag);
												return -1;
											}
										}.getValue(world, BlockPos.containing(x, y, z), "price"))) + " as part of a purchase made at a cash register.")), false);
									if (entity instanceof Player _player) {
										ItemStack _setstack = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
												.copy();
										_setstack.setCount(1);
										ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
									}
									if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
										((Slot) _slots.get(0)).set(ItemStack.EMPTY);
										_player.containerMenu.broadcastChanges();
									}
								} else {
									if (entity instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal("Could not find the cashier in online players!"), false);
								}
							} else {
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal("Sorry, it looks like you have insufficient funds!"), false);
								if (!world.isClientSide()) {
									BlockPos _bp = BlockPos.containing(x, y, z);
									BlockEntity _blockEntity = world.getBlockEntity(_bp);
									BlockState _bs = world.getBlockState(_bp);
									if (_blockEntity != null)
										_blockEntity.getPersistentData().putString("transaction_result", "FAIL");
									if (world instanceof Level _level)
										_level.sendBlockUpdated(_bp, _bs, _bs, 3);
								}
								if (entity instanceof Player _player) {
									ItemStack _setstack = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY).copy();
									_setstack.setCount(1);
									ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
								}
								if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
									((Slot) _slots.get(0)).set(ItemStack.EMPTY);
									_player.containerMenu.broadcastChanges();
								}
							}
						} else {
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal("Sorry, this card has been canceled. Please use another form of payment!"), false);
							if (!world.isClientSide()) {
								BlockPos _bp = BlockPos.containing(x, y, z);
								BlockEntity _blockEntity = world.getBlockEntity(_bp);
								BlockState _bs = world.getBlockState(_bp);
								if (_blockEntity != null)
									_blockEntity.getPersistentData().putString("transaction_result", "FAIL");
								if (world instanceof Level _level)
									_level.sendBlockUpdated(_bp, _bs, _bs, 3);
							}
							if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
								((Slot) _slots.get(0)).set(ItemStack.EMPTY);
								_player.containerMenu.broadcastChanges();
							}
						}
					} else {
						if (!(((new BiFunction<LevelAccessor, String, Entity>() {
							@Override
							public Entity apply(LevelAccessor levelAccessor, String uuid) {
								if (levelAccessor instanceof ServerLevel serverLevel) {
									try {
										return serverLevel.getEntity(UUID.fromString(uuid));
									} catch (Exception e) {
									}
								}
								return null;
							}
						}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
								.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))) == null)) {
							if (((new BiFunction<LevelAccessor, String, Entity>() {
								@Override
								public Entity apply(LevelAccessor levelAccessor, String uuid) {
									if (levelAccessor instanceof ServerLevel serverLevel) {
										try {
											return serverLevel.getEntity(UUID.fromString(uuid));
										} catch (Exception e) {
										}
									}
									return null;
								}
							}).apply(world,
									((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
											.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid"))))
									.getData(IcurrencyModVariables.PLAYER_VARIABLES).cards
									.containsValue((new Vec3(
											((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n")),
											((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")),
											((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("cvc")))))) {
								if (new Object() {
									public double getValue(LevelAccessor world, BlockPos pos, String tag) {
										BlockEntity blockEntity = world.getBlockEntity(pos);
										if (blockEntity != null)
											return blockEntity.getPersistentData().getDouble(tag);
										return -1;
									}
								}.getValue(world, BlockPos.containing(x, y, z), "total") <= ((new BiFunction<LevelAccessor, String, Entity>() {
									@Override
									public Entity apply(LevelAccessor levelAccessor, String uuid) {
										if (levelAccessor instanceof ServerLevel serverLevel) {
											try {
												return serverLevel.getEntity(UUID.fromString(uuid));
											} catch (Exception e) {
											}
										}
										return null;
									}
								}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
										.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES).money) {
									if (!(((new BiFunction<LevelAccessor, String, Entity>() {
										@Override
										public Entity apply(LevelAccessor levelAccessor, String uuid) {
											if (levelAccessor instanceof ServerLevel serverLevel) {
												try {
													return serverLevel.getEntity(UUID.fromString(uuid));
												} catch (Exception e) {
												}
											}
											return null;
										}
									}).apply(world, (new Object() {
										public String getValue(LevelAccessor world, BlockPos pos, String tag) {
											BlockEntity blockEntity = world.getBlockEntity(pos);
											if (blockEntity != null)
												return blockEntity.getPersistentData().getString(tag);
											return "";
										}
									}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))) == null)) {
										{
											IcurrencyModVariables.PlayerVariables _vars = ((new BiFunction<LevelAccessor, String, Entity>() {
												@Override
												public Entity apply(LevelAccessor levelAccessor, String uuid) {
													if (levelAccessor instanceof ServerLevel serverLevel) {
														try {
															return serverLevel.getEntity(UUID.fromString(uuid));
														} catch (Exception e) {
														}
													}
													return null;
												}
											}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES);
											_vars.money = ((new BiFunction<LevelAccessor, String, Entity>() {
												@Override
												public Entity apply(LevelAccessor levelAccessor, String uuid) {
													if (levelAccessor instanceof ServerLevel serverLevel) {
														try {
															return serverLevel.getEntity(UUID.fromString(uuid));
														} catch (Exception e) {
														}
													}
													return null;
												}
											}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES).money - (new Object() {
														public double getValue(LevelAccessor world, BlockPos pos, String tag) {
															BlockEntity blockEntity = world.getBlockEntity(pos);
															if (blockEntity != null)
																return blockEntity.getPersistentData().getDouble(tag);
															return -1;
														}
													}.getValue(world, BlockPos.containing(x, y, z), "total"));
											_vars.syncPlayerVariables(((new BiFunction<LevelAccessor, String, Entity>() {
												@Override
												public Entity apply(LevelAccessor levelAccessor, String uuid) {
													if (levelAccessor instanceof ServerLevel serverLevel) {
														try {
															return serverLevel.getEntity(UUID.fromString(uuid));
														} catch (Exception e) {
														}
													}
													return null;
												}
											}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))));
										}
										if (!world.isClientSide()) {
											BlockPos _bp = BlockPos.containing(x, y, z);
											BlockEntity _blockEntity = world.getBlockEntity(_bp);
											BlockState _bs = world.getBlockState(_bp);
											if (_blockEntity != null)
												_blockEntity.getPersistentData().putDouble("money", (new Object() {
													public double getValue(LevelAccessor world, BlockPos pos, String tag) {
														BlockEntity blockEntity = world.getBlockEntity(pos);
														if (blockEntity != null)
															return blockEntity.getPersistentData().getDouble(tag);
														return -1;
													}
												}.getValue(world, BlockPos.containing(x, y, z), "total") + new Object() {
													public double getValue(LevelAccessor world, BlockPos pos, String tag) {
														BlockEntity blockEntity = world.getBlockEntity(pos);
														if (blockEntity != null)
															return blockEntity.getPersistentData().getDouble(tag);
														return -1;
													}
												}.getValue(world, BlockPos.containing(x, y, z), "money")));
											if (world instanceof Level _level)
												_level.sendBlockUpdated(_bp, _bs, _bs, 3);
										}
										if (entity instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal("Success!"), false);
										if (((new BiFunction<LevelAccessor, String, Entity>() {
											@Override
											public Entity apply(LevelAccessor levelAccessor, String uuid) {
												if (levelAccessor instanceof ServerLevel serverLevel) {
													try {
														return serverLevel.getEntity(UUID.fromString(uuid));
													} catch (Exception e) {
													}
												}
												return null;
											}
										}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
												.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))) instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + "" + " bought \"" + (new Object() {
												public String getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getString(tag);
													return "";
												}
											}.getValue(world, BlockPos.containing(x, y, z), "product")) + "\" using your card with the number "
													+ Math.round((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
															.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n"))
													+ " "
													+ Math.round((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
															.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n")))),
													false);
										if (entity instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal("----------------------"), false);
										if (((new BiFunction<LevelAccessor, String, Entity>() {
											@Override
											public Entity apply(LevelAccessor levelAccessor, String uuid) {
												if (levelAccessor instanceof ServerLevel serverLevel) {
													try {
														return serverLevel.getEntity(UUID.fromString(uuid));
													} catch (Exception e) {
													}
												}
												return null;
											}
										}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
												.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))) instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal(("The payment total is \u01B5" + (new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "total")))), false);
										if (!world.isClientSide()) {
											BlockPos _bp = BlockPos.containing(x, y, z);
											BlockEntity _blockEntity = world.getBlockEntity(_bp);
											BlockState _bs = world.getBlockState(_bp);
											if (_blockEntity != null)
												_blockEntity.getPersistentData().putString("transaction_result", "SUCCESS");
											if (world instanceof Level _level)
												_level.sendBlockUpdated(_bp, _bs, _bs, 3);
										}
										{
											IcurrencyModVariables.PlayerVariables _vars = ((new BiFunction<LevelAccessor, String, Entity>() {
												@Override
												public Entity apply(LevelAccessor levelAccessor, String uuid) {
													if (levelAccessor instanceof ServerLevel serverLevel) {
														try {
															return serverLevel.getEntity(UUID.fromString(uuid));
														} catch (Exception e) {
														}
													}
													return null;
												}
											}).apply(world, (new Object() {
												public String getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getString(tag);
													return "";
												}
											}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES);
											_vars.money = ((new BiFunction<LevelAccessor, String, Entity>() {
												@Override
												public Entity apply(LevelAccessor levelAccessor, String uuid) {
													if (levelAccessor instanceof ServerLevel serverLevel) {
														try {
															return serverLevel.getEntity(UUID.fromString(uuid));
														} catch (Exception e) {
														}
													}
													return null;
												}
											}).apply(world, (new Object() {
												public String getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getString(tag);
													return "";
												}
											}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))).getData(IcurrencyModVariables.PLAYER_VARIABLES).money + (new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "total")) - (new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "price"));
											_vars.syncPlayerVariables(((new BiFunction<LevelAccessor, String, Entity>() {
												@Override
												public Entity apply(LevelAccessor levelAccessor, String uuid) {
													if (levelAccessor instanceof ServerLevel serverLevel) {
														try {
															return serverLevel.getEntity(UUID.fromString(uuid));
														} catch (Exception e) {
														}
													}
													return null;
												}
											}).apply(world, (new Object() {
												public String getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getString(tag);
													return "";
												}
											}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))));
										}
										if (((new BiFunction<LevelAccessor, String, Entity>() {
											@Override
											public Entity apply(LevelAccessor levelAccessor, String uuid) {
												if (levelAccessor instanceof ServerLevel serverLevel) {
													try {
														return serverLevel.getEntity(UUID.fromString(uuid));
													} catch (Exception e) {
													}
												}
												return null;
											}
										}).apply(world, (new Object() {
											public String getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getString(tag);
												return "";
											}
										}.getValue(world, BlockPos.containing(x, y, z), "cashier_uuid")))) instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal(("You received a tip of \u01B5" + ((new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "total")) - (new Object() {
												public double getValue(LevelAccessor world, BlockPos pos, String tag) {
													BlockEntity blockEntity = world.getBlockEntity(pos);
													if (blockEntity != null)
														return blockEntity.getPersistentData().getDouble(tag);
													return -1;
												}
											}.getValue(world, BlockPos.containing(x, y, z), "price"))) + " as part of a purchase made at a cash register.")), false);
										if (entity instanceof Player _player) {
											ItemStack _setstack = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
													.copy();
											_setstack.setCount(1);
											ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
										}
										if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
											((Slot) _slots.get(0)).set(ItemStack.EMPTY);
											_player.containerMenu.broadcastChanges();
										}
									} else {
										if (entity instanceof Player _player && !_player.level().isClientSide())
											_player.displayClientMessage(Component.literal("It seems that the cashier is offline! Please try again later."), false);
									}
								} else {
									if (entity instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal("Sorry, it looks like you have insufficient funds!"), false);
									if (((new BiFunction<LevelAccessor, String, Entity>() {
										@Override
										public Entity apply(LevelAccessor levelAccessor, String uuid) {
											if (levelAccessor instanceof ServerLevel serverLevel) {
												try {
													return serverLevel.getEntity(UUID.fromString(uuid));
												} catch (Exception e) {
												}
											}
											return null;
										}
									}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
											.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))) instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + "" + " tried to buy \"" + (new Object() {
											public String getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getString(tag);
												return "";
											}
										}.getValue(world, BlockPos.containing(x, y, z), "product")) + "\" using your card with the number "
												+ Math.round((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
														.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h1n"))
												+ " " + Math.round((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
														.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("h2n"))
												+ ". However there were insufficient funds on your account.")), false);
									if (entity instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal("----------------------"), false);
									if (((new BiFunction<LevelAccessor, String, Entity>() {
										@Override
										public Entity apply(LevelAccessor levelAccessor, String uuid) {
											if (levelAccessor instanceof ServerLevel serverLevel) {
												try {
													return serverLevel.getEntity(UUID.fromString(uuid));
												} catch (Exception e) {
												}
											}
											return null;
										}
									}).apply(world, ((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
											.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("holder_uuid")))) instanceof Player _player && !_player.level().isClientSide())
										_player.displayClientMessage(Component.literal(("The payment total was \u01B5" + (new Object() {
											public double getValue(LevelAccessor world, BlockPos pos, String tag) {
												BlockEntity blockEntity = world.getBlockEntity(pos);
												if (blockEntity != null)
													return blockEntity.getPersistentData().getDouble(tag);
												return -1;
											}
										}.getValue(world, BlockPos.containing(x, y, z), "total")))), false);
									if (!world.isClientSide()) {
										BlockPos _bp = BlockPos.containing(x, y, z);
										BlockEntity _blockEntity = world.getBlockEntity(_bp);
										BlockState _bs = world.getBlockState(_bp);
										if (_blockEntity != null)
											_blockEntity.getPersistentData().putString("transaction_result", "FAIL");
										if (world instanceof Level _level)
											_level.sendBlockUpdated(_bp, _bs, _bs, 3);
									}
									if (entity instanceof Player _player) {
										ItemStack _setstack = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
												.copy();
										_setstack.setCount(1);
										ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
									}
									if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
										((Slot) _slots.get(0)).set(ItemStack.EMPTY);
										_player.containerMenu.broadcastChanges();
									}
								}
							} else {
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal("Sorry, this card has been canceled. Please use another form of payment!"), false);
								if (!world.isClientSide()) {
									BlockPos _bp = BlockPos.containing(x, y, z);
									BlockEntity _blockEntity = world.getBlockEntity(_bp);
									BlockState _bs = world.getBlockState(_bp);
									if (_blockEntity != null)
										_blockEntity.getPersistentData().putString("transaction_result", "FAIL");
									if (world instanceof Level _level)
										_level.sendBlockUpdated(_bp, _bs, _bs, 3);
								}
								if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
									((Slot) _slots.get(0)).set(ItemStack.EMPTY);
									_player.containerMenu.broadcastChanges();
								}
							}
						} else {
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal("It looks like the owner of this card is either offline or changed their name!"), false);
						}
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null)
							_blockEntity.getPersistentData().putBoolean("transaction", false);
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (!world.isClientSide()) {
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockEntity _blockEntity = world.getBlockEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_blockEntity != null)
							_blockEntity.getPersistentData().putBoolean("finished_transaction", true);
						if (world instanceof Level _level)
							_level.sendBlockUpdated(_bp, _bs, _bs, 3);
					}
					if (entity instanceof Player _player)
						_player.closeContainer();
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("Incorrect Pin!"), false);
					if (entity instanceof Player _player) {
						ItemStack _setstack = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						((Slot) _slots.get(0)).set(ItemStack.EMPTY);
						_player.containerMenu.broadcastChanges();
					}
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("This card hasn't been activated yet!"), false);
			}
		});
	}
}
