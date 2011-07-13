package android.java.code;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class CodeSegment
{
    protected final ArrayList<CodeSegment> blocks = new ArrayList<CodeSegment>();

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for( int i = 0; i < blocks.size(); i++ )
        {
            if( i != 0 )
                builder.append( "\n" );
            builder.append( blocks.get( i ).toString() );
        }

        return builder.toString();
    }
    
    public void add( CodeSegment block )
    {
        if( block != null )
            blocks.add( block );
    }

    protected SortedMap<String, String> getDependencies()
    {
        TreeMap<String, String> dependencies = new TreeMap<String, String>();

        for( CodeSegment block : blocks )
            dependencies.putAll( block.getDependencies() );

        return dependencies;
    }
}
