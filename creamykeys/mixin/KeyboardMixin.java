package me.anchorhelper.creamykeys.mixin;

import me.anchorhelper.creamykeys.client.CreamyKeysClient;
import me.anchorhelper.creamykeys.client.KeySoundHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "onKey", at = @At("TAIL"))
    private void creamykeys$onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action != GLFW.GLFW_PRESS) return;
        if (!CreamyKeysClient.CONFIG.enabled) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc != null && mc.options != null) {
            if (mc.options.useKey.matchesKey(key, scancode) || mc.options.attackKey.matchesKey(key, scancode)) {
                KeySoundHandler.playSecondary();
                return;
            }
        }

        if (!CreamyKeysClient.CONFIG.allKeys) return;
        KeySoundHandler.playPrimary();
    }
}
