package android.java.code;

import android.java.util.CodeUtil;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class ConstructorCall extends Value
{
    private FunctionCall functionCall;
    private String identifier;

    public ConstructorCall( String identifier )
    {
        this.identifier = identifier;
        functionCall = new FunctionCall( CodeUtil.className( identifier ));
    }

    public ConstructorCall( String identifier, Value[] parameters )
    {
        this.identifier = identifier;
        functionCall = new FunctionCall( CodeUtil.className( identifier ), parameters );
    }

    public ConstructorCall( String identifier, Collection<Value> parameters )
    {
        this.identifier = identifier;
        functionCall = new FunctionCall( CodeUtil.className( identifier ), parameters );
    }

    public String toString()
    {
        return "new ".concat( functionCall.toString() );
    }

    protected SortedMap<String, String> getDependencies()
    {
        TreeMap<String, String> dependencies = new TreeMap<String, String>();

        dependencies.put( CodeUtil.removeGeneric( identifier ), CodeUtil.removeGeneric( CodeUtil.className( identifier )));
        dependencies.putAll( functionCall.getDependencies() );

        return dependencies;
    }
}
