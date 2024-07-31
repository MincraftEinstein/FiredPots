package einstein.fired_pots;

import einstein.fired_pots.client.renderers.blockentity.ClayPotRenderer;
import einstein.fired_pots.platform.NeoForgeRegistryHelper;
import einstein.fired_pots.util.ModifiableLootPool;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(FiredPots.MOD_ID)
public class FiredPotsNeoForge {

    public FiredPotsNeoForge(IEventBus eventBus) {
        FiredPots.init();
        NeoForgeRegistryHelper.ITEMS.register(eventBus);
        NeoForgeRegistryHelper.BLOCKS.register(eventBus);
        NeoForgeRegistryHelper.BLOCK_ENTITIES.register(eventBus);
        eventBus.addListener((FMLClientSetupEvent event) -> FiredPots.clientSetup());
        eventBus.addListener((EntityRenderersEvent.RegisterRenderers event) ->
                event.registerBlockEntityRenderer(ModInit.CLAY_POT_BLOCK_ENTITY.get(), ClayPotRenderer::new));

        eventBus.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) ->
                event.registerLayerDefinition(ClayPotRenderer.MODEL_LAYER, ClayPotRenderer::createSidesLayer));

        eventBus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            ResourceKey<CreativeModeTab> key = event.getTabKey();

            if (key.equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
                event.insertBefore(new ItemStack(Blocks.FLOWER_POT), new ItemStack(ModInit.CLAY_FLOWER_POT.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertBefore(new ItemStack(Blocks.DECORATED_POT), new ItemStack(ModInit.CLAY_POT.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
            else if (key.equals(CreativeModeTabs.INGREDIENTS)) {
                event.insertBefore(new ItemStack(Items.BRICK), new ItemStack(ModInit.CRUSHED_POTTERY.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        });

        NeoForge.EVENT_BUS.addListener((ServerStartingEvent event) -> FiredPots.onDataReload(event.getServer()));
        NeoForge.EVENT_BUS.addListener((OnDatapackSyncEvent event) -> FiredPots.onDataReload(event.getPlayerList().getServer()));

        // I know Loot Modifiers exist, but to my knowledge, you can't use them to modify an existing loot pool,
        // which is required for the game to pick only a single item from the table. This doesn't matter so much with
        // the Village Mason loot, but it does with the Trail Ruins loot (I think)
        NeoForge.EVENT_BUS.addListener((LootTableLoadEvent event) -> {
            ResourceLocation id = event.getName();
            LootTable table = event.getTable();
            LootPool pool = table.getPool("main");
            ModifiableLootPool modifiablePool = (ModifiableLootPool) pool;

            if (pool != null) {
                if (id.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON.location())) {
                    modifiablePool.firedPots$add(LootItem.lootTableItem(ModInit.CRUSHED_POTTERY.get()));
                    modifiablePool.firedPots$add(LootItem.lootTableItem(ModInit.CLAY_FLOWER_POT.get()));
                }
                else if (id.equals(BuiltInLootTables.VILLAGE_MASON.location())) {
                    modifiablePool.firedPots$add(LootItem.lootTableItem(ModInit.CLAY_POT.get()));
                    modifiablePool.firedPots$add(LootItem.lootTableItem(ModInit.CLAY_FLOWER_POT.get()));
                }
            }
        });
    }
}