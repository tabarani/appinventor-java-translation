package android.blocks;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class BlockStub extends Block
{
    String stubParentName = null, stubParentGenus = null;
    
    public BlockStub( Node block )
    {
        NodeList children = block.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
        {
            String name = children.item( i ).getNodeName();

            if( name.equals( "StubParentName" ))
                stubParentName = children.item( i ).getTextContent();
            else if( name.equals( "StubParentGenus" ))
                stubParentGenus = children.item( i ).getTextContent();
            else if(name.equals("Block"))
                load( children.item( i ));
        }
    }
}
