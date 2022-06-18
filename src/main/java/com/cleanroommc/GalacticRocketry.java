package com.cleanroommc;

import com.cleanroommc.api.word.GRDimensions;
import com.cleanroommc.command.GRCommand;
import com.cleanroommc.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import javax.annotation.Nonnull;

@Mod(modid = GalacticRocketry.MODID, name = GalacticRocketry.NAME, dependencies = GalacticRocketry.DEPENDENCIES)
public class GalacticRocketry {

    public static final String MODID = "galacticrocketry";
    public static final String NAME = "Galactic Rocketry";
    public static final String DEPENDENCIES = "required-after:modularui";

    @SidedProxy(clientSide = "com.cleanroommc.proxy.ClientProxy", serverSide = "com.cleanroommc.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void onPreInit(@Nonnull FMLPreInitializationEvent event) {
        GRDimensions.init();
    }

    @Mod.EventHandler
    public static void onServerLoad(@Nonnull FMLServerStartingEvent event) {
        event.registerServerCommand(new GRCommand());
    }
}
