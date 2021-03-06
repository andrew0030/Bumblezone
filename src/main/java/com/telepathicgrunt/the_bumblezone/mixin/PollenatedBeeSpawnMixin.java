package com.telepathicgrunt.the_bumblezone.mixin;

import com.telepathicgrunt.the_bumblezone.Bumblezone;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public class PollenatedBeeSpawnMixin {

    //spawns bees with chance to bee full of pollen
    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
                at = @At(value = "TAIL"))
    private void pollinateSpawnedBee(EntityType<? extends BeeEntity> entityType, World world, CallbackInfo ci) {
        if (!world.isRemote() && world.getRegistryKey().getValue().equals(Bumblezone.MOD_DIMENSION_ID)) {
            BeeEntity beeEntity = (BeeEntity)(Object)this;

            //20% chance of being full of pollen
            if (world.rand.nextFloat() < 0.2f) {
                beeEntity.setBeeFlag(8, true);
            }
        }
    }
}