package xyz.wulfco.icurrency.capabilities.ATM;

import net.minecraft.nbt.CompoundTag;

public class ATMCapability {
    private String atmId = "icATM0000";
    private String atmKey = "0x000";
    private String owner = "Notch";

    public String getAtmId() {
        return atmId;
    }

    public String getAtmKey() {
        return atmKey;
    }

    public String getOwner() {
        return owner;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public void setAtmKey(String atmKey) {
        this.atmKey = atmKey;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void saveNBTData(CompoundTag compound) {
        compound.putString("atmId", this.atmId);
        compound.putString("atmKey", this.atmKey);
    }

    public void loadNBTData(CompoundTag compound) {
        this.atmId = compound.getString("atmId");
        this.atmKey = compound.getString("atmKey");
    }
}
