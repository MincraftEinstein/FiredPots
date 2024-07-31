package einstein.fired_pots.mixin;

import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DecoratedPotBlockEntity.class)
public interface DecoratedPotBlockEntityAccessor {

    @Accessor("decorations")
    void setDecorations(PotDecorations decorations);
}
