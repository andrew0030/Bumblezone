package com.telepathicgrunt.the_bumblezone.blocks;

import com.telepathicgrunt.the_bumblezone.Bumblezone;
import com.telepathicgrunt.the_bumblezone.effects.BzEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

import java.util.Random;


public class FilledPorousHoneycomb extends Block {

    public FilledPorousHoneycomb() {
        super(Block.Properties.create(Material.CLAY, MaterialColor.ADOBE).hardnessAndResistance(0.5F, 0.5F).sound(SoundType.CORAL).velocityMultiplier(0.9f));
    }


    /**
     * Called when the given entity walks on this Block
     */
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        double yMagnitude = Math.abs(entityIn.getMotion().y);
        if (yMagnitude < 0.1D) {
            double slowFactor = 0.85D;
            entityIn.setMotion(entityIn.getMotion().mul(slowFactor, 1.0D, slowFactor));
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }


    /**
     * Allow player to harvest honey and put honey into this block using bottles
     */
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onUse(BlockState thisBlockState, World world, BlockPos position, PlayerEntity playerEntity, Hand playerHand, BlockRayTraceResult raytraceResult) {
        ItemStack itemstack = playerEntity.getHeldItem(playerHand);

        /*
         * Player is harvesting the honey from this block if it is filled with honey
         */
        if (itemstack.getItem() == Items.GLASS_BOTTLE) {
            world.setBlockState(position, BzBlocks.POROUS_HONEYCOMB.getDefaultState(), 3); // removed honey from this block
            world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);

            if (!playerEntity.isCreative()) {
                itemstack.shrink(1); // remove current empty bottle

                if (itemstack.isEmpty()) {
                    playerEntity.setHeldItem(playerHand, new ItemStack(Items.HONEY_BOTTLE)); // places honey bottle in hand
                } else if (!playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.HONEY_BOTTLE))) // places honey bottle in inventory
                {
                    playerEntity.dropItem(new ItemStack(Items.HONEY_BOTTLE), false); // drops honey bottle if inventory is full
                }
            }

            if ((playerEntity.getEntityWorld().getRegistryKey().getValue().equals(Bumblezone.MOD_DIMENSION_ID) ||
                    Bumblezone.BzBeeAggressionConfig.allowWrathOfTheHiveOutsideBumblezone.get()) &&
                    !playerEntity.isCreative() &&
                    !playerEntity.isSpectator() &&
                    Bumblezone.BzBeeAggressionConfig.aggressiveBees.get())
            {
                if(playerEntity.isPotionActive(BzEffects.PROTECTION_OF_THE_HIVE)){
                    playerEntity.removePotionEffect(BzEffects.PROTECTION_OF_THE_HIVE);
                }
                else{
                    //Now all bees nearby in Bumblezone will get VERY angry!!!
                    playerEntity.addPotionEffect(new EffectInstance(BzEffects.WRATH_OF_THE_HIVE, Bumblezone.BzBeeAggressionConfig.howLongWrathOfTheHiveLasts.get(), 2, false, Bumblezone.BzBeeAggressionConfig.showWrathOfTheHiveParticles.get(), true));
                }
            }

            return ActionResultType.SUCCESS;
        }

        return super.onUse(thisBlockState, world, position, playerEntity, playerHand, raytraceResult);
    }


    /**
     * Called periodically clientside on blocks near the player to show honey particles. 50% of attempting to spawn a
     * particle
     */
    @Override
    public void animateTick(BlockState blockState, World world, BlockPos position, Random random) {
        //number of particles in this tick
        for (int i = 0; i < random.nextInt(2); ++i) {
            this.spawnHoneyParticles(world, position, blockState);
        }
    }


    /**
     * tell redstone that this can be use with comparator
     */
    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }


    /**
     * the power fed into comparator 1
     */
    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return 1;
    }

    /**
     * Starts checking if the block can take the particle and if so and it passes another rng to reduce spawnrate, it then
     * takes the block's dimensions and passes into methods to spawn the actual particle
     */
    private void spawnHoneyParticles(World world, BlockPos position, BlockState blockState) {
        if (blockState.getFluidState().isEmpty() && world.rand.nextFloat() < 0.08F) {
            VoxelShape currentBlockShape = blockState.getCollisionShape(world, position);
            double yEndHeight = currentBlockShape.getEnd(Direction.Axis.Y);
            if (yEndHeight >= 1.0D && !blockState.isIn(BlockTags.IMPERMEABLE)) {
                double yStartHeight = currentBlockShape.getStart(Direction.Axis.Y);
                if (yStartHeight > 0.0D) {
                    this.addHoneyParticle(world, position, currentBlockShape, position.getY() + yStartHeight - 0.05D);
                } else {
                    BlockPos belowBlockpos = position.down();
                    BlockState belowBlockstate = world.getBlockState(belowBlockpos);
                    VoxelShape belowBlockShape = belowBlockstate.getCollisionShape(world, belowBlockpos);
                    double yEndHeight2 = belowBlockShape.getEnd(Direction.Axis.Y);
                    if ((yEndHeight2 < 1.0D || !belowBlockstate.isOpaqueCube(world, belowBlockpos)) && belowBlockstate.getFluidState().isEmpty()) {
                        this.addHoneyParticle(world, position, currentBlockShape, position.getY() - 0.05D);
                    }
                }
            }

        }
    }


    /**
     * intermediary method to apply the blockshape and ranges that the particle can spawn in for the next addHoneyParticle
     * method
     */
    private void addHoneyParticle(World world, BlockPos blockPos, VoxelShape blockShape, double height) {
        this.addHoneyParticle(
                world,
                blockPos.getX() + blockShape.getStart(Direction.Axis.X),
                blockPos.getX() + blockShape.getEnd(Direction.Axis.X),
                blockPos.getZ() + blockShape.getStart(Direction.Axis.Z),
                blockPos.getZ() + blockShape.getEnd(Direction.Axis.Z),
                height);
    }


    /**
     * Adds the actual honey particle into the world within the given range
     */
    private void addHoneyParticle(World world, double xMin, double xMax, double zMax, double zMin, double yHeight) {
        world.addParticle(
                ParticleTypes.DRIPPING_HONEY,
                MathHelper.lerp(world.rand.nextDouble(), xMin, xMax),
                yHeight,
                MathHelper.lerp(world.rand.nextDouble(), zMax, zMin),
                0.0D,
                0.0D,
                0.0D);
    }
}
