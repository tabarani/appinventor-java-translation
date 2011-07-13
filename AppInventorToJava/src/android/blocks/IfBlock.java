package android.blocks;

import android.java.code.CodeSegment;

import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class IfBlock extends Block
{
    public IfBlock( Node block )
    {
        super( block );
    }

    protected final CodeSegment toCode()
    {
        return null;
    }
}
