package eu.soulsmc.erbium.events.defaults;

import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class JoinEvent implements EventListener<PlayerLoginEvent> {

    @Override
    public @NotNull Class<PlayerLoginEvent> eventType() {
        return PlayerLoginEvent.class;
    }

    @Override
    public @NotNull Result run(@NotNull PlayerLoginEvent event) {
        eu.soulsmc.erbium.api.ErbiumServerAPI.getInstance().getInstanceProvider().getInstance("world").ifPresent(event::setSpawningInstance);
        return Result.SUCCESS;
    }
}
