package com.cleanroommc.api.spacedefinition;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Defines a big celestial body
 */
public abstract class CelestialBodyLarge implements ICelestialDefinition<CelestialBodyLarge> {

    private final String name;

    public CelestialBodyLarge(@Nonnull String name) {
        this.name = name;
    }

    @Override
    @Nonnull
    public final String getName() {
        return this.name;
    }

    @Nonnull
    @Override
    public List<CelestialBodyLarge> getSubBodies() {
        return Collections.emptyList();
    }
}
