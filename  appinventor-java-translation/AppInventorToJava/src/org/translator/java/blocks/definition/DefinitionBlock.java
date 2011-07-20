package org.translator.java.blocks.definition;

import org.translator.java.blocks.Block;
import org.translator.java.code.CodeSegment;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public abstract class DefinitionBlock extends Block
{
    public abstract CodeSegment declare();
    public abstract CodeSegment define();

    protected DefinitionBlock( Node block )
    {
        super( block );
    }
}
