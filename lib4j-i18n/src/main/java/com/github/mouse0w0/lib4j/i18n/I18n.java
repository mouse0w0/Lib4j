package com.github.mouse0w0.lib4j.i18n;

import java.util.Locale;

public interface I18n {

    String localize(Locale locale, String unlocalizedKey);

    default String localize(Locale locale, String unlocalizedKey, Object... args) {
        String localized = localize(locale, unlocalizedKey);
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof String)
                args[i] = localize(locale, (String) arg);
        }
        return String.format(localized, args);
    }

    default String localize(String unlocalizedKey) {
        return localize(getDefaultLocale(), unlocalizedKey);
    }

    default String localize(String unlocalizedKey, Object... args) {
        return localize(getDefaultLocale(), unlocalizedKey, args);
    }

    Locale getDefaultLocale();

    void setDefaultLocale(Locale locale);
}
