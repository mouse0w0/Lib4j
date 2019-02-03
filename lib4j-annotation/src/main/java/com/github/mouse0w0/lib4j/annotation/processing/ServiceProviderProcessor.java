package com.github.mouse0w0.lib4j.annotation.processing;

import com.github.mouse0w0.lib4j.annotation.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ServiceProviderProcessor extends AbstractProcessor {

    private static final String SERVICE_PROVIDER_NAME = ServiceProvider.class.getName();

    private final Map<String, List<String>> services = new HashMap<>();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(SERVICE_PROVIDER_NAME);
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getElementsAnnotatedWith(ServiceProvider.class)) {
                if (!(element instanceof TypeElement)) {
                    continue;
                }
                addService(ProcessingUtils.getAnnotationValue(element, SERVICE_PROVIDER_NAME, "value").getValue().toString(), ((TypeElement) element).getQualifiedName().toString());
            }
        } else {
            saveAll();
        }
        return false;
    }

    private void addService(String service, String provider) {
        services.computeIfAbsent(service, s -> new LinkedList<>()).add(provider);
    }

    private void saveAll() {
        for (Map.Entry<String, List<String>> entry : services.entrySet()) {
            FileObject fileObject = null;
            try {
                fileObject = ProcessingUtils.getOrCreateResource(processingEnv, StandardLocation.CLASS_OUTPUT, "", "META-INF/services/" + entry.getKey());
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                e.printStackTrace();
            }

            if (fileObject == null) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Cannot create file object for " + entry.getKey());
                return;
            }

            try (Writer writer = fileObject.openWriter()) {
                for (String s : entry.getValue()) {
                    writer.append(s).append("\r\n");
                }
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
