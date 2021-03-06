package com.telepathicgrunt.the_bumblezone.fluids;

import com.telepathicgrunt.the_bumblezone.blocks.BzBlocks;
import com.telepathicgrunt.the_bumblezone.items.BzItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Random;


public abstract class SugarWaterFluid extends ForgeFlowingFluid {

    protected SugarWaterFluid(Properties properties) {
        super(properties);
    }

    @Override
    public Fluid getFlowingFluid() {
        return BzBlocks.SUGAR_WATER_FLUID_FLOWING;
    }

    @Override
    public Fluid getStillFluid() {
        return BzBlocks.SUGAR_WATER_FLUID;
    }

    @Override
    public Item getFilledBucket() {
        return BzItems.SUGAR_WATER_BUCKET;
    }

    @Override
    public void randomTick(World world, BlockPos position, FluidState state, Random random) {
        //only attempts to grow sugar cane 50% of the time.
        if (random.nextBoolean() || !world.isAreaLoaded(position, position))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light

        //check one of the spot next to sugar water for sugar cane to grow
        BlockPos.Mutable blockPos = new BlockPos.Mutable().setPos(position.up());
        blockPos.move(Direction.byHorizontalIndex(random.nextInt(4)));
        BlockState blockstate = world.getBlockState(blockPos);

        if (blockstate.getBlock() == Blocks.SUGAR_CANE) {
            int height = 1;
            blockstate = world.getBlockState(blockPos.move(Direction.UP));

            //find top of sugar cane or
            while (blockstate.getBlock() == Blocks.SUGAR_CANE && height < 5) {
                blockstate = world.getBlockState(blockPos.move(Direction.UP));
                height++;
            }

            //at top of sugar cane. Time to see if it can grow more
            if (height < 5 && blockstate.getMaterial() == Material.AIR) {
                world.setBlockState(blockPos, Blocks.SUGAR_CANE.getDefaultState(), 3);
            }
        }
    }


    @Override
    public void animateTick(World worldIn, BlockPos pos, FluidState state, Random random) {
        if (!state.isSource() && !state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                worldIn.playSound(
                        (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D,
                        (double) pos.getZ() + 0.5D,
                        SoundEvents.BLOCK_WATER_AMBIENT,
                        SoundCategory.BLOCKS,
                        random.nextFloat() * 0.25F + 0.75F,
                        random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.UNDERWATER,
                    (double) pos.getX() + (double) random.nextFloat(),
                    (double) pos.getY() + (double) random.nextFloat(),
                    (double) pos.getZ() + (double) random.nextFloat(),
                    0.0D,
                    0.0D,
                    0.0D);
        }
    }


    @Override
    public IParticleData getDripParticleData() {
        return ParticleTypes.DRIPPING_WATER;
    }


    @Override
    protected boolean ticksRandomly() {
        return true;
    }


    @Override
    public int getTickRate(IWorldReader world) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    protected void beforeReplacingBlock(IWorld world, BlockPos pos, BlockState state) {
        TileEntity blockEntity = state.getBlock().hasBlockEntity() ? world.getTileEntity(pos) : null;
        Block.spawnDrops(state, world, pos, blockEntity);
    }

    @Override
    public int getSlopeFindDistance(IWorldReader world) {
        return 4;
    }

    @Override
    public int getLevelDecreasePerBlock(IWorldReader world) {
        return 1;
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid.isIn(FluidTags.WATER);
    }

    @Override
    public boolean canDisplace(FluidState state, IBlockReader world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    public BlockState getBlockState(FluidState state) {
        return BzBlocks.SUGAR_WATER_BLOCK.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    public static class Flowing extends SugarWaterFluid {
        public Flowing(Properties properties) {
            super(properties);
            setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
        }

        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        protected boolean canSourcesMultiply() {
            return true;
        }
    }

    public static class Source extends SugarWaterFluid {

        public Source(Properties properties) {
            super(properties);
            setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
        }

        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        protected boolean canSourcesMultiply() {
            return false;
        }
    }
}
