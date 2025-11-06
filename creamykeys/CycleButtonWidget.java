package me.anchorhelper.creamykeys;

import me.anchorhelper.creamykeys.client.KeySoundHandler;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import java.util.List;
import java.util.function.Consumer;

public class CycleButtonWidget extends ButtonWidget {
    private final String label;
    private final List<String> values;
    private int index;
    private final Consumer<String> onChange;

    public CycleButtonWidget(int x, int y, int w, int h, String label, List<String> values, String initial, Consumer<String> onChange) {
        super(x, y, w, h, Text.empty(), btn -> {}, DEFAULT_NARRATION_SUPPLIER);
        this.label = label;
        this.values = values;
        this.index = Math.max(0, values.indexOf(initial));
        this.onChange = onChange;
        updateText();
    }

    @Override
    public void onPress() {
        index = (index + 1) % values.size();
        updateText();
        String v = values.get(index);
        onChange.accept(v);
        KeySoundHandler.playPrimary();
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    private void updateText() {
        String v = values.get(Math.max(0, index));
        this.setMessage(Text.literal(label + ": " + v));
    }
}
