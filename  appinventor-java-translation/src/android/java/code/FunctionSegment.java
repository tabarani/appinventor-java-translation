package android.java.code;

import android.java.util.CodeUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class FunctionSegment extends CodeSegment
{
    private String identifier, returnType = "void";
    private CodeVisibility visibility = CodeVisibility.DEFAULT;
    private final ArrayList<Parameter> parameters = new ArrayList<Parameter>();

    private final TreeMap<String, String> dependencies = new TreeMap<String, String>();

    public FunctionSegment( String identifier )
    {
        this.identifier = identifier;
    }

    public FunctionSegment( String identifier, Parameter... params )
    {
        this.identifier = identifier;
        setParameters( params );
    }

    public FunctionSegment( String identifier, String returnType )
    {
        this.identifier = identifier;
        setReturnType( returnType );
    }

    public FunctionSegment( String identifier, CodeVisibility visibility, String returnType )
    {
        this.identifier = identifier;
        setReturnType( returnType );
        this.visibility = visibility;
    }

    public FunctionSegment( String identifier, CodeVisibility visibility, String returnType, Parameter... params )
    {
        this.identifier = identifier;
        setReturnType( returnType );
        this.visibility = visibility;
        setParameters( params );
    }

    public SortedMap<String, String> getDependencies()
    {
        TreeMap<String, String> newDependencies = (TreeMap<String, String>)dependencies.clone();

        for( CodeSegment block : blocks )
            newDependencies.putAll( block.getDependencies() );

        return newDependencies;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append( String.format("%s%s %s%s\n{", visibility.toString(), CodeUtil.className( returnType ), identifier, parameterString() ));

        for( int i = 0; i < blocks.size(); i++ )
        {
            builder.append( "\n" );
            builder.append( CodeUtil.indent( blocks.get( i ).toString() ));
        }

        builder.append( "\n}" );
        return builder.toString();
    }

    private String parameterString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append( "(" );

        for( int i = 0; i < parameters.size(); i++ )
        {
            if( i != 0 )
                builder.append( ", " );
            else
                builder.append( " " );

            builder.append( String.format( "%s %s", CodeUtil.className( parameters.get( i ).getType() ), parameters.get( i ).getIdentifier() ));
        }

        if( parameters.size() > 0 )
            builder.append( " " );
        builder.append( ")" );

        return builder.toString();
    }

    private void setReturnType( String s )
    {
        returnType = s;
        dependencies.put( CodeUtil.removeGeneric( s ), CodeUtil.removeGeneric( CodeUtil.className( s )));
    }

    private void setParameters( Parameter[] params )
    {
        for( Parameter p : params )
        {
            dependencies.put( CodeUtil.removeGeneric( p.getType() ), CodeUtil.removeGeneric( CodeUtil.className( p.getType() )));
            parameters.add( p );
        }
    }
}
