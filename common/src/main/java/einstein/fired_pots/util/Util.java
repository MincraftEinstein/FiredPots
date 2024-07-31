package einstein.fired_pots.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import einstein.fired_pots.mixin.RecipeManagerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;

import java.util.HashMap;
import java.util.Map;

public class Util {

    public static void playBlockSound(Level level, BlockPos pos, SoundEvent sound, SoundType soundType) {
        playBlockSound(level, pos, sound, (soundType.getVolume() + 1) / 2, soundType.getPitch() * 0.8F);
    }

    public static void playBlockSound(Level level, BlockPos pos, SoundEvent sound, float volume, float pitch) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, volume, pitch);
    }

    public static void removeRecipe(RecipeManager recipeManager, ResourceLocation id, RecipeType<?> type) {
        RecipeManagerAccessor manager = (RecipeManagerAccessor) recipeManager;
        Map<ResourceLocation, RecipeHolder<?>> recipesByName = new HashMap<>(manager.getRecipesByName());
        Multimap<RecipeType<?>, RecipeHolder<?>> recipesByType = HashMultimap.create(manager.getRecipesByType());

        for (ResourceLocation recipeId : recipesByName.keySet()) {
            if (recipeId.equals(id)) {
                recipesByName.remove(recipeId);
                break;
            }
        }

        boolean success = false;
        for (RecipeType<?> recipeType : recipesByType.keySet()) {
            if (recipeType.equals(type)) {
                for (RecipeHolder<?> holder : recipesByType.get(recipeType)) {
                    if (holder.id().equals(id)) {
                        recipesByType.remove(recipeType, holder);
                        success = true;
                        break;
                    }
                }

                if (success) {
                    break;
                }
            }
        }

        manager.setRecipesByName(recipesByName);
        manager.setRecipesByType(recipesByType);
    }
}
