package com.random.randommod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.random.randommod.RandomMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public String banReason = "Banned by staff through RandomMod";
    public String kickReason = "Kicked by staff through RandomMod";
    public String muteMessage = "You have been muted by staff.";
    public boolean broadcastModerationActions = true;
    public boolean requirePermissions = true;
    public String logFile = "randommod-actions.log";

    public static ModConfig load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("randommod.json");
        try {
            if (Files.exists(path)) {
                try (Reader reader = Files.newBufferedReader(path)) {
                    ModConfig config = GSON.fromJson(reader, ModConfig.class);
                    return config == null ? writeDefault(path) : config.save(path);
                }
            }
            return writeDefault(path);
        } catch (IOException exception) {
            RandomMod.LOGGER.error("Failed to load RandomMod config; using defaults.", exception);
            return new ModConfig();
        }
    }

    private static ModConfig writeDefault(Path path) throws IOException {
        return new ModConfig().save(path);
    }

    private ModConfig save(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        try (Writer writer = Files.newBufferedWriter(path)) { GSON.toJson(this, writer); }
        return this;
    }
}
