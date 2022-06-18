package com.cleanroommc.api.world;

import com.cleanroommc.api.world.provider.WorldProviderSpace;
import com.cleanroommc.config.GRConfig;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class GRDimensions {

    public static DimensionType SPACE;

    public static void init() {
        SPACE = DimensionType.register("space", "_space", GRConfig.dimensions.spaceId, WorldProviderSpace.class, false);
        DimensionManager.registerDimension(GRConfig.dimensions.spaceId, SPACE);
    }
}
