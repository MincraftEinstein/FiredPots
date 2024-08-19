package einstein.fired_pots.api;

import einstein.fired_pots.impl.ClayPotSherdTextureRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public interface ClayPotSherdTextureRegistry {

    ClayPotSherdTextureRegistry INSTANCE = new ClayPotSherdTextureRegistryImpl();

    default <T extends ItemLike> void register(Supplier<T> supplier, ResourceLocation texture) {
        register(supplier.get(), texture);
    }

    void register(ItemLike itemLike, ResourceLocation texture);
}
