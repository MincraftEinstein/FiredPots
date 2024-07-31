package einstein.fired_pots.mixin;

import einstein.fired_pots.util.ModifiableLootPool;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.ArrayList;
import java.util.List;

@Mixin(LootPool.class)
public abstract class LootPoolMixin implements ModifiableLootPool {

    @Mutable
    @Accessor("entries")
    abstract void setEntries(List<LootPoolEntryContainer> entries);

    @Accessor("entries")
    abstract List<LootPoolEntryContainer> getEntries();

    @Override
    public void firedPots$add(LootPoolEntryContainer.Builder<?> entryBuilder) {
        List<LootPoolEntryContainer> newEntries = new ArrayList<>(getEntries());
        newEntries.add(entryBuilder.build());
        setEntries(newEntries);
    }
}
