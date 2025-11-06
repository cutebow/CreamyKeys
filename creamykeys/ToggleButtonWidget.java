package me.anchorhelper.creamykeys;

import me.anchorhelper.creamykeys.client.KeySoundHandler;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import java.util.function.Consumer;

public class ToggleButtonWidget extends ButtonWidget {
    private final String label;
    private boolean value;
    private final Consumer<Boolean> onChange;

    public ToggleButtonWidget(int x, int y, int w, int h, String label, boolean initial, Consumer<Boolean> onChange) {
        super(x, y, w, h, Text.empty(), btn -> {}, DEFAULT_NARRATION_SUPPLIER);
        this.label = label;
        this.value = initial;
        this.onChange = onChange;
        updateText();
    }

    @Override
    public void onPress() {
        value = !value;
        updateText();
        onChange.accept(value);
        KeySoundHandler.playPrimary();
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    private void updateText() {
        this.setMessage(Text.literal(label + ": " + (value ? "ON" : "OFF")));
    }
}
