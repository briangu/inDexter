package demo.codeanalyzer.processor;


import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import demo.codeanalyzer.common.model.ClassModelMap;
import demo.codeanalyzer.common.model.JavaClassInfo;
import demo.codeanalyzer.helper.LocationInfoSetter;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * The annotation processor class which processes java annotaions in the supplied source file(s). This processor
 * supports v1.6 of java language and can processes all annotation types.
 *
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor
{

  private Trees trees;
  private BufferedWriter _writer;

  public CodeAnalyzerProcessor(BufferedWriter writer)
  {
    _writer = writer;
  }


  @Override
  public void init(ProcessingEnvironment pe)
  {
    super.init(pe);
    trees = Trees.instance(pe);
  }

  /**
   * Processes the annotation types defined for this processor.
   *
   * @param annotations      the annotation types requested to be processed
   * @param roundEnvironment environment to get information about the current and prior round
   *
   * @return whether or not the set of annotations are claimed by this processor
   */
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment)
  {
    for (Element e : roundEnvironment.getRootElements())
    {
      try
      {
        TreePath tp = trees.getPath(e);
        // invoke the scanner
        CodeAnalyzerTreeVisitor visitor = new CodeAnalyzerTreeVisitor();
        visitor.scan(tp, trees);
        TypeElement typeElement = (TypeElement) e;
        String className = typeElement.getQualifiedName().toString();
        JavaClassInfo clazzInfo = visitor.getClassInfo();
        LocationInfoSetter.setLocationInfoForElements(clazzInfo);

        clazzInfo.setSourceFile(((Symbol.ClassSymbol)e).sourcefile.toString());

        String json;

        json = clazzInfo.toSenseiDocumentJSON();
        System.out.println(json);
        _writer.write("sensei:" + json);
        _writer.write("\n");
        _writer.write("meta:" + clazzInfo.toClassMetaInfoJSON());
        _writer.write("\n");
//            ClassModelMap.getInstance().addClassInfo(className, clazzInfo);
//            CodeAnalyzer.getInstance().process(className);
      }
      catch (JSONException e1)
      {
        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      catch (NullPointerException ex)
      {
        ex.printStackTrace();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }

    return true;
  }
}
