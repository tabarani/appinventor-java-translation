package android.blocks;

import android.java.code.AssignmentStatement;
import android.java.code.CodeSegment;
import android.java.code.CodeVisibility;
import android.java.code.ConstructorCall;
import android.java.code.DeclarationStatement;
import android.java.util.CodeUtil;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class VariableDefinitionBlock extends Block
{
    public VariableDefinitionBlock( Node block )
    {
        super( block );
    }

    protected CodeSegment declare()
    {
        return new DeclarationStatement( connectors.get( 0 ).getDataType(), label, CodeVisibility.PRIVATE );
    }

    protected CodeSegment define()
    {
        String dataType = connectors.get( 0 ).getDataType();

        try
        {
            if( !Class.forName( CodeUtil.removeGeneric( dataType )).isPrimitive() )
                return new AssignmentStatement( label, new ConstructorCall( dataType ));
        } catch( ClassNotFoundException e ) {
            System.err.println( e );
        }

        return null;
    }
}
