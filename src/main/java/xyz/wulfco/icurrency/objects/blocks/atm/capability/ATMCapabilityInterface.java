package xyz.wulfco.icurrency.objects.blocks.atm.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ATMCapabilityInterface extends INBTSerializable<CompoundTag> {
    String getPrivateKey();
    String getAtmId();

    void setPrivateKey(String privateKey);
    void setAtmId(String atmId);
}
