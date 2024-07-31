package einstein.fired_pots;

import einstein.fired_pots.client.renderers.blockentity.ClayPotRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class FiredPotsFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        FiredPots.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.DECORATED_POT, new ItemStack(ModInit.CLAY_POT.get()));
            entries.addBefore(Blocks.FLOWER_POT, new ItemStack(ModInit.CLAY_FLOWER_POT.get()));
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries ->
                entries.addBefore(Items.BRICK, new ItemStack(ModInit.CRUSHED_POTTERY.get()))
        );

        LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
            if (source.isBuiltin()) {
                if (key.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON)) {
                    builder.modifyPools(poolBuilder -> {
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CRUSHED_POTTERY.get()).setWeight(2));
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CLAY_FLOWER_POT.get()));
                    });
                }
                else if (key.equals(BuiltInLootTables.VILLAGE_MASON)) {
                    builder.modifyPools(poolBuilder -> {
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CLAY_POT.get()));
                        poolBuilder.add(LootItem.lootTableItem(ModInit.CLAY_FLOWER_POT.get()));
                    });
                }
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(FiredPots::onDataReload);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> FiredPots.onDataReload(server));
    }

    @Override
    public void onInitializeClient() {
        FiredPots.clientSetup();
        BlockEntityRenderers.register(ModInit.CLAY_POT_BLOCK_ENTITY.get(), ClayPotRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ClayPotRenderer.MODEL_LAYER, ClayPotRenderer::createSidesLayer);
    }
}
