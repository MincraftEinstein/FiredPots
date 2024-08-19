package einstein.fired_pots.impl;

import einstein.fired_pots.FiredPots;
import einstein.fired_pots.api.ClayPotSherdTextureRegistry;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

import java.util.HashMap;
import java.util.Map;

public class ClayPotSherdTextureRegistryImpl implements ClayPotSherdTextureRegistry {

    public static final Map<ResourceKey<DecoratedPotPattern>, Material> TEXTURES = new HashMap<>();

    @Override
    public void register(ItemLike itemLike, ResourceLocation texture) {
        Item item = itemLike.asItem();
        ResourceKey<DecoratedPotPattern> potPatternKey = DecoratedPotPatterns.getPatternFromItem(item);

        if (potPatternKey == null) {
            FiredPots.LOGGER.warn("Failed to register sherd texture: No pattern found for item {}", BuiltInRegistries.ITEM.getKey(item));
            return;
        }

        TEXTURES.put(potPatternKey, new Material(Sheets.DECORATED_POT_SHEET, texture.withPrefix("entity/clay_pot/")));
    }
}
