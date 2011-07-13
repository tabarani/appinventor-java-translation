package android.blocks;

import android.java.code.CodeSegment;
import android.java.code.Value;

import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class TextLiteralBlock extends LiteralBlock
{
    public TextLiteralBlock( Node block )
    {
        super( block );
    }

    public final CodeSegment toCode()
    {
        return new Value( String.format( "\"%s\"", getLabel() ));
    }
}
