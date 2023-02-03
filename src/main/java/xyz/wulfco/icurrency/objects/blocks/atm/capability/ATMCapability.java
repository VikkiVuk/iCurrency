package xyz.wulfco.icurrency.objects.blocks.atm.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class ATMCapability {
    public static final Capability<ATMCapabilityInterface> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(ATMCapabilityInterface.class);
    }

    public ATMCapability() {
    }
}
