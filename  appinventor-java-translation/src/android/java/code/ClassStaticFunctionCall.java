package android.java.code;

import android.java.util.CodeUtil;

/**
 *
 * @author Joshua
 */
public class ClassStaticFunctionCall extends FunctionCall
{
    public ClassStaticFunctionCall( String className, String functionName )
    {
        super( String.format( "%s.%s", CodeUtil.classNameWithoutGeneric( className )));
    }

    /*public ClassStaticFunctionCall( String className, String functionName, Parameter[] parameters )
    {
        super( String.format( "%s.%s", CodeUtil.classNameWithoutGeneric( className )), parameters );
    }*/
}
