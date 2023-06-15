package dev.pixelbrain.uuidpatch;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean allowOfflinePlayers = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        allowOfflinePlayers = configuration.getBoolean(
            "allowOfflinePlayers",
            Configuration.CATEGORY_GENERAL,
            allowOfflinePlayers,
            "Whether connections from usernames that cannot be found on Mojang's servers should be allowed."
                + "\nEither there is no user with that name or Mojang's servers couldn't be reached");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
