package me.anchorhelper.creamykeys.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CKConfig {
    public boolean enabled = true;
    public boolean allKeys = true;
    public boolean mouseKeys = true;
    public String keyboardPrimary = "cherrymx_black_pbt";
    public String keyboardSecondary = "cherrymx_blue_abs_2";
    public float volume = 1.0f;
    public int amplify = 1;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("creamykeys.json");

    public static CKConfig load() {
        try {
            if (Files.exists(PATH)) return GSON.fromJson(Files.readString(PATH), CKConfig.class);
        } catch (Exception ignored) { }
        CKConfig c = new CKConfig();
        c.save();
        return c;
    }

    public void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(this));
        } catch (IOException ignored) { }
    }
}
