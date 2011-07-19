package android.java;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
class APIMapping extends HashMap<String, String>
{
    protected APIMapping()
    {
        load( JavaBridgeConstants.API_MAPPING_FILE );
    }

    private void load( String fileName )
    {
        try
        {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( fileName );

            NodeList nodes = doc.getDocumentElement().getChildNodes();

            for( int i = 0; i < nodes.getLength(); i++ )
                addMapping( nodes.item( i ));
        } catch( Exception e ) {
            System.err.println( "Error reading API mapping: ".concat( e.toString() ));
        }
    }

    private void addMapping( Node n )
    {
        if( n.getNodeName().equals( "Entry" ))
        {
            String genus = n.getAttributes().getNamedItem( "genus" ).getNodeValue();
            String f = n.getAttributes().getNamedItem( "function" ).getNodeValue();
            put( genus, f );
        }
    }
}
