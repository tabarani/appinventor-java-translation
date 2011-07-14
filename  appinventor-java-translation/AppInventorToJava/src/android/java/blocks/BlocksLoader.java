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

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
abstract class BlocksLoader
{
    protected static ArrayList<Block> loadBlocks( NodeList blocks )
    {
        ArrayList<Block> blocksList = new ArrayList<Block>();
        HashMap<Integer, Block> blocksMap = new HashMap<Integer, Block>();

        for( int i = 0; i < blocks.getLength(); i++ )
            if( blocks.item( i ).getNodeName().equals( "Block" ))
            {
                Block newBlock = createBlock( blocks.item( i ));
                blocksMap.put( newBlock.getID(), newBlock );
            } else if( blocks.item( i ).getNodeName().equals( "BlockStub" )) {
                Block newBlock = new BlockStub( blocks.item( i ));
                blocksMap.put( newBlock.getID(), newBlock );
            }

        for( Block b : blocksMap.values() )
            if( b.setReferences( blocksMap ))
                blocksList.add( b );

        return blocksList;
    }

    private static Block createBlock( Node blockNode )
    {
        String genus = blockNode.getAttributes().getNamedItem( "genus-name" ).getNodeValue();

        if( genus.startsWith( "define" ))
            return new FunctionDefinitionBlock( blockNode );
        else if(genus.contains("-"))
                return new EventDefinitionBlock( blockNode );
        else if( genus.equals( "def" ))
            return new VariableDefinitionBlock( blockNode );
        else if( genus.equals( "if" ))
            return new IfBlock( blockNode );
        else if( genus.equals( "text" ))
            return new TextLiteralBlock( blockNode );
        else if( isLiteral( genus ))
            return new LiteralBlock( blockNode );
        else
            return new Block( blockNode );
    }

    private static boolean isLiteral( String genus )
    {
        return genus.equals( "number" ) || genus.equals( "true" ) || genus.equals( "false" );
    }
}
