package com.github.mouse0w0.lib4j.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UTF8Control extends ResourceBundle.Control {
	
	@Override
	public String toBundleName(String baseName, Locale locale) {
		return getLanguageNameForBundle(baseName, locale);
	}

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		// The below is a copy of the default implementation.
		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, "lang");
		ResourceBundle bundle = null;
		InputStream stream = null;
		if (reload) {
			URL url = loader.getResource(resourceName);
			if (url != null) {
				URLConnection connection = url.openConnection();
				if (connection != null) {
					connection.setUseCaches(false);
					stream = connection.getInputStream();
				}
			}
		} else {
			stream = loader.getResourceAsStream(resourceName);
		}
		if (stream != null) {
			try {
				// Only this line is changed to make it to read properties files as UTF-8.
				bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
			} finally {
				stream.close();
			}
		}
		return bundle;
	}
	
	public static String getLanguageNameForBundle(String baseName, Locale locale) {
		if (locale == Locale.ROOT) {
			return baseName;
		}

		String language = locale.getLanguage();
		String script = locale.getScript();
		String country = locale.getCountry();
		String variant = locale.getVariant();

		if (Objects.equals(language, "") && Objects.equals(country, "") && Objects.equals(variant, "")) {
			return baseName;
		}

		StringBuilder sb = new StringBuilder(baseName);

		if (!(baseName.endsWith("/") || baseName.endsWith("\\")))
			sb.append('_');

		if (!Objects.equals(script, "")) {
			if (!Objects.equals(variant, "")) {
				sb.append(language).append('_').append(script).append('_').append(
					country).append('_').append(variant);
			} else if (!Objects.equals(country, "")) {
				sb.append(language).append('_').append(script).append('_').append(country);
			} else {
				sb.append(language).append('_').append(script);
			}
		} else {
			if (!Objects.equals(variant, "")) {
				sb.append(language).append('_').append(country).append('_').append(
					variant);
			} else if (!Objects.equals(country, "")) {
				sb.append(language).append('_').append(country);
			} else {
				sb.append(language);
			}
		}

		return sb.toString();
	}
}
