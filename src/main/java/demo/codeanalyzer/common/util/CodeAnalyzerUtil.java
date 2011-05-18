package demo.codeanalyzer.common.util;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.TypeElement;

import demo.codeanalyzer.common.model.Annotation;
import demo.codeanalyzer.common.model.ClassFile;

/**
 * Encapsulates the utility methods of verification module
 *
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class CodeAnalyzerUtil {

    /**
     * Returns the simple name of an element from its fully qualified name
     * @param qualifiedName Fully qualified name of the element
     * @return simpleName Simple name of the element
     */
    public static String getSimpleNameFromQualifiedName(String qualifiedName) {

        String simpleName = null;
        if (qualifiedName != null && qualifiedName.length() > 0) {
            simpleName = qualifiedName.substring(1 + qualifiedName.
                         lastIndexOf("."), qualifiedName.length());
        }
        return simpleName;
    }
    
    public static List getAnnotationAsStringList (ClassFile classFile) {
        List<String> annotationList = new ArrayList<String>();
        for (Annotation clazzAnnotation : classFile.getAnnotations()) {
            annotationList.add(clazzAnnotation.getName());
        }    
        return annotationList;
    }    
}
