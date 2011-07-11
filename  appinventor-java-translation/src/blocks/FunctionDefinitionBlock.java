package android.blocks;

import android.java.code.CodeSegment;
import android.java.code.CodeVisibility;
import android.java.code.FunctionSegment;
import android.java.code.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class FunctionDefinitionBlock extends Block
{
    public FunctionDefinitionBlock( Node block )
    {
        super( block );
    }

    protected final CodeSegment toCode()
    {
        FunctionSegment function = new FunctionSegment( getFunctionName(), CodeVisibility.PRIVATE, getFunctionReturnType(), getParameters() );

        if( afterBlock != null )
            function.add( afterBlock.toCode() );

        return function;
    }

    protected String getFunctionName()
    {
        return label;
    }

    protected String getFunctionReturnType()
    {
        return genusName.substring( genusName.indexOf( "-" )+1 );
    }

    protected Collection<Parameter> getParameters()
    {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        int iArg = 1;

        for( BlockConnector connector : connectors )
            if( connector.getLabel().equals( "arg" ))
            {
                String argName = "arg".concat( String.valueOf( iArg ));
                Block connected = connector.getConnectedBlock();

                if( connected != null )
                    argName = connected.getLabel();

                Parameter p = new Parameter( "Object", argName );
                parameters.add( p );
                iArg++;
            }

        return parameters;
    }
}
