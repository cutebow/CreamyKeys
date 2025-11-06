package me.anchorhelper.creamykeys;

import me.anchorhelper.creamykeys.client.KeySoundHandler;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;

public class KeySoundButtonWidget extends ButtonWidget {
    public KeySoundButtonWidget(int x, int y, int w, int h, Text msg, PressAction onPress) {
        super(x, y, w, h, msg, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void onPress() {
        KeySoundHandler.suppressNextMouseClick();
        super.onPress();
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }
}
