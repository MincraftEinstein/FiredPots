package einstein.fired_pots.client;

import einstein.fired_pots.ModInit;
import einstein.fired_pots.client.renderers.blockentity.ClayPotRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FiredPotsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModInit.initClient();
        BlockEntityRenderers.register(ModInit.CLAY_POT_BLOCK_ENTITY, ClayPotRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ClayPotRenderer.MODEL_LAYER, ClayPotRenderer::createSidesLayer);
    }
}
