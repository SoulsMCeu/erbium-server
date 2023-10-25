package eu.soulsmc.erbium;

import eu.soulsmc.erbium.api.command.injector.CommandInjector;
import eu.soulsmc.erbium.api.command.loader.CommandLoader;
import eu.soulsmc.erbium.events.defaults.JoinEvent;
import eu.soulsmc.erbium.events.defaults.PingEvent;
import eu.soulsmc.erbium.properties.ServerProperties;
import eu.soulsmc.erbium.terminal.ErbiumTerminal;
import net.hollowcube.minestom.extensions.ExtensionBootstrap;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.Instance;
import eu.soulsmc.erbium.api.ErbiumServerAPI;

import java.io.IOException;
import java.util.List;

public class ErbiumServerBootstrap {

    private static ErbiumServerBootstrap instance;
    private final ExtensionBootstrap extensionBootstrap;

    private ErbiumServerBootstrap() throws IOException {
        instance = this;
        this.extensionBootstrap = ExtensionBootstrap.init();
        new ErbiumServerAPI(this.extensionBootstrap) {
        };
        MinecraftServer.setTerminalEnabled(false);
        MinecraftServer.setBrandName("Erbium " + "1.0");
        System.setProperty("minestom.chunk-view-distance", "10");
        System.setProperty("minestom.entity-view-distance", "32");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            MinecraftServer.LOGGER.info("Shutting down Erbium-Server...");
            for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
                instance.saveChunksToStorage().join();
                MinecraftServer.LOGGER.info("Instance {} has been saved.", instance.getUniqueId());
            }
            ErbiumTerminal.stop();
            MinecraftServer.stopCleanly();
        }));
        ServerProperties serverProperties = ErbiumServerAPI.getInstance().getServerProperties();

        if (serverProperties.getProperty("enable-default-commands", Boolean.class)) {
            CommandLoader commandLoader = new CommandLoader("eu.soulsmc.erbium.commands.defaults");
            List<Class<?>> classSet = commandLoader.load();
            CommandInjector.inject(classSet);
        }

        if (serverProperties.getProperty("online-mode", Boolean.class)) {
            MojangAuth.init();
        }

        if (serverProperties.getProperty("velocity", Boolean.class)) {
            VelocityProxy.enable(serverProperties.getProperty("velocity-secret", String.class));
        }

        if (serverProperties.getProperty("enable-default-events", Boolean.class)) {
            EventNode<Event> defaultNode = EventNode.all("default-node");
            defaultNode.addListener(new PingEvent());
            defaultNode.addListener(new JoinEvent());
            MinecraftServer.getGlobalEventHandler().addChild(defaultNode);
        }
        ErbiumTerminal.start();
        this.extensionBootstrap.start(serverProperties.getProperty("address", String.class), serverProperties.getProperty("port", int.class));
    }

    public static ErbiumServerBootstrap getInstance() {
        return instance;
    }

    public static void main(String[] args) throws IOException {
        new ErbiumServerBootstrap();
    }
}
