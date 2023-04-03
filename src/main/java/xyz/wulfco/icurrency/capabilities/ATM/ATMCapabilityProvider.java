package xyz.wulfco.icurrency.capabilities.ATM;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.wulfco.icurrency.capabilities.Wallet.WalletCapability;

public class ATMCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ATMCapability> ATM_CAPABILITY = CapabilityManager.get(new CapabilityToken<ATMCapability>() {});

    private ATMCapability atmCapability;

    private final LazyOptional<ATMCapability> optional = LazyOptional.of(this::createWallet);

    private ATMCapability createWallet() {
        if (this.atmCapability == null) {
            this.atmCapability = new ATMCapability();
        }

        return this.atmCapability;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ATM_CAPABILITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createWallet().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createWallet().loadNBTData(nbt);
    }
}
