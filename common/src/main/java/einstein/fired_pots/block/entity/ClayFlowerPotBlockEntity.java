package einstein.fired_pots.block.entity;

import einstein.fired_pots.ModInit;
import einstein.fired_pots.util.Util;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ClayFlowerPotBlockEntity extends BlockEntity {

    private static final int MAX_COOK_TIME = 200;
    private int cookTime;

    public ClayFlowerPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModInit.CLAY_FLOWER_POT_BLOCK_ENTITY.get(), pos, state);
    }

    public ClayFlowerPotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ClayFlowerPotBlockEntity blockEntity) {
        if (isNextToHeatedBlock(level, pos) && !(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED))) {
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
            BlockState state = level.getBlockState(pos.relative(direction));
            if (direction != Direction.UP && state.is(ModInit.FIRES_CLAY_POT_TAG)) {
                if (state.hasProperty(BlockStateProperties.LIT)) {
                    return state.getValue(BlockStateProperties.LIT);
                }
                return true;
            }
        }
        return false;
    }

    protected void placeCookedBlock() {
        if (level != null) {
            level.setBlockAndUpdate(getBlockPos(), Blocks.FLOWER_POT.defaultBlockState());
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        cookTime = input.getIntOr("CookTime", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("CookTime", cookTime);
    }
}
