package com.vikkivuk.mcurrency.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import com.vikkivuk.mcurrency.network.McurrencyModVariables;

public class CashRegisterSafeThisGUIIsOpenedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
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
	}
}
