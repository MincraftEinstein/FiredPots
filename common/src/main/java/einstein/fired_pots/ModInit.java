package einstein.fired_pots;

import einstein.fired_pots.block.ClayFlowerPotBlock;
import einstein.fired_pots.block.entity.ClayFlowerPotBlockEntity;
import einstein.fired_pots.block.ClayPotBlock;
import einstein.fired_pots.block.entity.ClayPotBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static einstein.fired_pots.FiredPots.loc;
import static einstein.fired_pots.platform.Services.REGISTRY;

public class ModInit {

    public static final TagKey<Block> FIRES_CLAY_POT_TAG = TagKey.create(Registries.BLOCK, loc("fires_clay_pot"));
    public static final Supplier<Block> CLAY_POT = registerBlock("clay_pot", () -> new ClayPotBlock(clayPotProperties()));
    public static final Supplier<Block> CLAY_FLOWER_POT = registerBlock("clay_flower_pot", () -> new ClayFlowerPotBlock(clayPotProperties()));
    public static final Supplier<BlockEntityType<ClayPotBlockEntity>> CLAY_POT_BLOCK_ENTITY = REGISTRY.registerBlockEntity("clay_pot", () -> BlockEntityType.Builder.of(ClayPotBlockEntity::new, CLAY_POT.get()).build(null));
    public static final Supplier<BlockEntityType<ClayFlowerPotBlockEntity>> CLAY_FLOWER_POT_BLOCK_ENTITY = REGISTRY.registerBlockEntity("clay_flower_pot", () -> BlockEntityType.Builder.of(ClayFlowerPotBlockEntity::new, CLAY_FLOWER_POT.get()).build(null));
    public static final Supplier<Item> CRUSHED_POTTERY = REGISTRY.registerItem("crushed_pottery", () -> new Item(new Item.Properties()));

    public static void init() {
    }

    private static Supplier<Block> registerBlock(String name, Supplier<Block> block) {
        Supplier<Block> instance = REGISTRY.registerBlock(name, block);
        REGISTRY.registerItem(name, () -> new BlockItem(instance.get(), new Item.Properties()));
        return instance;
    }

    private static BlockBehaviour.Properties clayPotProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).noOcclusion().pushReaction(PushReaction.DESTROY);
    }
}
