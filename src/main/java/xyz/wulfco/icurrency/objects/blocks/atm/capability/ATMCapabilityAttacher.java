package xyz.wulfco.icurrency.objects.blocks.atm.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import xyz.wulfco.icurrency.iCurrency;
import xyz.wulfco.icurrency.objects.blocks.atm.ATMBlock;
import xyz.wulfco.icurrency.objects.blocks.atm.ATMBlockEntity;

import javax.annotation.Nullable;

public class ATMCapabilityAttacher {
    public static class ATMCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

        public static final ResourceLocation IDENTIFIER = new ResourceLocation(iCurrency.MOD_ID, "atm_capability");

        private final ATMCapabilityInterface backend = new ATMCapabilityImplementation();
        private final LazyOptional<ATMCapabilityInterface> optionalData = LazyOptional.of(() -> backend);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ATMCapability.INSTANCE.orEmpty(cap, this.optionalData);
        }

        void invalidate() {
            this.optionalData.invalidate();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    public static void attach(final AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof ATMBlockEntity) {
            event.addCapability(ATMCapabilityProvider.IDENTIFIER, new ATMCapabilityProvider());
        }
    }

    private ATMCapabilityAttacher() {
    }
}
