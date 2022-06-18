package com.cleanroommc.config;

import com.cleanroommc.util.GRLog;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtility {

    public static final JsonParser jsonParser = new JsonParser();

    @Nullable
    public static JsonObject getJsonFromFile(@Nonnull Path path) {
        try (InputStream stream = Files.newInputStream(path)) {
            InputStreamReader reader = new InputStreamReader(stream);
            return jsonParser.parse(reader).getAsJsonObject();
        } catch (IOException exception) {
            GRLog.logger.error("Failed to read file on path {}", path, exception);
        } catch (JsonParseException exception) {
            GRLog.logger.error("Failed to extract json from file", exception);
        } catch (Exception exception) {
            GRLog.logger.error("Failed to extract json from file on path {}", path, exception);
        }
        return null;
    }
}
