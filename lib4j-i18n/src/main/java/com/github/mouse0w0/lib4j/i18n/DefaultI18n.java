package com.github.mouse0w0.lib4j.i18n;

import java.util.Locale;

public class DefaultI18n extends I18nBase {

    public static final DefaultI18n INSTANCE = new DefaultI18n();

    private DefaultI18n() {

    }

    @Override
    public String localize(Locale locale, String unlocalizedKey) {
        return unlocalizedKey;
    }
}
