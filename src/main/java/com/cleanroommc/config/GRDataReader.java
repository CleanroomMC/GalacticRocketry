package com.cleanroommc.config;

import com.cleanroommc.GalacticRocketry;
import com.cleanroommc.api.GRAPI;
import com.cleanroommc.api.spacedefinition.ICelestialDefinition;
import com.cleanroommc.util.GRLog;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GRDataReader {

    public static void read() {
        try {
            readDefinitions();
        } catch (IOException | RuntimeException e) {
            GRLog.logger.fatal("Failed to initialize space definitions.", e);
        }
    }

    private static void readDefinitions() throws IOException {
        Path configPath = Loader.instance().getConfigDir().toPath().resolve(GalacticRocketry.MODID);

        // the file defining every galaxy
        Path galaxiesPath = configPath.resolve("galaxies.json");
        if (!Files.exists(galaxiesPath)) Files.createFile(galaxiesPath);

        // the Path for the base directory of galaxies
        Path galaxyPath = configPath.resolve("galaxy");
        if (!Files.exists(galaxyPath)) Files.createDirectories(galaxyPath);

        List<Path> objects;
        try (Stream<Path> walker = Files.walk(galaxyPath)) {
            objects = walker.filter(path -> path.toString().endsWith(".json"))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }

        for (Path object : objects) {
            JsonObject json = FileUtility.getJsonFromFile(object);
            if (json == null) break;

            // get the file name for logging
            final String fileName = galaxyPath.relativize(object).toString();

            // must have a name
            if (!json.has("name")) {
                GRLog.logger.error("JSON file '{}' has no main \"name\" field defined", fileName);
                continue;
            }

            // name must be unique
            final String name = json.get("name").getAsString();
            if (GRAPI.REGISTERED_NAMES.contains(name)) {
                GRLog.logger.error("JSON file '{}' main name \"{}\" is already defined", fileName, name);
                continue;
            }

            // must have a type
            if (!json.has("type")) {
                GRLog.logger.error("JSON file '{}' with main name \"{}\" has no \"type\" field defined", fileName, name);
                continue;
            }

            // type must exist
            final String type = json.get("type").getAsString();
            if (!GRAPI.CELESTIAL_DEFINITION_REGISTRY.containsKey(type)) {
                GRLog.logger.error("JSON file '{}' with main name \"{}\" type \"{}\" does not exist", fileName, name, type);
                continue;
            }

            // create a new definition and have it initialize itself
            ICelestialDefinition<?> definition = GRAPI.CELESTIAL_DEFINITION_REGISTRY.get(type).apply(name);
            definition.initializeFromJson(json, fileName);
        }
    }

    /**
     * Reads a definition's sub bodies
     *
     * @param json     the json object to read from
     * @param subListName  the name of the entry list for logging
     * @param fileName the name of the file for logging
     *
     * @return the sub definition
     */
    @Nullable
    public static ICelestialDefinition<?> readSubDefinition(@Nonnull JsonObject json, @Nonnull String subListName, @Nonnull String fileName) {
        // must have a name
        if (!json.has("name")) {
            GRLog.logger.error("JSON file '{}' with sub list '{}' has an entry with no \"name\" field defined", fileName, subListName);
            return null;
        }

        // name must be unique
        final String name = json.get("name").getAsString();
        if (GRAPI.REGISTERED_NAMES.contains(name)) {
            GRLog.logger.error("JSON file '{}' with sub list '{}' entry name \"{}\" is already defined", fileName, subListName, name);
            return null;
        }

        // must have a type
        if (!json.has("type")) {
            GRLog.logger.error("JSON file '{}' with sub list '{}' with name \"{}\" has no \"type\" field defined", fileName, subListName, name);
            return null;
        }

        // type must exist
        final String type = json.get("type").getAsString();
        if (!GRAPI.CELESTIAL_DEFINITION_REGISTRY.containsKey(type)) {
            GRLog.logger.error("JSON file '{}' with sub list '{}' with name \"{}\" type \"{}\" does not exist", fileName, subListName, name, type);
            return null;
        }

        // create a new definition and have it initialize itself
        ICelestialDefinition<?> definition = GRAPI.CELESTIAL_DEFINITION_REGISTRY.get(type).apply(name);
        definition.initializeFromJson(json, fileName);
        return definition;
    }
}
