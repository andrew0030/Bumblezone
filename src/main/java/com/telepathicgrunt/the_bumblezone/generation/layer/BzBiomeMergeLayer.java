package com.telepathicgrunt.the_bumblezone.generation.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;


public enum BzBiomeMergeLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    public int apply(INoiseRandom iNoiseRandom, IArea iArea, IArea iArea1, int x, int z) {
        int biomeID1 = iArea.getValue(this.func_215721_a(x), this.func_215722_b(z));
        int biomeID2 = iArea1.getValue(this.func_215721_a(x), this.func_215722_b(z));

        if (biomeID1 == -1) {
            return biomeID2;
        }
        else {
            return biomeID1;
        }
    }
}
