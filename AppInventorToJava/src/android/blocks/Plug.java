package android.blocks;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class Plug extends BlockConnector
{
    public Plug( Node plug )
    {
        NodeList children = plug.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
            if( children.item( i ).getNodeName().equals( "BlockConnector" ))
            {
                load( children.item( i ));
                break;
            }
    }
}
