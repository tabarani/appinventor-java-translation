/*
   appinventor-java-translation

   Originally authored by Joshua Swank at the University of Alabama
   Work supported in part by NSF award #0702764 and a Google 2011 CS4HS award

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package android.java.blocks;

import android.java.code.CodeSegment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class Block
{
    private int id, beforeBlockId = -1, afterBlockId = -1;

    protected final ArrayList<BlockConnector> connectors = new ArrayList<BlockConnector>();
    protected String genusName, label;
    protected Block beforeBlock = null, afterBlock = null;
    private boolean plugged = false;

    public Block( Node block )
    {
        load( block );
    }

    protected Block()
    {
    }

    protected final String getGenus()
    {
        return genusName;
    }

    public CodeSegment declare()
    {
        return null;
    }

    public CodeSegment define()
    {
        return null;
    }

    public CodeSegment toCode()
    {
        return null;
    }

    public final String getLabel()
    {
        return new String( label );
    }

    public final int getID()
    {
        return id;
    }

    public HashSet<String> getEvents()
    {
        return new HashSet<String>();
    }

    public final boolean isFirstGeneration()
    {
        return ( beforeBlock == null );
    }

    public final boolean isPlugged()
    {
        return plugged;
    }

    //Returns true if the block has no parents
    protected boolean setReferences( HashMap<Integer, Block> blocksMap )
    {
        for( BlockConnector connector : connectors )
            connector.setReferences( blocksMap );

        if( afterBlockId != -1 )
            afterBlock = blocksMap.get( afterBlockId );

        if( beforeBlockId != -1 )
            beforeBlock = blocksMap.get( beforeBlockId );
        else if( !plugged )
            return true;

        return false;
    }

    protected final CodeSegment getNextCode()
    {
        if( afterBlock != null )
            return afterBlock.toCode();
        else
            return null;
    }

    protected void load( Node block )
    {
        NamedNodeMap attributes = block.getAttributes();

        id = Integer.valueOf( attributes.getNamedItem( "id" ).getNodeValue() );
        genusName = attributes.getNamedItem( "genus-name" ).getNodeValue();

        NodeList children = block.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
            handleChild( children.item( i ));
    }

    private void handleChild( Node child )
    {
        String name = child.getNodeName();

        if( name.equals( "Label" ))
            label = child.getTextContent();
        else if( name.equals( "BeforeBlockId" ))
            beforeBlockId = Integer.valueOf( child.getTextContent() );
        else if( name.equals( "AfterBlockId" ))
            afterBlockId = Integer.valueOf( child.getTextContent() );
        else if( name.equals( "Sockets" ))
            loadSockets( child );
        else if( name.equals( "Plug" ))
        {
            connectors.add( new Plug( child ));
            plugged = true;
        }
    }

    private void loadSockets( Node sockets )
    {
        NodeList children = sockets.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
            if( children.item( i ).getNodeName().equals( "BlockConnector" ))
                connectors.add( new BlockConnector( children.item( i )));
    }
}
