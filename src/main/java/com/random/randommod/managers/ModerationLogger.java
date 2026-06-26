package com.random.randommod.managers;

import com.random.randommod.RandomMod;
import com.random.randommod.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public final class ModerationLogger {
    private final Path logPath;

    public ModerationLogger(ModConfig config) {
        this.logPath = FabricLoader.getInstance().getGameDir().resolve("logs").resolve(config.logFile);
    }

    public void log(String action, String admin, String target, String detail) {
        String line = "[%s] action=%s admin=%s target=%s detail=%s%n".formatted(Instant.now(), action, admin, target, detail);
        try {
            Files.createDirectories(logPath.getParent());
            Files.writeString(logPath, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException exception) {
            RandomMod.LOGGER.error("Failed to write moderation log entry: {}", line.strip(), exception);
        }
        RandomMod.LOGGER.info(line.strip());
    }
}
