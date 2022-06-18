package com.cleanroommc.api.spacedefinition;

import com.cleanroommc.config.GRDataReader;
import com.cleanroommc.util.GRLog;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines a system, containing many {@link CelestialBody}s
 */
public class GenericSystem implements ICelestialDefinition<CelestialBody> {

    public static final String TYPE = "generic_system";

    private final List<CelestialBody> celestialBodies;

    private final String name;

    public GenericSystem(@Nonnull String name) {
        this.name = name;
        this.celestialBodies = new ObjectArrayList<>();
    }

    @Override
    @Nonnull
    public final String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public final List<CelestialBody> getSubBodies() {
        return this.celestialBodies;
    }

    @Override
    public void initializeFromJson(@Nonnull JsonObject object, @Nonnull String fileName) {
        // must have the bodies array
        if (!object.has("bodies")) {
            GRLog.logger.error("JSON file \"{}\" has no \"bodies\" field defined", fileName);
            return;
        }
        JsonArray bodies = object.get("bodies").getAsJsonArray();
        for (JsonElement element : bodies) {
            ICelestialDefinition<?> definition = GRDataReader.readSubDefinition(element.getAsJsonObject(), "bodies", fileName);
            if (definition == null) {
                GRLog.logger.error("JSON file \"{}\" bodies had a null body!", fileName);
                continue;
            }
            if (!(definition instanceof CelestialBody)) {
                GRLog.logger.error("JSON file \"{}\" bodies with body name \"{}\" is not a celestial body.", fileName, definition.getName());
                continue;
            }
            this.celestialBodies.add((CelestialBody) definition);
        }
    }
}
