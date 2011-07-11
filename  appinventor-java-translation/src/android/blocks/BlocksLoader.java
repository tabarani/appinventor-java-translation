package android.blocks;

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

        if( genus.contains( "-" ))
        {
            if( genus.startsWith( "define-" ))
                return new FunctionDefinitionBlock( blockNode );
            else
                return new EventDefinitionBlock( blockNode );
        }
        else if( genus.equals( "def" ))
            return new VariableDefinitionBlock( blockNode );

        return new Block( blockNode );
    }
}
