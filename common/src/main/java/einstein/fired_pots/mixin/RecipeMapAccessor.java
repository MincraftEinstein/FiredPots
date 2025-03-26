package einstein.fired_pots.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RecipeMap.class)
public interface RecipeMapAccessor {

    @Final
    @Accessor("byKey")
    Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> getRecipesByName();

    @Mutable
    @Final
    @Accessor("byKey")
    void setRecipesByName(Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> recipesByName);

    @Final
    @Accessor("byType")
    Multimap<RecipeType<?>, RecipeHolder<?>> getRecipesByType();

    @Mutable
    @Final
    @Accessor("byType")
    void setRecipesByType(Multimap<RecipeType<?>, RecipeHolder<?>> recipesByType);
}
