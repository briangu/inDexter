package demo.codeanalyzer.helper;


import com.sun.source.tree.CompilationUnitTree;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaFileObject;
import demo.codeanalyzer.common.model.Field;
import demo.codeanalyzer.common.model.JavaClassInfo;
import demo.codeanalyzer.common.model.LocationInfo;
import demo.codeanalyzer.common.model.Method;
import demo.codeanalyzer.common.util.CodeAnalyzerUtil;


/**
 * Helper class to set the location info of class, methods and fields to the java class model
 *
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class LocationInfoSetter
{

  /**
   * Set the location info of class and its methods and fields to the java class model
   *
   * @param clazzInfo The java class model
   */
  public static void setLocationInfoForElements(JavaClassInfo clazzInfo)
  {
    try
    {
      //Get compilation unit tree
      CompilationUnitTree compileTree = clazzInfo.getSourceTreeInfo().
                                                                       getCompileTree();
      //Java file which is being processed
      JavaFileObject file = compileTree.getSourceFile();
      String javaFileContent = file.getCharContent(true).toString();
      //Convert the java file content to character buffer
      CharBuffer buffer = getCharacterBufferOfSource(javaFileContent);
      //Set location info for various elements
      setLocInfoOfClass(clazzInfo, buffer, compileTree);
      setLocInfoOfConstructors(clazzInfo, buffer, compileTree);
      setLocInfoOfMethods(clazzInfo, buffer, compileTree);
      setLocInfoOfVariables(clazzInfo, buffer, compileTree);
    }
    catch (IOException ex)
    {
      Logger.getLogger(LocationInfoSetter.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Converts the java file content to character buffer
   *
   * @param javaFile Content of java file being processed
   *
   * @return Character buffer representation of the java source file
   */
  private static CharBuffer getCharacterBufferOfSource(String javaFile)
  {
    CharBuffer charBuffer = CharBuffer.wrap(javaFile.toCharArray());
    return charBuffer;
  }

  /**
   * Set the location info of class
   *
   * @param clazzInfo   The java class model
   * @param buffer      character buffer representation of java source
   * @param compileTree Compilation unit tree
   */
  private static void setLocInfoOfClass(JavaClassInfo clazzInfo, CharBuffer buffer, CompilationUnitTree compileTree)
  {
    String clazzName = CodeAnalyzerUtil.getSimpleNameFromQualifiedName(clazzInfo.getName());
    LocationInfo loc = (LocationInfo) clazzInfo.getLocationInfo();
    int startIndex = loc.getStartOffset();
    int endIndex = -1;
    if (startIndex >= 0)
    {
      String strToSearch = buffer.subSequence(startIndex, buffer.length()).toString();
      Pattern p = Pattern.compile(clazzName);
      Matcher matcher = p.matcher(strToSearch);
      matcher.find();
      startIndex = matcher.start() + startIndex;
      endIndex = startIndex + clazzName.length();
    }

    loc.setStartOffset(startIndex);
    loc.setEndOffset(endIndex);
    loc.setStartColumn(getRelativeOffset(compileTree, startIndex));
    loc.setStopColumn(getRelativeOffset(compileTree, endIndex));
    loc.setStartLineNumber(compileTree.getLineMap().getLineNumber(startIndex));
    loc.setStopLineNumber(compileTree.getLineMap().getLineNumber(endIndex));
  }

  private static int getRelativeOffset(CompilationUnitTree compileTree, int index)
  {
    try
    {
      return (int)compileTree.getLineMap().getColumnNumber(index);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      System.out.println("failed to get col for " + index);
    }

    return 0;
/*
    int originalIndex = index;
    int relativeOffset = 0;
    long startLineNumber = compileTree.getLineMap().getLineNumber(index);
    if (startLineNumber <= 0) return index;
    while(startLineNumber >= compileTree.getLineMap().getLineNumber(--index) && (index >= 0))
    {
      relativeOffset++;
    }

    return relativeOffset;
*/
  }

  /**
   * Set the location info of constructors
   *
   * @param clazzInfo   The java class model
   * @param buffer      character buffer representation of java source
   * @param compileTree Compilation unit tree
   */
  private static void setLocInfoOfConstructors(JavaClassInfo clazzInfo,
                                               CharBuffer buffer,
                                               CompilationUnitTree compileTree)
  {
    for (Method method : clazzInfo.getConstructors())
    {
      try
      {
        LocationInfo loc = (LocationInfo)method.getLocationInfo();
        int startIndex = loc.getStartOffset();
        int endIndex = -1;
        if (startIndex >= 0)
        {
          String strToSearch = buffer.subSequence(startIndex, buffer.length()).toString();
          Pattern p = Pattern.compile(method.getName());
          Matcher matcher = p.matcher(strToSearch);
          matcher.find();
          startIndex = matcher.start() + startIndex;
          endIndex = startIndex + method.getName().length();
        }
        loc.setStartOffset(startIndex);
        loc.setEndOffset(endIndex);
        loc.setStartColumn(getRelativeOffset(compileTree, startIndex));
        loc.setStopColumn(getRelativeOffset(compileTree, endIndex));
        loc.setStartLineNumber(compileTree.getLineMap().getLineNumber(startIndex));
        loc.setStopLineNumber(compileTree.getLineMap().getLineNumber(endIndex));
      }
      catch(IllegalStateException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Set the location info of methods
   *
   * @param clazzInfo   The java class model
   * @param buffer      character buffer representation of java source
   * @param compileTree Compilation unit tree
   */
  private static void setLocInfoOfMethods(JavaClassInfo clazzInfo, CharBuffer buffer, CompilationUnitTree compileTree)
  {
    for (Method method : clazzInfo.getMethods())
    {
      try
      {
        LocationInfo loc = (LocationInfo) method.getLocationInfo();
        int startIndex = loc.getStartOffset();
        int endIndex = -1;
        if (startIndex >= 0)
        {
          String strToSearch = buffer.subSequence(startIndex, buffer.length()).toString();
          Pattern p = Pattern.compile(method.getName());
          Matcher matcher = p.matcher(strToSearch);
          matcher.find();
          startIndex = matcher.start() + startIndex;
          endIndex = startIndex + method.getName().length();
        }
        loc.setStartOffset(startIndex);
        loc.setEndOffset(endIndex);
        loc.setStartColumn(getRelativeOffset(compileTree, startIndex));
        loc.setStopColumn(getRelativeOffset(compileTree, endIndex));
        loc.setStartLineNumber(compileTree.getLineMap().getLineNumber(startIndex));
        loc.setStopLineNumber(compileTree.getLineMap().getLineNumber(endIndex));
      }
      catch(IllegalStateException e)
      {
        e.printStackTrace();
      }
    }
  }

  private static void setLocInfoOfVariables(JavaClassInfo clazzInfo, CharBuffer buffer, CompilationUnitTree compileTree)
  {
    for (Field field : clazzInfo.getFields())
    {
      try
      {
        LocationInfo loc = (LocationInfo) field.getLocationInfo();
        int startIndex = loc.getStartOffset();
        int endIndex = -1;
        if (startIndex >= 0)
        {
          String strToSearch = buffer.subSequence(startIndex, buffer.length()).toString();
          Pattern p = Pattern.compile(field.getName());
          Matcher matcher = p.matcher(strToSearch);
          matcher.find();
          startIndex = matcher.start() + startIndex;
          endIndex = startIndex + field.getName().length();
        }
        loc.setStartOffset(startIndex);
        loc.setEndOffset(endIndex);
        loc.setStartColumn(getRelativeOffset(compileTree, startIndex));
        loc.setStopColumn(getRelativeOffset(compileTree, endIndex));
        loc.setStartLineNumber(compileTree.getLineMap().getLineNumber(startIndex));
        loc.setStopLineNumber(compileTree.getLineMap().getLineNumber(endIndex));
      }
      catch(IllegalStateException e)
      {
        e.printStackTrace();
      }
    }
  }
}
