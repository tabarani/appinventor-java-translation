package android.blocks;

import android.java.code.Value;
import java.util.HashMap;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class BlockConnector
{
    private String connectorKind, connectorType, initType, label;
    private int conBlockId = -1;
    private Block connectedBlock = null;

    public BlockConnector( Node blockConnector )
    {
        load( blockConnector );
    }

    protected BlockConnector()
    {
        
    }

    protected final String getLabel()
    {
        return new String( label );
    }

    protected final Value getValue()
    {
        return null;
    }

    protected String getDataType()
    {
        String genus = connectedBlock.getGenus();

        if( genus.equals( "make-list" ))
            return "java.util.List<Object>";
        else if( genus.equals( "number" ))
            return "java.lang.Float";
        else
            return "<!unknown type>";
    }

    protected Block getConnectedBlock()
    {
        return connectedBlock;
    }

    protected void load( Node blockConnector )
    {
        NamedNodeMap attributes = blockConnector.getAttributes();

        connectorKind = attributes.getNamedItem( "connector-kind" ).getNodeValue();
        connectorType = attributes.getNamedItem( "connector-type" ).getNodeValue();
        initType = attributes.getNamedItem( "init-type" ).getNodeValue();
        label = attributes.getNamedItem( "label" ).getNodeValue();

        Node blockId = attributes.getNamedItem( "con-block-id" );
        if( blockId != null )
            conBlockId = Integer.valueOf( blockId.getNodeValue() );
    }

    protected void setReferences( HashMap<Integer, Block> blocksMap )
    {
        if( conBlockId != -1 )
            connectedBlock = blocksMap.get( conBlockId );
    }
}
