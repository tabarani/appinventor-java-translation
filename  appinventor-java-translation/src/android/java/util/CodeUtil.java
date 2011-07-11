package android.java.util;

/**
 *
 * @author Joshua
 */
public abstract class CodeUtil
{
    public static String indent( String s )
    {
        return new StringBuilder( s.replaceAll( "\n", "\n\t" )).insert( 0, "\t" ).toString();
    }

    public static String className( String s )
    {
        if( s.contains( "." ))
            return s.substring( s.lastIndexOf( "." ) + 1 );
        else
            return s;
    }

    public static String classNameWithoutGeneric( String s )
    {
        return removeGeneric( className( s ));
    }

    public static String removeGeneric( String s )
    {
        if( s.contains( "<" ))
            return s.substring( 0, s.lastIndexOf( "<" ) );
        else
            return s;
    }
}
