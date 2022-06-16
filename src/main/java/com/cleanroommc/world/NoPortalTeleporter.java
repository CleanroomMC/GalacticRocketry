package com.cleanroommc.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;

/**
 * Teleporter used to prevent issues with MC creating portals
 */
public class NoPortalTeleporter extends Teleporter {

    public NoPortalTeleporter(WorldServer worldIn) {
        super(worldIn);
    }

    @Override
    public boolean placeInExistingPortal(@Nonnull Entity entityIn, float rotationYaw) {
        return false;
    }

    @Override
    public void removeStalePortalLocations(long worldTime) {/**/}

    @Override
    public boolean makePortal(@Nonnull Entity entityIn) {
        return true;
    }
}
