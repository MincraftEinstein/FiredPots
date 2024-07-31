package einstein.fired_pots.api;

import einstein.fired_pots.impl.SherdTextureRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface SherdTextureRegistry {

    SherdTextureRegistry INSTANCE = new SherdTextureRegistryImpl();

    default void register(Supplier<Item> item, ResourceLocation texture) {
        register(item.get(), texture);
    }

    void register(Item item, ResourceLocation texture);
}
