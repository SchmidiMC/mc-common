package de.schmidimc.mc.common.helper;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public final class ReflectionHelper {

    public static final String getFullyQualifiedName(TypeElement element) {
        Element currElement = element;
        String result = element.getSimpleName().toString();
        while (currElement.getEnclosingElement() != null) {
            currElement = currElement.getEnclosingElement();
            if (currElement instanceof TypeElement) {
                result = currElement.getSimpleName() + "$" + result;
            } else if (currElement instanceof PackageElement) {
                if (!"".equals(currElement.getSimpleName())) {
                    result = ((PackageElement) currElement)
                            .getQualifiedName() + "." + result;
                }
            }
        }
        return result;
    }
}
