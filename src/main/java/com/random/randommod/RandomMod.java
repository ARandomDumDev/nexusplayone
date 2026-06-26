package com.random.randommod;

import com.random.randommod.commands.RandomModCommand;
import com.random.randommod.config.ModConfig;
import com.random.randommod.managers.ModerationLogger;
import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RandomMod implements DedicatedServerModInitializer {
    public static final String MOD_ID = "randommod";
    public static final Logger LOGGER = LoggerFactory.getLogger("RandomMod");
    private static ModConfig config;
    private static ModerationLogger moderationLogger;

    @Override
    public void onInitializeServer() {
        config = ModConfig.load();
        moderationLogger = new ModerationLogger(config);
        RandomModCommand.register();
        LOGGER.info("RandomMod loaded for server-side moderation.");
    }

    public static ModConfig config() { return config; }
    public static ModerationLogger moderationLogger() { return moderationLogger; }
}
