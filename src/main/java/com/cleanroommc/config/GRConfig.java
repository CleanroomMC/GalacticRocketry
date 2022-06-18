package com.cleanroommc.config;

import com.cleanroommc.GalacticRocketry;
import net.minecraftforge.common.config.Config;

@Config(modid = GalacticRocketry.MODID, name = GalacticRocketry.MODID + "/" + GalacticRocketry.MODID)
public class GRConfig {

    public static Dimensions dimensions = new Dimensions();

    public static class Dimensions {

        @Config.Comment({"The dimension id used for the \"Space\" dimension.", "Default: 2"})
        @Config.RangeInt()
        public int spaceId = 2;

        @Config.Comment({"Whether the player can respawn in the \"Space\" dimension.", "Default: false"})
        public boolean spaceRespawn = false;
    }
}
