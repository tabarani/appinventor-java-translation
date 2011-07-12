package android.java.code;

import android.java.util.CodeUtil;

import java.util.SortedMap;

/**
 *
 * @author Joshua
 */
public class ClassStaticFunctionCall extends FunctionCall
{
    private String className;

    public ClassStaticFunctionCall( String className, String functionName )
    {
        super( String.format( "%s.%s", CodeUtil.classNameWithoutGeneric( className ), functionName ));
        this.className = new String( className );
    }

    public ClassStaticFunctionCall( String className, String functionName, Value... parameters )
    {
        super( String.format( "%s.%s", CodeUtil.classNameWithoutGeneric( className ), functionName ), parameters );
        this.className = new String( className );
    }

    protected SortedMap<String, String> getDependencies()
    {
        SortedMap<String, String> dependencies = super.getDependencies();

        dependencies.put( CodeUtil.removeGeneric( className ), CodeUtil.classNameWithoutGeneric( className ));

        return dependencies;
    }
}
