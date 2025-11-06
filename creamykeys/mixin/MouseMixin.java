package me.anchorhelper.creamykeys.mixin;

import me.anchorhelper.creamykeys.client.KeySoundHandler;
import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("TAIL"))
    private void creamykeys$onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (KeySoundHandler.consumeMouseSuppression()) return;
        if (action == GLFW.GLFW_PRESS &&
                (button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            KeySoundHandler.playSecondary();
        }
    }
}
