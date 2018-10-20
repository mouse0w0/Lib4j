package com.github.mouse0w0.lib4j.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

public class SinglePropertiesI18n extends I18nBase {

    public static SinglePropertiesI18n create(Path path) throws IOException {
        String fileName = path.getFileName().toString();
        Locale locale = Locale.forLanguageTag(fileName.substring(0, fileName.indexOf('.')).replace('_', '-'));
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(path)) {
            return create(locale, inputStream);
        }
    }

    public static SinglePropertiesI18n create(Locale locale, InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return new SinglePropertiesI18n(locale, properties);
    }

    private final Properties properties;

    protected SinglePropertiesI18n(Locale locale, Properties properties) {
        super.setDefaultLocale(locale);
        this.properties = properties;
    }

    @Override
    public String localize(Locale locale, String unlocalizedKey) {
        return properties.getProperty(unlocalizedKey, unlocalizedKey);
    }

    @Override
    public void setDefaultLocale(Locale locale) {
        throw new UnsupportedOperationException("SinglePropertiesI18n cannot set default locale.");
    }
}
