package einstein.fired_pots.platform;

import einstein.fired_pots.platform.services.RegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static einstein.fired_pots.FiredPots.MOD_ID;

public class NeoForgeRegistryHelper implements RegistryHelper {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> supplier) {
        return ITEMS.register(name, supplier);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    @Override
    public <T extends BlockEntityType<?>> Supplier<T> registerBlockEntity(String name, Supplier<T> supplier) {
        return BLOCK_ENTITIES.register(name, supplier);
    }
}
