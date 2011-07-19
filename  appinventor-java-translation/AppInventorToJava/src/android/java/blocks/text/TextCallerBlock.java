package android.java.blocks.text;

import android.java.JavaBridgeConstants;
import android.java.blocks.BlockConnector;
import android.java.blocks.CallerBlock;
import android.java.blocks.Plug;
import android.java.code.CodeSegment;
import android.java.code.FunctionCall;
import android.java.code.Value;
import java.util.LinkedList;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class TextCallerBlock extends CallerBlock
{
    public TextCallerBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "string-.*|caller.*";
    }

    public CodeSegment generateCode()
    {
        return createFunction();
    }

    private FunctionCall createFunction()
    {
        LinkedList<Value> params = new LinkedList<Value>();

        for( BlockConnector c : connectors )
            if( !(c instanceof Plug ))
                if( c.hasConnectedBlock())
                    params.add( (Value)(c.getConnectedBlock().generateCode()) );
                else
                    params.add( new Value( "\"\"" ));

        Value target = params.removeFirst();
        String function = (JavaBridgeConstants.API.containsKey( getGenus() ))?JavaBridgeConstants.API.get( getGenus() ):getLabel();
        return new FunctionCall( target.toString(), function, params );
    }
}
