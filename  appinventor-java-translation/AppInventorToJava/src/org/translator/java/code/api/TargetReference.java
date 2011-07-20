package org.translator.java.code.api;

import java.util.LinkedList;
import org.translator.java.code.Value;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class TargetReference
{
    private ActionEntry target;
    
    public TargetReference( Node target )
    {
        NodeList children = target.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
        {
            this.target = ActionEntryFactory.create( children.item( i ));
            if( this.target != null )
                break;
        }
    }

    public Value getTarget( APIMapping mapping, Value target, LinkedList<Value> params )
    {
        if( this.target == null )
            return target;
        else
            return this.target.generateCode( mapping, target, params );
    }
}
