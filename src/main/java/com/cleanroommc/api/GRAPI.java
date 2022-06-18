package com.cleanroommc.api;

import com.cleanroommc.api.spacedefinition.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class GRAPI {

    /**
     * Contains functions for creating new definitions by their names
     */
    public static final Map<String, Function<String, ICelestialDefinition<?>>> CELESTIAL_DEFINITION_REGISTRY = new Object2ObjectAVLTreeMap<>(String.CASE_INSENSITIVE_ORDER);

    static {
        CELESTIAL_DEFINITION_REGISTRY.put(Galaxy.TYPE, Galaxy::new);
        CELESTIAL_DEFINITION_REGISTRY.put(GenericSystem.TYPE, GenericSystem::new);
        CELESTIAL_DEFINITION_REGISTRY.put(GenericSystemOrbital.TYPE, GenericSystemOrbital::new);
    }

    /**
     * contains every name of a registered definition
     */
    public static final Set<String> REGISTERED_NAMES = new ObjectOpenHashSet<>();

    /**
     * Contains every registered {@link Galaxy} by name
     */
    public static final Map<String, Galaxy> GALAXIES = new Object2ObjectAVLTreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * contains every registered {@link GenericSystem} by name
     */
    public static final Map<String, GenericSystem> SYSTEMS = new Object2ObjectAVLTreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * contains every {@link CelestialBodyLarge} by name
     */
    public static final Map<String, CelestialBodyLarge> LARGE_CELESTIAL_BODIES = new Object2ObjectAVLTreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * contains every {@link CelestialBody} by name
     */
    public static final Map<String, CelestialBody> CELESTIAL_BODIES = new Object2ObjectAVLTreeMap<>(String.CASE_INSENSITIVE_ORDER);
}
