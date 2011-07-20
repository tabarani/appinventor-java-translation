package org.translator.java.code.api;

import java.util.ArrayList;
import java.util.LinkedList;
import org.translator.java.code.Value;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class ParameterReferenceList extends ArrayList<ParameterReference>
{
    public void add( Node param )
    {
        if( param.getNodeName().equals( "Parameters" ) || param.getNodeName().equals( "Parameter" ))
            add( new ParameterReference( param ));
    }

    public LinkedList<Value> getParameters( APIMapping mapping, Value target, LinkedList<Value> params )
    {
        LinkedList<Value> parameters = new LinkedList<Value>();

        for( ParameterReference reference : this )
            parameters.addAll( reference.getParameters( mapping, target, params ));

        return parameters;
    }
}
