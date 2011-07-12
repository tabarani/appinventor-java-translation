package android.blocks;

import android.java.code.CodeSegment;
import android.java.code.Value;

import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class LiteralBlock extends Block
{
    public LiteralBlock( Node block )
    {
        super( block );
    }

    public CodeSegment toCode()
    {
        return new Value( getLabel() );
    }
}
