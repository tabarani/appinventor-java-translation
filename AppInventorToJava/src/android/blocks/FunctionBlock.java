package android.blocks;

import android.java.code.CodeSegment;
import android.java.code.CodeVisibility;
import android.java.code.FunctionCall;
import android.java.code.FunctionSegment;
import android.java.code.Parameter;
import android.java.code.Value;
import android.java.code.ValueStatement;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class FunctionBlock extends Block
{
    public FunctionBlock( Node block )
    {
        super( block );
    }

    protected final CodeSegment toCode()
    {
        CodeSegment code = new CodeSegment();

        if( isFirstGeneration() )
            code.add( createDeclaration() );
        else
            code.add( createCall() );

        code.add( getNextCode() );

        return code;
    }

    protected String getFunctionName()
    {
        return label;
    }

    protected String getFunctionReturnType()
    {
        return genusName.substring( genusName.indexOf( "-" )+1 );
    }

    protected Collection<Parameter> getDeclarationParameters()
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

    private Collection<Value> getCallParameters()
    {
        ArrayList<Value> parameters = new ArrayList<Value>();

        for( BlockConnector connector : connectors )
        {
            Block connected = connector.getConnectedBlock();
            if( connected instanceof LiteralBlock )
                parameters.add( (Value)connected.toCode() );
            else
                parameters.add( new Value( connected.getLabel() ));
        }

        return parameters;
    }

    private final CodeSegment createCall()
    {
        FunctionCall call = new FunctionCall( getFunctionName().replaceAll( "_", "." ), getCallParameters() );

        if( !isPlugged() )
            return new ValueStatement( call );
        else
            return call;
    }

    private final CodeSegment createDeclaration()
    {
        FunctionSegment function = new FunctionSegment( getFunctionName(), CodeVisibility.PRIVATE, getFunctionReturnType(), getDeclarationParameters().toArray( new Parameter[0] ));

        for( BlockConnector connector : connectors )
            if( connector.getLabel().equals( "do" ) && connector.getConnectedBlock() != null )
                function.add( connector.getConnectedBlock().toCode() );

        return function;
    }
}
