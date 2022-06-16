package com.cleanroommc.proxy;

import com.cleanroommc.GalacticRocketry;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void syncConfigValues(@Nonnull ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(GalacticRocketry.MODID)) {
            ConfigManager.sync(GalacticRocketry.MODID, Config.Type.INSTANCE);
        }
    }
}
