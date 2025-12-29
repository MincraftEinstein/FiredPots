package einstein.fired_pots.client.renderers.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import einstein.fired_pots.FiredPots;
import einstein.fired_pots.block.entity.ClayPotBlockEntity;
import einstein.fired_pots.impl.ClayPotSherdTextureRegistryImpl;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;

public class ClayPotRenderer implements BlockEntityRenderer<ClayPotBlockEntity, ClayPotRenderer.RenderState> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FiredPots.loc("clay_pot_sides"), "main");

    private final MaterialSet materials;
    private final ModelPart northSide;
    private final ModelPart southSide;
    private final ModelPart eastSide;
    private final ModelPart westSide;

    public ClayPotRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.materials());
    }

    public ClayPotRenderer(EntityModelSet modelSet, MaterialSet materials) {
        ModelPart modelPart = modelSet.bakeLayer(MODEL_LAYER);
        northSide = modelPart.getChild("north");
        southSide = modelPart.getChild("south");
        eastSide = modelPart.getChild("east");
        westSide = modelPart.getChild("west");
        this.materials = materials;
    }

    public static LayerDefinition createSidesLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        CubeListBuilder builder = CubeListBuilder.create().texOffs(1, 0).addBox(0, 0, -0.021F, 14, 16, 0, EnumSet.of(Direction.NORTH));
        partDefinition.addOrReplaceChild("north", builder, PartPose.offsetAndRotation(1, 16, 15, (float) Math.PI, 0, 0));
        partDefinition.addOrReplaceChild("south", builder, PartPose.offsetAndRotation(15, 16, 1, 0, 0, (float) Math.PI));
        partDefinition.addOrReplaceChild("west", builder, PartPose.offsetAndRotation(15, 16, 15, 0, (float) (Math.PI / 2), (float) Math.PI));
        partDefinition.addOrReplaceChild("east", builder, PartPose.offsetAndRotation(1, 16, 1, 0, (float) (-Math.PI / 2), (float) Math.PI));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(ClayPotBlockEntity blockEntity, RenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.decorations = blockEntity.getDecorations();
    }

    @Override
    public void submit(RenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.translate(-0.5, 0, -0.5);

        submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.decorations, 0);
        poseStack.popPose();
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, PotDecorations decorations, int outlineColor) {
        submitSide(decorations.front(), northSide, poseStack, nodeCollector, packedLight, packedOverlay, outlineColor);
        submitSide(decorations.back(), southSide, poseStack, nodeCollector, packedLight, packedOverlay, outlineColor);
        submitSide(decorations.left(), westSide, poseStack, nodeCollector, packedLight, packedOverlay, outlineColor);
        submitSide(decorations.right(), eastSide, poseStack, nodeCollector, packedLight, packedOverlay, outlineColor);
    }

    private void submitSide(Optional<Item> sideItem, ModelPart part, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, int outlineColor) {
        sideItem.ifPresent(item -> {
            if (!item.equals(Items.AIR) && !item.equals(Items.BRICK)) {
                getSideMaterial(item).ifPresent(material ->
                        nodeCollector.submitModelPart(part, poseStack, material.renderType(RenderTypes::entityCutout), packedLight, packedOverlay, materials.get(material), false, false, -1, null, outlineColor)
                );
            }
        });
    }

    private static Optional<Material> getSideMaterial(Item item) {
        ResourceKey<DecoratedPotPattern> pattern = DecoratedPotPatterns.getPatternFromItem(item);
        if (ClayPotSherdTextureRegistryImpl.TEXTURES.containsKey(pattern)) {
            return Optional.of(ClayPotSherdTextureRegistryImpl.TEXTURES.get(pattern));
        }
        return Optional.ofNullable(Sheets.getDecoratedPotMaterial(pattern));
    }

    public static class RenderState extends BlockEntityRenderState {

        private PotDecorations decorations = PotDecorations.EMPTY;
    }
}
