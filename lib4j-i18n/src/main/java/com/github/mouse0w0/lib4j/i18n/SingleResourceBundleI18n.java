package com.github.mouse0w0.lib4j.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class SingleResourceBundleI18n extends I18nBase {

    private ResourceBundle resourceBundle;

    public SingleResourceBundleI18n(String baseName, Locale locale) {
        setDefaultLocale(locale);
        resourceBundle = ResourceBundle.getBundle(baseName, locale, new UTF8Control());
    }

    public SingleResourceBundleI18n(String baseName, Locale locale, ClassLoader classLoader) {
        setDefaultLocale(locale);
        resourceBundle = ResourceBundle.getBundle(baseName, locale, classLoader, new UTF8Control());
    }

    @Override
    public String localize(Locale locale, String unlocalizedKey) {
        return resourceBundle.containsKey(unlocalizedKey) ? resourceBundle.getString(unlocalizedKey) : unlocalizedKey;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
