package android.blocks;

import java.util.HashSet;

import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class EventDefinitionBlock extends FunctionBlock
{
    public EventDefinitionBlock( Node block )
    {
        super( block );
    }

    protected String getFunctionName()
    {
        return label.replace( ".", "_" );
    }

    protected String getFunctionReturnType()
    {
        return "void";
    }

    protected HashSet<String> getEvents()
    {
        HashSet<String> eventSet = new HashSet<String>();

        eventSet.add( getEventName() );

        return eventSet;
    }

    private String getEventName()
    {
        return label.substring( label.lastIndexOf( "." ) + 1 );
    }
}
