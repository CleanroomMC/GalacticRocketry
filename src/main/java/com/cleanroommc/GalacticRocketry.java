package com.cleanroommc;

import com.cleanroommc.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = GalacticRocketry.MODID, name = GalacticRocketry.NAME)
public class GalacticRocketry {

    public static final String MODID = "galacticrocketry";
    public static final String NAME = "Galactic Rocketry";

    @SidedProxy(clientSide = "com.cleanroommc.proxy.ClientProxy", serverSide = "com.cleanroommc.proxy.CommonProxy")
    public static CommonProxy proxy = new CommonProxy();
}
