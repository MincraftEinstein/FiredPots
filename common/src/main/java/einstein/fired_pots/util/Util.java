package einstein.fired_pots.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import einstein.fired_pots.mixin.RecipeManagerAccessor;
import einstein.fired_pots.mixin.RecipeMapAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static void playBlockSound(Level level, BlockPos pos, SoundEvent sound, SoundType soundType) {
        playBlockSound(level, pos, sound, (soundType.getVolume() + 1) / 2, soundType.getPitch() * 0.8F);
    }

    public static void playBlockSound(Level level, BlockPos pos, SoundEvent sound, float volume, float pitch) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, volume, pitch);
    }

    public static void removeRecipe(RecipeManager recipeManager, Identifier id, RecipeType<?> type) {
        RecipeMapAccessor recipeMap = ((RecipeMapAccessor) ((RecipeManagerAccessor) recipeManager).getRecipeMap());
        Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> recipesByName = new HashMap<>(recipeMap.getRecipesByName());
        Multimap<RecipeType<?>, RecipeHolder<?>> recipesByType = HashMultimap.create(recipeMap.getRecipesByType());

        recipesByName.keySet().stream().filter(recipeId -> recipeId.identifier().equals(id))
                .findFirst().ifPresent(recipesByName::remove);

        recipesByType.keySet().stream().filter(recipeType -> recipeType.equals(type))
                .findFirst().ifPresent(recipeType ->
                        List.copyOf(recipesByType.get(type)).stream()
                                .filter(holder -> holder.id().identifier().equals(id))
                                .forEach(holder -> recipesByType.remove(recipeType, holder))
                );

        recipeMap.setRecipesByName(recipesByName);
        recipeMap.setRecipesByType(recipesByType);
    }
}
