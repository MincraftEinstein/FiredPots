package einstein.fired_pots.block.entity;

import einstein.fired_pots.ModInit;
import einstein.fired_pots.mixin.DecoratedPotBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class ClayPotBlockEntity extends ClayFlowerPotBlockEntity {

    private PotDecorations decorations = PotDecorations.EMPTY;

    public ClayPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModInit.CLAY_POT_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void placeCookedBlock() {
        if (level != null) {
            BlockPos pos = getBlockPos();
            level.setBlockAndUpdate(pos, Blocks.DECORATED_POT.defaultBlockState());

            BlockEntity newBlockEntity = level.getBlockEntity(pos);
            if (newBlockEntity instanceof DecoratedPotBlockEntity decoratedPotBlockEntity) {
                ((DecoratedPotBlockEntityAccessor) decoratedPotBlockEntity).setDecorations(new PotDecorations(decorations.front(), decorations.left(), decorations.right(), decorations.back()));
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveCustomOnly(provider);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        decorations = input.read("sherds", PotDecorations.CODEC).orElse(PotDecorations.EMPTY);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!decorations.equals(PotDecorations.EMPTY)) {
            output.store("sherds", PotDecorations.CODEC, decorations);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.POT_DECORATIONS, decorations);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter getter) {
        super.applyImplicitComponents(getter);
        decorations = getter.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        super.removeComponentsFromTag(output);
        output.discard("sherds");
    }

    public PotDecorations getDecorations() {
        return decorations;
    }

    public void setDecorations(PotDecorations decorations) {
        this.decorations = decorations;

        if (level != null) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }
}
