package com.cleanroommc.api.spacedefinition;

import com.cleanroommc.config.GRDataReader;
import com.cleanroommc.util.GRLog;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines a system orbiting one or more central bodies, containing many {@link CelestialBody}s
 */
public class GenericSystemOrbital extends GenericSystem {

    public static final String TYPE = "generic_orbital_system";

    private final List<CelestialBody> centralBodies;

    public GenericSystemOrbital(@Nonnull String name) {
        super(name);
        this.centralBodies = new ObjectArrayList<>();
    }

    @Nonnull
    public final List<CelestialBody> getCentralBodies() {
        return this.centralBodies;
    }

    @Override
    public void initializeFromJson(@Nonnull JsonObject object, @Nonnull String fileName) {
        super.initializeFromJson(object, fileName);

        // must have the central object
        if (!object.has("central")) {
            GRLog.logger.error("JSON file \"{}\" has no \"central\" field defined", fileName);
            return;
        }

        JsonObject central = object.get("central").getAsJsonObject();
        ICelestialDefinition<?> definition = GRDataReader.readSubDefinition(central, "central", fileName);
        if (definition == null) {
            GRLog.logger.error("JSON file \"{}\" central had a null body!", fileName);
            return;
        }
        if (!(definition instanceof CelestialBody)) {
            GRLog.logger.error("JSON file \"{}\" central with body name \"{}\" is not a celestial body.", fileName, definition.getName());
            return;
        }
        this.centralBodies.add((CelestialBody) definition);
    }
}
