package einstein.fired_pots;

import einstein.fired_pots.api.ClayPotSherdTextureRegistry;
import einstein.fired_pots.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
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
        Util.removeRecipe(server.getRecipeManager(), mcLoc("decorated_pot"), RecipeType.CRAFTING);
        Util.removeRecipe(server.getRecipeManager(), mcLoc("flower_pot"), RecipeType.CRAFTING);
    }

    public static void clientSetup() {
        register(Items.ANGLER_POTTERY_SHERD, "angler_pottery_sherd");
        register(Items.ARCHER_POTTERY_SHERD, "archer_pottery_sherd");
        register(Items.ARMS_UP_POTTERY_SHERD, "arms_up_pottery_sherd");
        register(Items.BLADE_POTTERY_SHERD, "blade_pottery_sherd");
        register(Items.BREWER_POTTERY_SHERD, "brewer_pottery_sherd");
        register(Items.BURN_POTTERY_SHERD, "burn_pottery_sherd");
        register(Items.DANGER_POTTERY_SHERD, "danger_pottery_sherd");
        register(Items.EXPLORER_POTTERY_SHERD, "explorer_pottery_sherd");
        register(Items.FLOW_POTTERY_SHERD, "flow_pottery_sherd");
        register(Items.FRIEND_POTTERY_SHERD, "friend_pottery_sherd");
        register(Items.GUSTER_POTTERY_SHERD, "guster_pottery_sherd");
        register(Items.HEART_POTTERY_SHERD, "heart_pottery_sherd");
        register(Items.HEARTBREAK_POTTERY_SHERD, "heartbreak_pottery_sherd");
        register(Items.HOWL_POTTERY_SHERD, "howl_pottery_sherd");
        register(Items.MINER_POTTERY_SHERD, "miner_pottery_sherd");
        register(Items.MOURNER_POTTERY_SHERD, "mourner_pottery_sherd");
        register(Items.PLENTY_POTTERY_SHERD, "plenty_pottery_sherd");
        register(Items.PRIZE_POTTERY_SHERD, "prize_pottery_sherd");
        register(Items.SCRAPE_POTTERY_SHERD, "scrape_pottery_sherd");
        register(Items.SHEAF_POTTERY_SHERD, "sheaf_pottery_sherd");
        register(Items.SHELTER_POTTERY_SHERD, "shelter_pottery_sherd");
        register(Items.SKULL_POTTERY_SHERD, "skull_pottery_sherd");
        register(Items.SNORT_POTTERY_SHERD, "snort_pottery_sherd");
    }

    private static void register(Item item, String name) {
        ClayPotSherdTextureRegistry.INSTANCE.register(item, loc(name));
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
