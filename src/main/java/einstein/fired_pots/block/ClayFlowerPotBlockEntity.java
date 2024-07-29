package einstein.fired_pots.block;

import einstein.fired_pots.ModInit;
import einstein.fired_pots.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ClayFlowerPotBlockEntity extends BlockEntity {

    private static final int MAX_COOK_TIME = 200;
    private int cookTime;

    public ClayFlowerPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModInit.CLAY_FLOWER_POT_BLOCK_ENTITY, pos, state);
    }

    public ClayFlowerPotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ClayFlowerPotBlockEntity blockEntity) {
        if (isNextToHeatedBlock(level, pos) && !blockEntity.isWaterLogged()) {
            blockEntity.cookTime++;

            if (blockEntity.cookTime >= MAX_COOK_TIME) {
                blockEntity.cookTime = 0;
                blockEntity.placeCookedBlock();
                Util.playBlockSound(level, pos, SoundEvents.FIRE_EXTINGUISH, 0.3F, 1);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            }
        }
    }

    private static boolean isNextToHeatedBlock(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (direction != Direction.UP && level.getBlockState(pos.relative(direction)).is(ModInit.FIRES_CLAY_POT_TAG)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isWaterLogged() {
        return false;
    }

    protected void placeCookedBlock() {
        if (level != null) {
            BlockPos pos = getBlockPos();
            level.setBlockAndUpdate(pos, Blocks.FLOWER_POT.defaultBlockState());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        cookTime = tag.getInt("CookTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("CookTime", cookTime);
    }
}
