package xyz.wulfco.icurrency.capabilities.Wallet;

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

public class WalletCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<WalletCapability> WALLET_CAPABILITY = CapabilityManager.get(new CapabilityToken<WalletCapability>() {});

    private WalletCapability walletCapability;

    private final LazyOptional<WalletCapability> optional = LazyOptional.of(this::createWallet);

    private WalletCapability createWallet() {
        if (this.walletCapability == null) {
            this.walletCapability = new WalletCapability();
        }

        return this.walletCapability;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == WALLET_CAPABILITY) {
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
