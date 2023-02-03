
package xyz.wulfco.icurrency.objects.blocks.atm;

import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.objects.blocks.atm.capability.ATMCapability;
import xyz.wulfco.icurrency.objects.blocks.atm.capability.ATMCapabilityAttacher;
import xyz.wulfco.icurrency.objects.blocks.atm.capability.ATMCapabilityInterface;
import xyz.wulfco.icurrency.objects.items.Card;
import xyz.wulfco.icurrency.registry.BlockRegistry;
import xyz.wulfco.icurrency.registry.ItemRegistry;
import xyz.wulfco.icurrency.util.NetworkHandler;
import xyz.wulfco.icurrency.world.inventory.EnterCVCMenu;

import javax.json.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ATMBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private final Map<Direction, LazyOptional<ATMCapabilityInterface>> cache = new EnumMap<>(Direction.class);

	public ATMBlock() {
		super(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL).strength(1f, 10f).lightLevel((state) -> 3));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return box(0, 0, 0, 16, 32, 16);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new ATMBlockEntity(blockPos, blockState);
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState blockState, boolean p_60570_) {
		if (!world.isClientSide()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			Player player = world.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false);

			if (blockEntity == null) {System.out.println("Provider is null!"); world.removeBlock(pos, false); return; }
			if (player == null) {System.out.println("Player is null!"); world.removeBlock(pos, false); return; }

			final LazyOptional<ATMCapabilityInterface> capability = blockEntity.getCapability(ATMCapability.INSTANCE);
			if (capability.isPresent()) {
				capability.ifPresent((cap) -> {
					JsonObject response;

					if (iCurrency.isCracked) {
						response = NetworkHandler.post("https://icurrency.wulfco.xyz/atm/new/cracked", Json.createObjectBuilder().add("server", (Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer()).isSingleplayer()) ? Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).ip : "false").add("player-name", player.getName().getString()).build());
					} else {
						response = NetworkHandler.post("https://icurrency.wulfco.xyz/atm/new/premium", Json.createObjectBuilder().add("server", (Objects.requireNonNull(Minecraft.getInstance().getSingleplayerServer()).isSingleplayer()) ? Objects.requireNonNull(Minecraft.getInstance().getCurrentServer()).ip : "false").add("player-uuid", player.getStringUUID()).build());
					}

					assert response != null;
					if (response.getBoolean("success")) {
						cap.setAtmId(response.getString("atmId"));
						cap.setPrivateKey(response.getString("privateKey"));
					} else {
						world.removeBlock(pos, false);
						player.displayClientMessage(new TextComponent("§c" + response.getString("message")), true);
					}
				});
			} else {
				world.removeBlock(pos, false);
				player.displayClientMessage(new TextComponent("§cSomething went wrong while setting the ATM up!"), true);
			}
		}
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
		return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		if (entity instanceof ServerPlayer player) {
			if (entity.getItemInHand(hand).getItem() == ItemRegistry.CARD.get()) {
				final String owner = Card.getOwner(entity.getItemInHand(hand));

				if (owner == null) { player.displayClientMessage(new TextComponent(ChatFormatting.RED + "This card doesn't have an owner!"), true); return InteractionResult.FAIL; }

				NetworkHooks.openGui(player, new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return new TextComponent("Enter CVC");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new EnterCVCMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
					}
				}, pos);
			} else {
				player.displayClientMessage(new TextComponent(ChatFormatting.RED + "You need a card to use this!"), true);
			}
		}
		return InteractionResult.SUCCESS;
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerRenderLayer() {
		ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ATM_BLOCK.get(), renderType -> renderType == RenderType.solid());
	}
}
