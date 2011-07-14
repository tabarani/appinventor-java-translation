package android.java.blocks;

import android.java.blocks.annotation.BlockAnnotation;
import android.java.blocks.annotation.StringRelationship;
import android.java.code.CodeSegment;
import android.java.code.Value;
import org.w3c.dom.Node;

@BlockAnnotation(
    genus = "Global",
    genusRelation = StringRelationship.ENDS_WITH )

/**
 *
 * @author Joshua
 */
public class GlobalStub extends Block
{
    public GlobalStub( Node node )
    {
        super( node );
    }

    protected CodeSegment toCode()
    {
        return new Value( getLabel() );
    }
}
