package com.github.mouse0w0.lib4j.annotation.processing;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import java.io.IOException;
import java.util.Map;

public class ProcessingUtils {

    public static FileObject getOrCreateResource(ProcessingEnvironment processingEnv,
                                                 JavaFileManager.Location location,
                                                 CharSequence moduleAndPkg,
                                                 CharSequence relativeName,
                                                 Element... originatingElements) throws IOException {
        try {
            return processingEnv.getFiler().createResource(location, moduleAndPkg, relativeName, originatingElements);
        } catch (FilerException e) {
            try {
                return processingEnv.getFiler().getResource(location, moduleAndPkg, relativeName);
            } catch (Exception e2) {
                e2.addSuppressed(e);
                throw e2;
            }
        }
    }

    public static AnnotationValue getAnnotationValue(Element element, String annoTypeName, String key) {
        for (AnnotationMirror anno : element.getAnnotationMirrors()) {
            if (!annoTypeName.equals(anno.getAnnotationType().toString())) {
                continue;
            }

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : anno.getElementValues().entrySet()) {
                if (key.equals(entry.getKey().getSimpleName().toString())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
