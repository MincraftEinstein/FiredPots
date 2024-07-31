package einstein.fired_pots.platform;

import einstein.fired_pots.FiredPots;
import einstein.fired_pots.platform.services.IPlatformHelper;
import einstein.fired_pots.platform.services.RegistryHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final RegistryHelper REGISTRY = load(RegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        FiredPots.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}