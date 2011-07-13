package android.java.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class FunctionCall extends Value
{
    private String target = null;
    private String identifier;
    private final ArrayList<Value> parameters = new ArrayList<Value>();

    public FunctionCall( String identifier )
    {
        this.identifier = new String( identifier );
    }

    public FunctionCall( String target, String identifier )
    {
        this.target = new String( target );
        this.identifier = new String( identifier );
    }

    public FunctionCall( String identifier, Value... params )
    {
        this.identifier = identifier;

        for( Value v : params )
            parameters.add( v );
    }

    public FunctionCall( String target, String identifier, Value... params )
    {
        this.target = new String( target );
        this.identifier = new String( identifier );

        for( Value v : params )
            parameters.add( v );
    }

    public FunctionCall( String identifier, Collection<Value> params )
    {
        this.identifier = identifier;

        for( Value v : params )
            parameters.add( v );
    }

    public String toString()
    {
        String call = identifier.concat( parameterString() );

        if( target == null )
            return call;
        else
            return String.format( "%s.%s", target, call );
    }

    protected SortedMap<String, String> getDependencies()
    {
        TreeMap<String, String> dependencies = new TreeMap<String, String>();

        for( Value v : parameters )
            dependencies.putAll( v.getDependencies() );

        return dependencies;
    }

    private String parameterString()
    {
        StringBuilder builder = new StringBuilder( "(" );

        for( int i = 0; i < parameters.size(); i++ )
        {
            if( i > 0 )
                builder.append( ", " );
            else
                builder.append( " " );

            builder.append( parameters.get( i ).toString() );
        }

        if( parameters.size() > 0 )
            builder.append( " " );

        builder.append( ")" );
        return builder.toString();
    }
}
