package com.cleanroommc.api.word.provider;

import com.cleanroommc.api.render.SpaceSkyRenderer;
import com.cleanroommc.api.word.generator.ChunkGeneratorSpace;
import com.cleanroommc.config.GRConfig;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorldProviderSpace extends WorldProvider {

    @Override
    protected void init() {
        super.init();
        this.hasSkyLight = true;
        this.doesWaterVaporize = true; // no water in space
        this.setSkyRenderer(new SpaceSkyRenderer());
    }

    @Nonnull
    @Override
    public DimensionType getDimensionType() {
        return DimensionType.getById(GRConfig.dimensions.spaceId);
    }

    @Nonnull
    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorSpace(this.world);
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float angle, float partialTicks) {
        return Vec3d.ZERO; // black void
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    public boolean canRespawnHere() {
        return GRConfig.dimensions.spaceRespawn;
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 8.0F; // disables clouds
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return false; // no fog
    }

    @Override
    public float getStarBrightness(float partialTicks) {
        return 1.0F;
    }

    @Nullable
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }
}
