package einstein.fired_pots.client.renderers.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import einstein.fired_pots.FiredPots;
import einstein.fired_pots.block.ClayPotBlockEntity;
import einstein.fired_pots.impl.SherdTextureRegistryImpl;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Optional;

public class ClayPotRenderer implements BlockEntityRenderer<ClayPotBlockEntity> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FiredPots.loc("clay_pot_sides"), "main");

    private final ModelPart northSide;
    private final ModelPart southSide;
    private final ModelPart eastSide;
    private final ModelPart westSide;

    public ClayPotRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER);
        northSide = modelPart.getChild("north");
        southSide = modelPart.getChild("south");
        eastSide = modelPart.getChild("east");
        westSide = modelPart.getChild("west");
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
    public void render(ClayPotBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        PotDecorations decorations = blockEntity.getDecorations();

        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.translate(-0.5, 0, -0.5);
        renderSide(decorations.front(), northSide, poseStack, buffer, packedLight, packedOverlay);
        renderSide(decorations.back(), southSide, poseStack, buffer, packedLight, packedOverlay);
        renderSide(decorations.left(), westSide, poseStack, buffer, packedLight, packedOverlay);
        renderSide(decorations.right(), eastSide, poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private static void renderSide(Optional<Item> sideItem, ModelPart part, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        sideItem.ifPresent(item -> {
            if (!item.equals(Items.AIR) && !item.equals(Items.BRICK)) {
                Optional<Material> material = getSideMaterial(item);
                material.ifPresent(value -> part.render(poseStack, value.buffer(buffer, RenderType::entityCutout), packedLight, packedOverlay));
            }
        });
    }

    private static Optional<Material> getSideMaterial(Item item) {
        ResourceKey<DecoratedPotPattern> pattern = DecoratedPotPatterns.getPatternFromItem(item);
        if (SherdTextureRegistryImpl.TEXTURES.containsKey(pattern)) {
            return Optional.of(SherdTextureRegistryImpl.TEXTURES.get(pattern));
        }
        return Optional.ofNullable(Sheets.getDecoratedPotMaterial(pattern));
    }
}
