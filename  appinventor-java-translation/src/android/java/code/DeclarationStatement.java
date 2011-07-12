package android.java.code;

import android.java.util.CodeUtil;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class DeclarationStatement extends Statement
{
    private String type, identifier;
    private CodeVisibility visibility = CodeVisibility.DEFAULT;
    private Value value = null;

    public DeclarationStatement( String type, String identifier )
    {
        this.type = type;
        this.identifier = identifier;
    }

    public DeclarationStatement( String type, String identifier, Value value )
    {
        this.type = type;
        this.identifier = identifier;
        this.value = value;
    }

    public DeclarationStatement( String type, String identifier, CodeVisibility visibility )
    {
        this.type = type;
        this.identifier = identifier;
        this.visibility = visibility;
    }

    public DeclarationStatement( String type, String identifier, CodeVisibility visibility, Value value )
    {
        this.type = type;
        this.identifier = identifier;
        this.visibility = visibility;
        this.value = value;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append( String.format( "%s%s %s", visibility.toString(), CodeUtil.className( type ), identifier ));

        if( value != null )
            builder.append( String.format( " = %s", value.toString() ));

        builder.append( ";" );
        return builder.toString();
    }

    protected SortedMap<String, String> getDependencies()
    {
        TreeMap<String, String> dependencies = new TreeMap<String, String>();

        dependencies.put( CodeUtil.removeGeneric( type ), CodeUtil.removeGeneric( CodeUtil.className( type )));

        if( value != null )
            dependencies.putAll( value.getDependencies() );

        return dependencies;
    }
}
