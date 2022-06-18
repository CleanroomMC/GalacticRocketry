package com.cleanroommc.api.spacedefinition;

import javax.annotation.Nonnull;

/**
 * Defines a Star
 */
public abstract class Star extends CelestialBody {

    public Star(@Nonnull String name) {
        super(name);
    }
}
