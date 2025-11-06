package me.anchorhelper.creamykeys.client;

import me.anchorhelper.creamykeys.config.CKConfig;
import net.fabricmc.api.ClientModInitializer;

public class CreamyKeysClient implements ClientModInitializer {
    public static CKConfig CONFIG;

    @Override
    public void onInitializeClient() {
        CONFIG = CKConfig.load();
    }
}
