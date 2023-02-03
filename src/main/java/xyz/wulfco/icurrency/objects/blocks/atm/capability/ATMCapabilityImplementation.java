package xyz.wulfco.icurrency.objects.blocks.atm.capability;

import net.minecraft.nbt.CompoundTag;

public class ATMCapabilityImplementation implements ATMCapabilityInterface {
    private String privateKey;
    private String atmId;

    public ATMCapabilityImplementation() {
        this.privateKey = "undefined";
        this.atmId = "undefined";
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public String getAtmId() {
        return atmId;
    }

    @Override
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putString("private_key", this.privateKey);
        tag.putString("atm_id", this.atmId);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.privateKey = nbt.getString("private_key");
        this.atmId = nbt.getString("atm_id");
    }
}
