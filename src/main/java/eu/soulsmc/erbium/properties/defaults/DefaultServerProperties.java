package eu.soulsmc.erbium.properties.defaults;

import net.minestom.server.world.Difficulty;
import eu.soulsmc.erbium.api.json.Document;

public class DefaultServerProperties {


    public static Document get() {
        Document document = new Document();

        document.addIfNotExists("address", "127.0.0.1");
        document.addIfNotExists("port", 25566);
        document.addIfNotExists("max-players", 20);
        document.addIfNotExists("motd", "A Erbium Server");
        document.addIfNotExists("online-mode", true);
        document.addIfNotExists("difficulty", Difficulty.EASY.name().toUpperCase());
        document.addIfNotExists("enable-default-commands", true);
        document.addIfNotExists("enable-default-handlers", true);
        document.addIfNotExists("enable-default-events", true);
        document.addIfNotExists("no-permission-message", "<dark_gray>┃ <red>Security <dark_gray>› <gray>You do not have permission to execute this command.");
        document.addIfNotExists("velocity", false);
        document.addIfNotExists("velocity-secret", "");
        return document;
    }
}
