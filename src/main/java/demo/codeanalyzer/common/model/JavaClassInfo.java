package demo.codeanalyzer.common.model;


//import com.google.common.base.Joiner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.NestingKind;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Stores basic attributes of a java class
 *
 * @author Deepa Sobhana, Seema Richard
 */
public class JavaClassInfo extends BaseJavaClassModelInfo implements ClassFile, Serializable
{
  private long id;
  private String hashId;
  private String sourceFile;
  private String nameOfSuperClass;
  private String packageName;
  private String nestingKind;
  private boolean isInterface;
  private boolean isSerializable;
  private JavaSourceTreeInfo sourceTreeInfo;
  private Collection<Method> methods = new ArrayList<Method>();
  private Collection<Method> constructors = new ArrayList<Method>();
  private Collection<String> nameOfInterfaces = new ArrayList<String>();
  private Collection<String> classTypes = new ArrayList<String>();
  private Map<String, Field> fields = new HashMap<String, Field>();

  public void setSourceFile(String filename)
  {
    this.sourceFile = filename;
  }

  public String getSourceFile()
  {
    return this.sourceFile;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public void setHashId(String hashId)
  {
    this.hashId = hashId;
  }

  public String getHashId()
  {
    return this.hashId;
  }

  public void addField(Field field)
  {
    fields.put(field.getName(), field);
  }

  public Field getField(String fieldName)
  {
    return fields.get(fieldName);
  }

  public void setFields(Map<String, Field> fields)
  {
    this.fields = fields;
  }

  public Collection<Field> getFields()
  {
    return fields.values();
  }

  public void setNameOfSuperClass(String superClass)
  {
    this.nameOfSuperClass = superClass;
  }

  public String getNameOfSuperClass()
  {
    return this.nameOfSuperClass;
  }

  public void setPackageName(String packageName)
  {
    this.packageName = packageName;
  }

  public String getPackageName()
  {
    return this.packageName;
  }

  public String getNestingKind()
  {
    return nestingKind;
  }

  public void setNestingKind(String nestingKind)
  {
    this.nestingKind = nestingKind;
  }

  public void setSourceTreeInfo(JavaSourceTreeInfo sourceTreeInfo)
  {
    this.sourceTreeInfo = sourceTreeInfo;
  }

  public JavaSourceTreeInfo getSourceTreeInfo()
  {
    return this.sourceTreeInfo;
  }

  public Collection<Method> getMethods()
  {
    return this.methods;
  }

  public void setMethods(Collection<Method> methods)
  {
    this.methods = methods;
  }

  public void addMethod(Method method)
  {
    methods.add(method);
  }

  public Collection<Method> getConstructors()
  {
    return constructors;
  }

  public void setConstructors(Collection<Method> constructors)
  {
    this.constructors = constructors;
  }

  public void addConstructor(Method method)
  {
    constructors.add(method);
  }

  public Collection<String> getNameOfInterfaces()
  {
    return nameOfInterfaces;
  }

  public void setNameOfInterfaces(Collection<String> interfaces)
  {
    this.nameOfInterfaces = interfaces;
  }

  public void addNameOfInterface(String interfaceDet)
  {
    this.nameOfInterfaces.add(interfaceDet);
  }

  public Collection<String> getClassTypes()
  {
    return classTypes;
  }

  public void setClassTypes(Collection<String> classTypes)
  {
    this.classTypes = classTypes;
  }

  public void addClassType(String classType)
  {
    this.classTypes.add(classType);
  }

  public boolean isInterface()
  {
    return this.isInterface;
  }

  public void setIsInterface(boolean interfaceFlag)
  {
    this.isInterface = interfaceFlag;
  }

  public void setSerializable(boolean isSerializable)
  {
    this.isSerializable = isSerializable;
  }

  public boolean isSerializable()
  {
    return isSerializable;
  }

  public boolean isTopLevelClass()
  {
    return nestingKind.equals(NestingKind.TOP_LEVEL.toString());
  }

  public String toSenseiDocumentJSON()
    throws JSONException
  {
    JSONObject obj = new JSONObject();

    obj.put("hashId", getHashId());
    obj.put("id", getId());
    obj.put("sourceFile", getSourceFile());

    List<String> textParts = new ArrayList<String>();
    Set<String> set;

    set = new HashSet<String>();
    for (Method method : getMethods())
    {
      textParts.add(method.getName());
      set.add(method.getName());
    }
    obj.put("methods", join(",", set));

    set = new HashSet<String>();
    for (Method method : getConstructors())
    {
      textParts.add(method.getName());
      set.add(method.getName());
    }
    obj.put("constructors", join(",", set));

    set = new HashSet<String>();
    for (Field field : getFields())
    {
      textParts.add(field.getName());
      set.add(field.getName());
    }
    obj.put("fields", join(",", set));

    set = new HashSet<String>();
    for (String interfaceName : getNameOfInterfaces())
    {
      textParts.add(interfaceName);
      set.add(interfaceName);
    }
    obj.put("interfaces", join(",", set));

    textParts.add(getName());

    obj.put("packageName", getPackageName());
    obj.put("name", getName());
    obj.put("nameOfSuperClass", getNameOfSuperClass());

    obj.put("text", join(",", textParts));

    return obj.toString();
  }

  public static String join(String delim, Collection<String> collection)
  {
    StringBuilder sb = new StringBuilder();

    for (String s : collection)
    {
      sb.append(s);
      sb.append(delim);
    }

    return sb.toString();
  }

  public String toClassMetaInfoJSON()
    throws JSONException
  {
    JSONObject obj = new JSONObject();

    obj.put("hashId", getHashId());
    obj.put("id", getId());
    obj.put("sourceFile", getSourceFile());

    JSONArray arr = new JSONArray();
    for (Method method : getMethods())
    {
      arr.put(getMethodJsonObject(method));
    }
    obj.put("methods", arr);

    arr = new JSONArray();
    for (Method method : getConstructors())
    {
      arr.put(getMethodJsonObject(method));
    }
    obj.put("constructors", arr);

    arr = new JSONArray();
    for (Field field : getFields())
    {
      arr.put(getFieldJsonObject(field));
    }
    obj.put("fields", arr);

    arr = new JSONArray();
    for (String interfaceName : getNameOfInterfaces())
    {
      arr.put(interfaceName);
    }
    obj.put("interfaces", arr);

    obj.put("classLoc", getLocationObject(getLocationInfo()));

    obj.put("isInterface", Boolean.toString(isInterface()));
    obj.put("isTopLevelClass", Boolean.toString(isInterface()));
    obj.put("isSerializable", Boolean.toString(isInterface()));
    obj.put("packageName", getPackageName());
    obj.put("name", getName());
    obj.put("nestingKind", getNestingKind());
    obj.put("nameOfSuperClass", getNameOfSuperClass());

    return obj.toString();
  }

  private JSONObject getMethodJsonObject(Method method)
    throws JSONException
  {
    JSONObject obj = new JSONObject();
    obj.put("name", method.getName());
    obj.put("loc", getLocationObject(method.getLocationInfo()));

    return obj;
  }

  private JSONObject getLocationObject(Location location)
    throws JSONException
  {
    JSONObject loc = new JSONObject();
    JSONObject start = new JSONObject();
    start.put("line", location.getStartLineNumber());
    start.put("ch", location.getStartRelativeOffset());
    JSONObject stop = new JSONObject();
    stop.put("line", location.getStartLineNumber());
    stop.put("ch", location.getEndRelativeOffset());
    loc.put("start", start);
    loc.put("stop", stop);

    return loc;
  }

  private JSONObject getFieldJsonObject(Field field)
    throws JSONException
  {
    JSONObject obj = new JSONObject();

    obj.put("name", field.getName());
    obj.put("loc", getLocationObject(field.getLocationInfo()));

    return obj;
  }
}
