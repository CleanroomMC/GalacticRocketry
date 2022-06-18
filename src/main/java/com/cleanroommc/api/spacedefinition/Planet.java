package com.cleanroommc.api.spacedefinition;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines a Planet
 */
public abstract class Planet extends CelestialBody {

    private final List<Moon> moons;

    public Planet(@Nonnull String name) {
        super(name);
        this.moons = new ObjectArrayList<>();
    }

    public final List<Moon> getMoons() {
        return this.moons;
    }
}
