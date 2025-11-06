package me.anchorhelper.creamykeys.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public final class KeySoundHandler {
    private static final MinecraftClient MC = MinecraftClient.getInstance();
    private static final AtomicBoolean SUPPRESS_MOUSE_ONCE = new AtomicBoolean(false);

    public static void playPrimary() {
        if (!CreamyKeysClient.CONFIG.enabled) return;
        playPack(CreamyKeysClient.CONFIG.keyboardPrimary, CreamyKeysClient.CONFIG.volume, CreamyKeysClient.CONFIG.amplify);
    }

    public static void playSecondary() {
        if (!CreamyKeysClient.CONFIG.enabled) return;
        if (!CreamyKeysClient.CONFIG.mouseKeys) return;
        playPack(CreamyKeysClient.CONFIG.keyboardSecondary, CreamyKeysClient.CONFIG.volume, CreamyKeysClient.CONFIG.amplify);
    }

    public static void testPrimary() {
        suppressNextMouseClick();
        playPack(CreamyKeysClient.CONFIG.keyboardPrimary, CreamyKeysClient.CONFIG.volume, CreamyKeysClient.CONFIG.amplify);
    }

    public static void testSecondary() {
        suppressNextMouseClick();
        playPack(CreamyKeysClient.CONFIG.keyboardSecondary, CreamyKeysClient.CONFIG.volume, CreamyKeysClient.CONFIG.amplify);
    }

    public static void suppressNextMouseClick() {
        SUPPRESS_MOUSE_ONCE.set(true);
    }

    public static boolean consumeMouseSuppression() {
        return SUPPRESS_MOUSE_ONCE.getAndSet(false);
    }

    private static void playPack(String pack, float customVolume, int amplify) {
        if (MC == null || MC.getSoundManager() == null) return;

        int n = ThreadLocalRandom.current().nextInt(1, 41);
        String nn = n < 10 ? "0" + n : Integer.toString(n);
        Identifier id = Identifier.of("creamykeys", "keyboards." + pack + ".key_" + nn);
        SoundEvent ev = SoundEvent.of(id);

        float master = MC.options.getSoundVolume(SoundCategory.MASTER);
        float players = MC.options.getSoundVolume(SoundCategory.PLAYERS);
        float base = Math.max(0f, Math.min(1f, customVolume * players));

        int layers = Math.max(1, amplify);
        for (int i = 0; i < layers; i++) {
            PositionedSoundInstance s = new PositionedSoundInstance(
                    ev.getId(),
                    SoundCategory.MASTER,
                    base,
                    1.0f,
                    Random.create(),
                    false,
                    0,
                    SoundInstance.AttenuationType.NONE,
                    0.0, 0.0, 0.0,
                    true
            );
            MC.getSoundManager().play(s);
        }
    }
}
