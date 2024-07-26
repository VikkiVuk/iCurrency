package com.vikkivuk.icurrency.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import com.vikkivuk.icurrency.init.IcurrencyModItems;

public class CashRegisterGiveReceiptProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack receipt = ItemStack.EMPTY;
		receipt = (new ItemStack(IcurrencyModItems.RECEIPT.get()).copy());
		{
			final String _tagName = "valid";
			final boolean _tagValue = true;
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putBoolean(_tagName, _tagValue));
		}
		{
			final String _tagName = "cashier";
			final String _tagValue = (new Object() {
				public String getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getString(tag);
					return "";
				}
			}.getValue(world, BlockPos.containing(x, y, z), "cashier"));
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "cr_name";
			final String _tagValue = (new Object() {
				public String getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getString(tag);
					return "";
				}
			}.getValue(world, BlockPos.containing(x, y, z), "name"));
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "product";
			final String _tagValue = (new Object() {
				public String getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getString(tag);
					return "";
				}
			}.getValue(world, BlockPos.containing(x, y, z), "product"));
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putString(_tagName, _tagValue));
		}
		{
			final String _tagName = "total";
			final double _tagValue = (new Object() {
				public double getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getDouble(tag);
					return -1;
				}
			}.getValue(world, BlockPos.containing(x, y, z), "total"));
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putDouble(_tagName, _tagValue));
		}
		{
			final String _tagName = "tip";
			final double _tagValue = (new Object() {
				public double getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getDouble(tag);
					return -1;
				}
			}.getValue(world, BlockPos.containing(x, y, z), "tip"));
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putDouble(_tagName, _tagValue));
		}
		{
			final String _tagName = "price";
			final double _tagValue = (new Object() {
				public double getValue(LevelAccessor world, BlockPos pos, String tag) {
					BlockEntity blockEntity = world.getBlockEntity(pos);
					if (blockEntity != null)
						return blockEntity.getPersistentData().getDouble(tag);
					return -1;
				}
			}.getValue(world, BlockPos.containing(x, y, z), "price"));
			CustomData.update(DataComponents.CUSTOM_DATA, receipt, tag -> tag.putDouble(_tagName, _tagValue));
		}
		if (entity instanceof Player _player) {
			ItemStack _setstack = receipt.copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
	}
}
