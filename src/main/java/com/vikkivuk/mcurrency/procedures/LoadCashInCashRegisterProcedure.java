package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.Map;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;
import com.vikkivuk.mcurrency.init.McurrencyModItems;

public class LoadCashInCashRegisterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double to_load = 0;
		if (!world.isClientSide()) {
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.current_slot_cr = 0;
				_vars.syncPlayerVariables(entity);
			}
			{
				McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
				_vars.max_slots_cr = 9;
				_vars.syncPlayerVariables(entity);
			}
			if (new Object() {
				public double getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getDouble(tag);
					return -1;
				}
			}.getValue(world, BlockPos.containing(x, y, z), "money") >= 576000) {
				to_load = 576000;
			} else {
				to_load = new Object() {
					public double getValue(LevelAccessor world, BlockPos pos, String tag) {
						BlockEntity blockEntity = world.getBlockEntity(pos);
						if (blockEntity != null)
							return blockEntity.getPersistentData().getDouble(tag);
						return -1;
					}
				}.getValue(world, BlockPos.containing(x, y, z), "money");
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putDouble("money", ((new Object() {
						public double getValue(LevelAccessor world, BlockPos pos, String tag) {
							BlockEntity blockEntity = world.getBlockEntity(pos);
							if (blockEntity != null)
								return blockEntity.getPersistentData().getDouble(tag);
							return -1;
						}
					}.getValue(world, BlockPos.containing(x, y, z), "money")) - to_load));
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (new Object() {
				public double getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getDouble(tag);
					return -1;
				}
			}.getValue(world, BlockPos.containing(x, y, z), "money") > 0) {
				{
					McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.load_more_disabled = false;
					_vars.syncPlayerVariables(entity);
				}
			} else {
				{
					McurrencyModVariables.PlayerVariables _vars = entity.getData(McurrencyModVariables.PLAYER_VARIABLES);
					_vars.load_more_disabled = true;
					_vars.syncPlayerVariables(entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 1000) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_THOUSAND.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 64000;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_THOUSAND.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 1000));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 1000) * 1000;
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 500) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_FIVE_HUNDRED.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 32000;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_FIVE_HUNDRED.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 500));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 500) * 500;
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 200) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWO_HUNDRED.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 12800;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWO_HUNDRED.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 200));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 200) * 200;
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 100) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_HUNDRED.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 6400;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_HUNDRED.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 100));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 100) * 100;
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 50) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_FIFTY.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 3200;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_FIFTY.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 50));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 50) * 50;
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 20) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWENTY.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 1280;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWENTY.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 20));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 20) * 20;
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				while (Math.floor(to_load / 10) >= 64) {
					if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
						ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TEN.get()).copy();
						_setstack.setCount(64);
						((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
						_player.containerMenu.broadcastChanges();
					}
					to_load = to_load - 640;
					CheckSlotCRSafeProcedure.execute(world, entity);
				}
			}
			CheckSlotCRSafeProcedure.execute(world, entity);
			if (-1 != entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr) {
				if (entity instanceof Player _player && _player.containerMenu instanceof Supplier _current && _current.get() instanceof Map _slots) {
					ItemStack _setstack = new ItemStack(McurrencyModItems.DOLLAR_TWENTY.get()).copy();
					_setstack.setCount((int) Math.floor(to_load / 10));
					((Slot) _slots.get((int) entity.getData(McurrencyModVariables.PLAYER_VARIABLES).current_slot_cr)).set(_setstack);
					_player.containerMenu.broadcastChanges();
				}
				to_load = to_load - Math.floor(to_load / 10) * 10;
			}
		}
	}
}
