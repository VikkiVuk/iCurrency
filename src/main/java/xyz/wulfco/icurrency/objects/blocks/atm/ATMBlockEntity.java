package xyz.wulfco.icurrency.objects.blocks.atm;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.wulfco.icurrency.objects.blocks.atm.capability.ATMCapability;
import xyz.wulfco.icurrency.objects.blocks.atm.capability.ATMCapabilityImplementation;
import xyz.wulfco.icurrency.registry.BlockEntityRegistry;

public class ATMBlockEntity extends BlockEntity {
    public ATMBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ATM_BLOCK.get(), blockPos, blockState);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return LazyOptional.of(ATMCapabilityImplementation::new).cast();
    }
}
