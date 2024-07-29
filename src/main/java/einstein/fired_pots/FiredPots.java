package einstein.fired_pots;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiredPots implements ModInitializer {

    public static final String MOD_ID = "fired_pots";
    public static final String MOD_NAME = "Fired Pots";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        ModInit.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.DECORATED_POT, new ItemStack(ModInit.CLAY_POT));
            entries.addBefore(Blocks.FLOWER_POT, new ItemStack(ModInit.CLAY_FLOWER_POT));
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries ->
                entries.addBefore(Items.BRICK, new ItemStack(ModInit.CRUSHED_POTTERY))
        );

        LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
            if (source.isBuiltin()) {
                if (key.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON)) {
                    builder.modifyPools(poolBuilder -> {
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CRUSHED_POTTERY).setWeight(2));
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CLAY_FLOWER_POT));
                    });
                }
                else if (key.equals(BuiltInLootTables.VILLAGE_MASON)) {
                    builder.modifyPools(poolBuilder -> {
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CLAY_POT));
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CLAY_FLOWER_POT));
                    });
                }
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(FiredPots::onDataReload);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> onDataReload(server));
    }

    private static void onDataReload(MinecraftServer server) {
        Util.removeRecipe(server.getRecipeManager(), mcLoc("decorated_pot_simple"), RecipeType.CRAFTING);
        Util.removeRecipe(server.getRecipeManager(), mcLoc("flower_pot"), RecipeType.CRAFTING);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
