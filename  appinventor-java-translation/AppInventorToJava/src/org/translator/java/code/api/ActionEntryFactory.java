package org.translator.java.code.api;

import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public abstract class ActionEntryFactory
{
    public static ActionEntry create( Node entry )
    {
        if( entry.getNodeName().equals( "Function" ))
            return new FunctionEntry( entry );
        else if( entry.getNodeName().equals( "Call" ))
            return new CallEntry( entry );
        
        return null;
    }
}
