package eu.soulsmc.erbium.api;

import eu.soulsmc.erbium.api.instance.InstanceProvider;
import eu.soulsmc.erbium.api.instance.type.InstanceType;
import eu.soulsmc.erbium.properties.ServerProperties;
import net.hollowcube.minestom.extensions.ExtensionBootstrap;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public abstract class ErbiumServerAPI {

    private static eu.soulsmc.erbium.api.ErbiumServerAPI instance;
    private final ExtensionBootstrap extensionBootstrap;
    private final InstanceProvider instanceProvider;
    private final ServerProperties serverProperties;

    public ErbiumServerAPI(ExtensionBootstrap extensionBootstrap) throws IOException {
        instance = this;
        this.serverProperties = new ServerProperties();
        this.extensionBootstrap = extensionBootstrap;
        this.instanceProvider = new InstanceProvider();
        this.instanceProvider.createInstance("world", InstanceType.FLAT);

        MinecraftServer.LOGGER.info("ErbiumServerAPI initialized.");
    }

    public static eu.soulsmc.erbium.api.ErbiumServerAPI getInstance() {
        return instance;
    }

    public ExtensionBootstrap getExtensionBootstrap() {
        return this.extensionBootstrap;
    }

    public InstanceProvider getInstanceProvider() {
        return this.instanceProvider;
    }

    public ServerProperties getServerProperties() {
        return this.serverProperties;
    }

    public Optional<Player> findPlayer(String name) {
        return Optional.ofNullable(MinecraftServer.getConnectionManager().getPlayer(name));
    }

    public Optional<Player> findPlayer(UUID uniqueId) {
        return Optional.ofNullable(MinecraftServer.getConnectionManager().getPlayer(uniqueId));
    }

    public void broadcastMessage(Component message) {
        for (Player onlinePlayer : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            onlinePlayer.sendMessage(message);
        }
    }

    public void changeSlots(int slots) {
        this.serverProperties.setProperty("max-players", slots);
        this.serverProperties.updateDocument();
        this.serverProperties.reload();
    }
}
