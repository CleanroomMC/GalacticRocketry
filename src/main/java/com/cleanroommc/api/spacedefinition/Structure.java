package com.cleanroommc.api.spacedefinition;

import javax.annotation.Nonnull;

/**
 * Defines an Artificial Structure
 */
public abstract class Structure extends CelestialBody {

    public Structure(@Nonnull String name) {
        super(name);
    }
}
