package eu.soulsmc.erbium.api.item;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import eu.soulsmc.erbium.api.item.action.IClickAction;

public record ClickableItem(ItemStack itemStack, IClickAction clickAction) {

    public void click(Player player) {
        if (clickAction != null) {
            clickAction.click(player);
        }
    }
}
