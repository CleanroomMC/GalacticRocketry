package com.cleanroommc.api.spacedefinition;

import javax.annotation.Nonnull;

/**
 * Defines a Moon
 */
public abstract class Moon extends CelestialBody {

    public Moon(@Nonnull String name) {
        super(name);
    }
}
