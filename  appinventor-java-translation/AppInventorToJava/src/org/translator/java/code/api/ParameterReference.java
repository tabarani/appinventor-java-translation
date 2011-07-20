package org.translator.java.code.api;

import java.util.LinkedList;
import org.translator.java.code.CodeSegment;
import org.translator.java.code.Value;
import org.translator.java.code.api.util.APIUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class ParameterReference
{
    private int index = -1, start = -1, end = -1;
    private String value = null;
    private boolean isInterval = false;

    public ParameterReference( Node param )
    {
        NamedNodeMap fields = param.getAttributes();

        if( param.getNodeName().equals( "Parameter" ))
            loadParameter( fields );
        else if( param.getNodeName().equals( "Parameters" ))
            loadParameters( fields );
    }

    protected ParameterReference()
    {
        isInterval = true;
    }

    public CodeSegment generateCode( APIMapping mapping, Value target, LinkedList<Value> params )
    {
        CodeSegment segment = new CodeSegment();

        for( Value v : getParameters( params ))
            segment.add( v );

        return segment;
    }

    public LinkedList<Value> getParameters( LinkedList<Value> params )
    {
        LinkedList<Value> parameters = new LinkedList<Value>();
        
        if( isInterval )
            for( int i = (start >= 0)?start:0; i <= ((end >= 0 && end < params.size())?end:params.size()); i++ )
                parameters.add( params.get( i ));
        else {
            if( index != -1 )
                parameters.add( params.get( index ));
            else
                parameters.add( new Value( value ));
        }

        return parameters;
    }

    private void loadParameter( NamedNodeMap fields )
    {
        String index = APIUtil.getField( fields, "index" );

        if( !index.isEmpty() )
            this.index = Integer.valueOf( index );
        else
            this.value = APIUtil.getField( fields, "value" );
    }

    private void loadParameters( NamedNodeMap fields )
    {
        String min = APIUtil.getField( fields, "start" );
        if( !min.isEmpty() )
            start = Integer.valueOf( min );

        String max = APIUtil.getField( fields, "end" );
        if( !max.isEmpty() )
            end = Integer.valueOf( max );

        isInterval = true;
    }
}
