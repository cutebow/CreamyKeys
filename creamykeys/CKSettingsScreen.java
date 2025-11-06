package me.anchorhelper.creamykeys;

import me.anchorhelper.creamykeys.client.CreamyKeysClient;
import me.anchorhelper.creamykeys.client.KeySoundHandler;
import me.anchorhelper.creamykeys.config.CKConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CKSettingsScreen extends Screen {
    private final Screen parent;
    private final List<String> packs = Arrays.asList(
            "cherrymx_blue_abs",
            "cherrymx_blue_abs_2",
            "cherrymx_black_abs",
            "cherrymx_black_pbt",
            "cherrymx_brown_abs",
            "cherrymx_brown_pbt",
            "cherrymx_red_abs"
    );

    public CKSettingsScreen(Screen parent) {
        super(Text.literal("CreamyKeys"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int y = this.height / 6;
        int w = 220;
        int center = this.width / 2 - w / 2;

        CKConfig cfg = CreamyKeysClient.CONFIG;

        this.addDrawableChild(new ToggleButtonWidget(center, y, w, 20, "Enabled", cfg.enabled, v -> { cfg.enabled = v; cfg.save(); }));
        y += 24;

        this.addDrawableChild(new CycleButtonWidget(center, y, w, 20, "Keyboard", packs, cfg.keyboardPrimary, v -> { cfg.keyboardPrimary = v; cfg.save(); KeySoundHandler.testPrimary(); }));
        y += 24;
        this.addDrawableChild(new KeySoundButtonWidget(center, y, w, 20, Text.literal("Test Keyboard"), btn -> KeySoundHandler.testPrimary()));
        y += 24;

        this.addDrawableChild(new ToggleButtonWidget(center, y, w, 20, "All Keys", cfg.allKeys, v -> { cfg.allKeys = v; cfg.save(); }));
        y += 24;

        this.addDrawableChild(new CycleButtonWidget(center, y, w, 20, "Mouse Keyboard", packs, cfg.keyboardSecondary, v -> { cfg.keyboardSecondary = v; cfg.save(); KeySoundHandler.testSecondary(); }));
        y += 24;
        this.addDrawableChild(new KeySoundButtonWidget(center, y, w, 20, Text.literal("Test Mouse"), btn -> KeySoundHandler.testSecondary()));
        y += 24;

        this.addDrawableChild(new ToggleButtonWidget(center, y, w, 20, "Mouse Keys", cfg.mouseKeys, v -> { cfg.mouseKeys = v; cfg.save(); }));
        y += 24;

        this.addDrawableChild(intSlider(center, y, w, "Volume", (int)Math.round(cfg.volume * 100), v -> {
            cfg.volume = Math.max(0f, Math.min(1f, v / 100f));
            cfg.save();
            if (this.client != null && this.client.getSoundManager() != null) {
                this.client.getSoundManager().updateSoundVolume(SoundCategory.PLAYERS, this.client.options.getSoundVolume(SoundCategory.PLAYERS));
            }
        }, val -> Text.literal("Volume: " + val + "%"), 0, 100));
        y += 24;

        this.addDrawableChild(intSlider(center, y, w, "Amplify", cfg.amplify, v -> { cfg.amplify = Math.max(1, Math.min(8, v)); cfg.save(); }, val -> Text.literal("Amplify: " + val + "x"), 1, 8));
        y += 28;

        this.addDrawableChild(new KeySoundButtonWidget(center, y, w, 20, Text.literal("Done"), btn -> this.close()));
    }

    private SliderWidget intSlider(int x, int y, int w, String label, int initial, Consumer<Integer> save, Function<Integer, Text> fmt, int min, int max) {
        double init = (initial - min) / (double)(max - min);
        return new SliderWidget(x, y, w, 20, fmt.apply(initial), init) {
            @Override
            protected void updateMessage() {
                setMessage(fmt.apply((int)Math.round(min + this.value * (max - min))));
            }
            @Override
            protected void applyValue() {
                save.accept((int)Math.round(min + this.value * (max - min)));
            }
        };
    }

    @Override
    public void render(DrawContext dc, int mouseX, int mouseY, float delta) {
        this.renderBackground(dc, mouseX, mouseY, delta);
        super.render(dc, mouseX, mouseY, delta);
        dc.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
