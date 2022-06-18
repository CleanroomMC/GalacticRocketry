package com.cleanroommc.api.spacedefinition;

import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A definition for a celestial related object
 *
 * @param <T> the class of the sub body objects
 */
public interface ICelestialDefinition<T extends ICelestialDefinition<?>> {

    /**
     * @return the name of this definition
     */
    @Nonnull
    String getName();

    /**
     * @return the sub bodies of this definition, can be empty
     */
    @Nonnull
    List<T> getSubBodies();

    /**
     * Initializes this definition from json
     *
     * @param object   the json data to read from
     * @param fileName the name of this definition's file, for logging
     */
    void initializeFromJson(@Nonnull JsonObject object, @Nonnull String fileName);
}
