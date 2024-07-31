package einstein.fired_pots.util;

import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public interface ModifiableLootPool {

    void firedPots$add(LootPoolEntryContainer.Builder<?> entryBuilder);
}