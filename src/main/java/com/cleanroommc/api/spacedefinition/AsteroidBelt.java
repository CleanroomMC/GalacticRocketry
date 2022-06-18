package com.cleanroommc.api.spacedefinition;

import com.google.gson.JsonObject;

import javax.annotation.Nonnull;

/**
 * Defines an Asteroid Belt
 */
public class AsteroidBelt extends CelestialBody {

    public AsteroidBelt(@Nonnull String name) {
        super(name);
    }

    @Override
    public void initializeFromJson(@Nonnull JsonObject object, @Nonnull String fileName) {

    }
}
