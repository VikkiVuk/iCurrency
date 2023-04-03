package xyz.wulfco.icurrency.capabilities.Wallet;

import net.minecraft.nbt.CompoundTag;

public class WalletCapability {
    private String walletAddress = "ic0000";
    private Double walletAmount = 0.0;

    public String getWalletAddress() {
        return walletAddress;
    }

    public Double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public void setWalletAmount(Double walletAmount) {
        this.walletAmount = walletAmount;
    }

    public void updateWalletAmount(Double amount) {
        this.walletAmount += amount;
    }

    public void saveNBTData(CompoundTag compound) {
        compound.putString("walletAddress", this.walletAddress);
        compound.putDouble("walletAmount", this.walletAmount);
    }

    public void loadNBTData(CompoundTag compound) {
        this.walletAddress = compound.getString("walletAddress");
        this.walletAmount = compound.getDouble("walletAmount");
    }

    public void copyFrom(WalletCapability source) {
        this.walletAddress = source.walletAddress;
        this.walletAmount = source.walletAmount;
    }
}
