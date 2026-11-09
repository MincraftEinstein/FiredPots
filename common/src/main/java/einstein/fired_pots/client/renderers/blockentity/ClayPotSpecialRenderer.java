package einstein.fired_pots.client.renderers.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.Objects;
import java.util.function.Consumer;

public record ClayPotSpecialRenderer(ClayPotRenderer renderer) implements SpecialModelRenderer<PotDecorations> {

    @Override
    public void submit(@Nullable PotDecorations decorations, PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        renderer.submit(poseStack, nodeCollector, lightCoords, overlayCoords, Objects.requireNonNullElse(decorations, PotDecorations.EMPTY), outlineColor);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
    }

    @Override
    public @Nullable PotDecorations extractArgument(ItemStack stack) {
        return stack.get(DataComponents.POT_DECORATIONS);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<PotDecorations> {

        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(Unbaked::new);

        @Override
        public SpecialModelRenderer<PotDecorations> bake(BakingContext bakingContext) {
            return new ClayPotSpecialRenderer(new ClayPotRenderer(bakingContext.entityModelSet(), bakingContext.sprites()));
        }

        @Override
        public MapCodec<Unbaked> type() {
            return CODEC;
        }
    }
}
