package einstein.fired_pots.block;

import einstein.fired_pots.ModInit;
import einstein.fired_pots.mixin.DecoratedPotBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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

    @Override
    protected boolean isWaterLogged() {
        return getBlockState().getValue(BlockStateProperties.WATERLOGGED);
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
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        decorations = PotDecorations.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        decorations.save(tag);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.POT_DECORATIONS, decorations);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput input) {
        super.applyImplicitComponents(input);
        decorations = input.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        super.removeComponentsFromTag(tag);
        tag.remove("sherds");
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
