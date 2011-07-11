package android.java.code;

/**
 *
 * @author Joshua
 */
public class SourceFile
{
    private String pkg;
    private ClassSegment mainClass;

    public SourceFile()
    {
        pkg = "";
        mainClass = null;
    }

    public SourceFile( String pkg )
    {
        this.pkg = pkg;
        mainClass = null;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        if( !pkg.equals( "" ))
            builder.append( String.format("package %s;\n\n", pkg ));

        builder.append( importString() );

        if( mainClass != null )
            builder.append( mainClass.toString() );

        return builder.toString();
    }

    public void setMainClass( ClassSegment mainClass )
    {
        this.mainClass = mainClass;
    }

    private static String getPackage( String s )
    {
        if( s.contains( "." ))
            return s.substring( 0, s.lastIndexOf( "." ));
        else
            return "";
    }

    private String importString()
    {
        StringBuilder builder = new StringBuilder();

        if( mainClass != null )
            for( String dep : mainClass.getDependencies().keySet() )
            {
                String depPackage = getPackage( dep );
                if( !depPackage.isEmpty() && !depPackage.equals( pkg ) && !depPackage.equals( "java.lang" ))
                    builder.append( String.format( "import %s;\n", dep ));
            }

        if( builder.length() > 0 )
            builder.append( "\n" );

        return builder.toString();
    }
}
