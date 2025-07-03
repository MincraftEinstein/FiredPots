package einstein.fired_pots.client.renderers.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public record ClayPotSpecialRenderer(ClayPotRenderer renderer) implements SpecialModelRenderer<PotDecorations> {

    @Override
    public void render(@Nullable PotDecorations decorations, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoil) {
        renderer.renderSides(poseStack, bufferSource, packedLight, packedOverlay, Objects.requireNonNullElse(decorations, PotDecorations.EMPTY));
    }

    @Override
    public void getExtents(Set<Vector3f> set) {
    }

    @Override
    public @Nullable PotDecorations extractArgument(ItemStack stack) {
        return stack.get(DataComponents.POT_DECORATIONS);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(Unbaked::new);

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new ClayPotSpecialRenderer(new ClayPotRenderer(modelSet));
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
            return CODEC;
        }
    }
}
