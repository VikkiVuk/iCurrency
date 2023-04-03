package xyz.wulfco.icurrency.objects.blocks.atm;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.wulfco.icurrency.registry.BlockEntityRegistry;

public class ATMBlockEntity extends BlockEntity {
    public ATMBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ATM_BLOCK.get(), blockPos, blockState);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
