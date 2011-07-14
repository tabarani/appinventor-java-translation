package android.java.blocks.definition;

import android.java.blocks.Block;
import android.java.blocks.BlockConnector;
import android.java.blocks.annotation.BlockAnnotation;
import android.java.blocks.annotation.StringRelationship;
import android.java.code.CodeSegment;
import android.java.code.Value;
import android.java.code.ValueStatement;
import org.w3c.dom.Node;

@BlockAnnotation(
    genus = "glue",
    genusRelation = StringRelationship.EQUALS )

/**
 *
 * @author Joshua
 */
public class GlueBlock extends Block
{
    public GlueBlock( Node block )
    {
        super( block );
    }

    public CodeSegment toCode()
    {
        BlockConnector toCall = connectors.get( 0 );

        if( toCall.hasConnectedBlock() )
            return new ValueStatement( (Value)toCall.getConnectedBlock().toCode() );
        else
            return null;
    }
}
