package einstein.fired_pots;

import einstein.fired_pots.block.ClayFlowerPotBlock;
import einstein.fired_pots.block.ClayPotBlock;
import einstein.fired_pots.block.entity.ClayFlowerPotBlockEntity;
import einstein.fired_pots.block.entity.ClayPotBlockEntity;
import einstein.fired_pots.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static einstein.fired_pots.FiredPots.loc;
import static einstein.fired_pots.platform.Services.REGISTRY;

public class ModInit {

    public static final TagKey<Block> FIRES_CLAY_POT_TAG = TagKey.create(Registries.BLOCK, loc("fires_clay_pot"));
    public static final Supplier<Block> CLAY_POT = registerBlock("clay_pot", ClayPotBlock::new, ModInit::clayPotProperties);
    public static final Supplier<Block> CLAY_FLOWER_POT = registerBlock("clay_flower_pot", ClayFlowerPotBlock::new, ModInit::clayPotProperties);
    public static final Supplier<BlockEntityType<ClayPotBlockEntity>> CLAY_POT_BLOCK_ENTITY = REGISTRY.registerBlockEntity("clay_pot", () -> new BlockEntityType<>(ClayPotBlockEntity::new, Set.of(CLAY_POT.get())));
    public static final Supplier<BlockEntityType<ClayFlowerPotBlockEntity>> CLAY_FLOWER_POT_BLOCK_ENTITY = REGISTRY.registerBlockEntity("clay_flower_pot", () -> new BlockEntityType<>(ClayFlowerPotBlockEntity::new, Set.of(CLAY_FLOWER_POT.get())));
    public static final Supplier<Item> CRUSHED_POTTERY = registerItem("crushed_pottery", Item::new, new Item.Properties());

    public static void init() {
    }

    private static <T extends Block> Supplier<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block, Supplier<BlockBehaviour.Properties> blockProperties) {
        ResourceLocation id = loc(name);
        Supplier<T> instance = Services.REGISTRY.registerBlock(name, () -> block.apply(blockProperties.get()
                .setId(ResourceKey.create(Registries.BLOCK, id))));
        registerItem(name, properties -> new BlockItem(instance.get(), properties), new Item.Properties().useBlockDescriptionPrefix());
        return instance;
    }

    private static <T extends Item> Supplier<T> registerItem(String name, Function<Item.Properties, T> item, Item.Properties properties) {
        properties.setId(ResourceKey.create(Registries.ITEM, loc(name)));
        return Services.REGISTRY.registerItem(name, () -> item.apply(properties));
    }

    private static BlockBehaviour.Properties clayPotProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).noOcclusion().pushReaction(PushReaction.DESTROY);
    }
}
