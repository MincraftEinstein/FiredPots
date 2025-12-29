package einstein.fired_pots.block;

import com.mojang.serialization.MapCodec;
import einstein.fired_pots.ModInit;
import einstein.fired_pots.block.entity.ClayPotBlockEntity;
import einstein.fired_pots.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ClayPotBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    private static final MapCodec<ClayPotBlock> CODEC = simpleCodec(ClayPotBlock::new);
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ClayPotBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ClayPotBlockEntity clayPotBlockEntity) {
            Direction hitDirection = hitResult.getDirection();
            if (hitDirection.getAxis() == Direction.Axis.Y) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }

            PotDecorations decorations = clayPotBlockEntity.getDecorations();
            Optional<Item> sideItem = List.of(decorations.front(), decorations.back(), decorations.left(), decorations.right())
                    .get(hitDirection.get3DDataValue() - 2);

            if (sideItem.isEmpty() || !(new ItemStack(sideItem.get())).is(ItemTags.DECORATED_POT_SHERDS)) {
                if (!level.isClientSide()) {
                    if (stack.is(ItemTags.DECORATED_POT_SHERDS)) {
                        setDecorations(stack.getItem(), clayPotBlockEntity, hitDirection, decorations);
                        Util.playBlockSound(level, pos, soundType.getPlaceSound(), soundType);
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                        stack.consume(1, player);
                        return InteractionResult.SUCCESS_SERVER;
                    }
                }
                return InteractionResult.CONSUME;
            }

            if (stack.is(Items.BRUSH)) {
                if (level.isClientSide()) {
                    return InteractionResult.CONSUME;
                }

                setDecorations(null, clayPotBlockEntity, hitDirection, decorations);
                Util.playBlockSound(level, pos, soundType.getBreakSound(), soundType);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

                if (!player.isCreative()) {
                    popResourceFromFace(level, pos, hitDirection, new ItemStack(sideItem.get()));
                }
                return InteractionResult.SUCCESS_SERVER;
            }
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        return InteractionResult.PASS;
    }

    private static void setDecorations(@Nullable Item item, ClayPotBlockEntity clayPotBlockEntity, Direction direction, PotDecorations decorations) {
        Optional<Item> optionalItem = Optional.ofNullable(item);
        clayPotBlockEntity.setDecorations(switch (direction) {
            case NORTH -> new PotDecorations(decorations.back(), decorations.left(), decorations.right(), optionalItem);
            case SOUTH ->
                    new PotDecorations(optionalItem, decorations.left(), decorations.right(), decorations.front());
            case EAST -> new PotDecorations(decorations.back(), decorations.left(), optionalItem, decorations.front());
            case WEST -> new PotDecorations(decorations.back(), optionalItem, decorations.right(), decorations.front());
            default -> decorations;
        });
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader reader, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(reader));
        }
        return super.updateShape(state, reader, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity blockentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof ClayPotBlockEntity clayPotBlockEntity) {
            builder.withDynamicDrop(DecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID, (consumer) -> {
                for (Item item : clayPotBlockEntity.getDecorations().ordered()) {
                    ItemStack stack = new ItemStack(item);

                    if (stack.is(ItemTags.DECORATED_POT_SHERDS)) {
                        consumer.accept(stack);
                    }
                }
            });
        }

        return super.getDrops(state, builder);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType computationType) {
        return false;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ClayPotBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ModInit.CLAY_POT_BLOCK_ENTITY.get(), ClayPotBlockEntity::serverTick);
    }
}
