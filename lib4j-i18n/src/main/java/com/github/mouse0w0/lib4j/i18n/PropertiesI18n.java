package com.github.mouse0w0.lib4j.i18n;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesI18n extends I18nBase {

    private static final Logger logger = Logger.getLogger(PropertiesI18n.class.getName());

    private final Path path;
    private final Set<Locale> unfoundI18n = new HashSet<>();
    private final Map<Locale, I18n> loadedI18n = new HashMap<>();

    private I18n defaultI18n;

    public PropertiesI18n(Path path) {
        if(!Files.isDirectory(path))
            throw new IllegalArgumentException("Path must be a directory.");
        this.path = path;
    }

    @Override
    public String localize(Locale locale, String unlocalizedKey) {
        if (unfoundI18n.contains(locale))
            return localize(unlocalizedKey);

        return loadedI18n.getOrDefault(locale, defaultI18n).localize(locale, unlocalizedKey);
    }

    @Override
    public void setDefaultLocale(Locale locale) {
        super.setDefaultLocale(locale);
        if (unfoundI18n.contains(locale)) {
            defaultI18n = DefaultI18n.INSTANCE;
        } else {
            defaultI18n = getOrLoadI18n(locale);
        }
    }

    private I18n getOrLoadI18n(Locale locale) {
        I18n i18n = loadedI18n.get(locale);
        if (i18n != null)
            return i18n;

        Path langFile = path.resolve(locale.toLanguageTag() + ".lang");
        if (!Files.exists(langFile) || Files.isDirectory(langFile)) {
            unfoundI18n.add(locale);
            logger.log(Level.WARNING, String.format("Cannot load language file %s", langFile.toAbsolutePath()));
            return DefaultI18n.INSTANCE;
        }

        try {
            return SinglePropertiesI18n.create(langFile);
        } catch (IOException e) {
            unfoundI18n.add(locale);
            logger.log(Level.WARNING, String.format("Cannot load language file %s", langFile.toAbsolutePath()), e);
            return DefaultI18n.INSTANCE;
        }
    }
}
