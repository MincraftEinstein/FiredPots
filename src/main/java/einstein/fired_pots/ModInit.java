package einstein.fired_pots;

import einstein.fired_pots.block.ClayFlowerPotBlock;
import einstein.fired_pots.block.ClayFlowerPotBlockEntity;
import einstein.fired_pots.block.ClayPotBlock;
import einstein.fired_pots.block.ClayPotBlockEntity;
import einstein.fired_pots.api.SherdTextureRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import static einstein.fired_pots.FiredPots.loc;

public class ModInit {

    public static final TagKey<Block> FIRES_CLAY_POT_TAG = TagKey.create(Registries.BLOCK, loc("fires_clay_pot"));
    public static final Block CLAY_POT = registerBlock("clay_pot", new ClayPotBlock(getProperties()));
    public static final Block CLAY_FLOWER_POT = registerBlock("clay_flower_pot", new ClayFlowerPotBlock(getProperties()));
    public static final BlockEntityType<ClayPotBlockEntity> CLAY_POT_BLOCK_ENTITY = registerBlockEntity("clay_pot", ClayPotBlockEntity::new, CLAY_POT);
    public static final BlockEntityType<ClayFlowerPotBlockEntity> CLAY_FLOWER_POT_BLOCK_ENTITY = registerBlockEntity("clay_flower_pot", ClayFlowerPotBlockEntity::new, CLAY_FLOWER_POT);
    public static final Item CRUSHED_POTTERY = registerItem("crushed_pottery", new Item(new Item.Properties()));

    public static void init() {
    }

    public static void initClient() {
        SherdTextureRegistry.INSTANCE.register(Items.ANGLER_POTTERY_SHERD, loc("angler_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.ARCHER_POTTERY_SHERD, loc("archer_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.ARMS_UP_POTTERY_SHERD, loc("arms_up_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.BLADE_POTTERY_SHERD, loc("blade_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.BREWER_POTTERY_SHERD, loc("brewer_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.BURN_POTTERY_SHERD, loc("burn_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.DANGER_POTTERY_SHERD, loc("danger_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.EXPLORER_POTTERY_SHERD, loc("explorer_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.FLOW_POTTERY_SHERD, loc("flow_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.FRIEND_POTTERY_SHERD, loc("friend_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.GUSTER_POTTERY_SHERD, loc("guster_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.HEART_POTTERY_SHERD, loc("heart_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.HEARTBREAK_POTTERY_SHERD, loc("heartbreak_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.HOWL_POTTERY_SHERD, loc("howl_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.MINER_POTTERY_SHERD, loc("miner_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.MOURNER_POTTERY_SHERD, loc("mourner_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.PLENTY_POTTERY_SHERD, loc("plenty_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.PRIZE_POTTERY_SHERD, loc("prize_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SCRAPE_POTTERY_SHERD, loc("scrape_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SHEAF_POTTERY_SHERD, loc("sheaf_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SHELTER_POTTERY_SHERD, loc("shelter_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SKULL_POTTERY_SHERD, loc("skull_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SNORT_POTTERY_SHERD, loc("snort_pottery_sherd"));
    }

    private static Block registerBlock(String name, Block block) {
        Block instance = Registry.register(BuiltInRegistries.BLOCK, loc(name), block);
        registerItem(name, new BlockItem(block, new Item.Properties()));
        return instance;
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, loc(name), item);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, loc(name), BlockEntityType.Builder.of(supplier, blocks).build(null));
    }

    private static BlockBehaviour.Properties getProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY).noOcclusion().pushReaction(PushReaction.DESTROY);
    }
}
