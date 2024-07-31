package einstein.fired_pots.platform;

import einstein.fired_pots.platform.services.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import static einstein.fired_pots.FiredPots.loc;

public class FabricRegistryHelper implements RegistryHelper {

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> supplier) {
        T t = Registry.register(BuiltInRegistries.ITEM, loc(name), supplier.get());
        return () -> t;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> supplier) {
        T t = Registry.register(BuiltInRegistries.BLOCK, loc(name), supplier.get());
        return () -> t;
    }

    @Override
    public <T extends BlockEntityType<?>> Supplier<T> registerBlockEntity(String name, Supplier<T> type) {
        T t = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, loc(name), type.get());
        return () -> t;
    }
}
