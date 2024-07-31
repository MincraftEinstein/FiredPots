package einstein.fired_pots.platform.services;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface RegistryHelper {

    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> supplier);

    <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> supplier);

    <T extends BlockEntityType<?>> Supplier<T> registerBlockEntity(String name, Supplier<T> supplier);
}
