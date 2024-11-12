
package com.vikkivuk.mcurrency.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import com.vikkivuk.mcurrency.procedures.ATMOnBlockRightClickedProcedure;
import com.vikkivuk.mcurrency.block.entity.ATMBlockEntity;

public class ATMBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public ATMBlock() {
		super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.GRAVEL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			default -> Shapes.or(box(3, 23, 12.6, 13, 29, 13.6), box(0, 0, 1, 16, 16, 16), box(4, 10, 0, 16, 16, 1), box(2, 15, 0, 4, 16, 1), box(3, 14, 0, 4, 15, 1), box(0, 10, 0, 2, 16, 1), box(3, 10, 0, 4, 13, 1), box(0, 0, 0, 16, 10, 1),
					box(0, 16, 0, 3, 32, 16), box(3, 16, 0, 13, 29, 1), box(3, 29, 0, 13, 32, 16), box(13, 16, 0, 16, 32, 16), box(3, 16, 15, 13, 19, 16), box(3, 13, 0.5, 4, 14, 1), box(2, 10, 0.5, 3, 13, 1), box(2, 13, 0.5, 3, 15, 1));
			case NORTH -> Shapes.or(box(3, 23, 2.4, 13, 29, 3.4), box(0, 0, 0, 16, 16, 15), box(0, 10, 15, 12, 16, 16), box(12, 15, 15, 14, 16, 16), box(12, 14, 15, 13, 15, 16), box(14, 10, 15, 16, 16, 16), box(12, 10, 15, 13, 13, 16),
					box(0, 0, 15, 16, 10, 16), box(13, 16, 0, 16, 32, 16), box(3, 16, 15, 13, 29, 16), box(3, 29, 0, 13, 32, 16), box(0, 16, 0, 3, 32, 16), box(3, 16, 0, 13, 19, 1), box(12, 13, 15, 13, 14, 15.5), box(13, 10, 15, 14, 13, 15.5),
					box(13, 13, 15, 14, 15, 15.5));
			case EAST ->
				Shapes.or(box(12.6, 23, 3, 13.6, 29, 13), box(1, 0, 0, 16, 16, 16), box(0, 10, 0, 1, 16, 12), box(0, 15, 12, 1, 16, 14), box(0, 14, 12, 1, 15, 13), box(0, 10, 14, 1, 16, 16), box(0, 10, 12, 1, 13, 13), box(0, 0, 0, 1, 10, 16),
						box(0, 16, 13, 16, 32, 16), box(0, 16, 3, 1, 29, 13), box(0, 29, 3, 16, 32, 13), box(0, 16, 0, 16, 32, 3), box(15, 16, 3, 16, 19, 13), box(0.5, 13, 12, 1, 14, 13), box(0.5, 10, 13, 1, 13, 14), box(0.5, 13, 13, 1, 15, 14));
			case WEST ->
				Shapes.or(box(2.4, 23, 3, 3.4, 29, 13), box(0, 0, 0, 15, 16, 16), box(15, 10, 4, 16, 16, 16), box(15, 15, 2, 16, 16, 4), box(15, 14, 3, 16, 15, 4), box(15, 10, 0, 16, 16, 2), box(15, 10, 3, 16, 13, 4), box(15, 0, 0, 16, 10, 16),
						box(0, 16, 0, 16, 32, 3), box(15, 16, 3, 16, 29, 13), box(0, 29, 3, 16, 32, 13), box(0, 16, 13, 16, 32, 16), box(0, 16, 3, 1, 19, 13), box(15, 13, 3, 15.5, 14, 4), box(15, 10, 2, 15.5, 13, 3), box(15, 13, 2, 15.5, 15, 3));
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos, Player entity, BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		ATMOnBlockRightClickedProcedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ATMBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ATMBlockEntity be) {
				Containers.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof ATMBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}
