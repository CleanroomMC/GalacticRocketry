package com.cleanroommc.api.spacedefinition;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines a Galaxy, containing many {@link GenericSystem}s and {@link CelestialBodyLarge}s
 */
public class Galaxy implements ICelestialDefinition<GenericSystem> {

    public static final String TYPE = "galaxy";

    private final List<GenericSystem> systems;

    private final String name;

    public Galaxy(@Nonnull String name) {
        this.name = name;
        this.systems = new ObjectArrayList<>();
    }

    @Override
    @Nonnull
    public final String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public final List<GenericSystem> getSubBodies() {
        return this.systems;
    }

    @Override
    public void initializeFromJson(@Nonnull JsonObject object, @Nonnull String fileName) {

    }
}
