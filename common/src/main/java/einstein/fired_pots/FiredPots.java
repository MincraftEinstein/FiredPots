package einstein.fired_pots;

import einstein.fired_pots.api.SherdTextureRegistry;
import einstein.fired_pots.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiredPots {

    public static final String MOD_ID = "fired_pots";
    public static final String MOD_NAME = "Fired Pots";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        ModInit.init();
    }

    public static void onDataReload(MinecraftServer server) {
        Util.removeRecipe(server.getRecipeManager(), mcLoc("decorated_pot_simple"), RecipeType.CRAFTING);
        Util.removeRecipe(server.getRecipeManager(), mcLoc("flower_pot"), RecipeType.CRAFTING);
    }

    public static void clientSetup() {
        SherdTextureRegistry.INSTANCE.register(Items.ANGLER_POTTERY_SHERD, loc("angler_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.ARCHER_POTTERY_SHERD, loc("archer_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.ARMS_UP_POTTERY_SHERD, loc("arms_up_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.BLADE_POTTERY_SHERD, loc("blade_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.BREWER_POTTERY_SHERD, loc("brewer_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.BURN_POTTERY_SHERD, loc("burn_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.DANGER_POTTERY_SHERD, loc("danger_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.EXPLORER_POTTERY_SHERD, loc("explorer_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.FLOW_POTTERY_SHERD, loc("flow_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.FRIEND_POTTERY_SHERD, loc("friend_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.GUSTER_POTTERY_SHERD, loc("guster_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.HEART_POTTERY_SHERD, loc("heart_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.HEARTBREAK_POTTERY_SHERD, loc("heartbreak_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.HOWL_POTTERY_SHERD, loc("howl_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.MINER_POTTERY_SHERD, loc("miner_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.MOURNER_POTTERY_SHERD, loc("mourner_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.PLENTY_POTTERY_SHERD, loc("plenty_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.PRIZE_POTTERY_SHERD, loc("prize_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SCRAPE_POTTERY_SHERD, loc("scrape_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SHEAF_POTTERY_SHERD, loc("sheaf_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SHELTER_POTTERY_SHERD, loc("shelter_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SKULL_POTTERY_SHERD, loc("skull_pottery_sherd"));
        SherdTextureRegistry.INSTANCE.register(Items.SNORT_POTTERY_SHERD, loc("snort_pottery_sherd"));
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
