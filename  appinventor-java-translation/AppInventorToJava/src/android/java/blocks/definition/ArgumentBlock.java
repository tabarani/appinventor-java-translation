package android.java.blocks.definition;

import android.java.blocks.Block;
import android.java.blocks.annotation.BlockAnnotation;
import android.java.blocks.annotation.StringRelationship;
import android.java.code.CodeSegment;
import android.java.code.Parameter;
import org.w3c.dom.Node;

@BlockAnnotation(
    genus = "argument",
    genusRelation = StringRelationship.EQUALS )

/**
 *
 * @author Joshua
 */
public class ArgumentBlock extends Block
{
    public ArgumentBlock( Node block )
    {
        super( block );
    }

    public CodeSegment toCode()
    {
        return new Parameter( "Object", getLabel() );
    }
}
