package com.github.mouse0w0.lib4j.i18n;

import java.util.Locale;
import java.util.Objects;

public abstract class I18nBase implements I18n {

    private Locale defaultLocale;

    public I18nBase() {
        setDefaultLocale(Locale.getDefault());
    }

    @Override
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public void setDefaultLocale(Locale locale) {
        defaultLocale = Objects.requireNonNull(locale, "Locale cannot be null.");
    }
}
