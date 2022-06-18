package com.cleanroommc.api.spacedefinition;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines a celestial body containing many child {@link CelestialBody}s
 */
public abstract class CelestialBody implements ICelestialDefinition<CelestialBody> {

    private final List<CelestialBody> childBodies;

    private final String name;

    public CelestialBody(@Nonnull String name) {
        this.name = name;
        this.childBodies = new ObjectArrayList<>();
    }

    @Override
    @Nonnull
    public final String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public final List<CelestialBody> getSubBodies() {
        return this.childBodies;
    }
}
