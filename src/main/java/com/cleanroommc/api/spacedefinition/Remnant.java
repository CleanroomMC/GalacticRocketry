package com.cleanroommc.api.spacedefinition;

import javax.annotation.Nonnull;

/**
 * Defines a Stellar Remnant
 */
public abstract class Remnant extends CelestialBody {

    public Remnant(@Nonnull String name) {
        super(name);
    }
}
