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

import android.java.blocks.math.MathLiteralBlock;
import android.java.blocks.annotation.BlockAnnotation;
import android.java.blocks.annotation.BlockAnnotationComparator;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.reflections.Reflections;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
class BlocksLoader
{
    private final TreeMap<BlockAnnotation, Class<?>> knownBlocks = new TreeMap<BlockAnnotation, Class<?>>( new BlockAnnotationComparator() );

    protected BlocksLoader()
    {
        String packageName = getClass().getPackage().getName();
        Reflections reflections = new Reflections( packageName );

        Set<Class<?>> blocks = reflections.getTypesAnnotatedWith( BlockAnnotation.class );

        for( Class<?> c : blocks )
            if( Block.class.isAssignableFrom( c ))
            {
                BlockAnnotation annotation = c.getAnnotation( BlockAnnotation.class );
                knownBlocks.put( annotation, c );
            }
    }

    protected ArrayList<Block> loadBlocks( NodeList blocks )
    {
        ArrayList<Block> blocksList = new ArrayList<Block>();
        HashMap<Integer, Block> blocksMap = getBlocksMap( blocks );

        for( Block b : blocksMap.values() )
            if( b.setReferences( blocksMap ))
                blocksList.add( b );

        return blocksList;
    }

    private HashMap<Integer, Block> getBlocksMap( NodeList blocks )
    {
        HashMap<Integer, Block> blocksMap = new HashMap<Integer, Block>();

        for( int i = 0; i < blocks.getLength(); i++ )
        {
            Node target = blocks.item( i );
            if( target.getNodeName().equals( "Block" ))
            {
                Block newBlock = createBlock( target );
                blocksMap.put( newBlock.getID(), newBlock );
            } else if( target.getNodeName().equals( "BlockStub" ) )
                blocksMap.putAll( getBlocksMap( target.getChildNodes() ));
        }

        return blocksMap;
    }

    private Block createBlock( Node blockNode )
    {
        String myPackage = Block.class.getPackage().getName();

        String genus = blockNode.getAttributes().getNamedItem( "genus-name" ).getNodeValue();

        Set<BlockAnnotation> keys = knownBlocks.keySet();
        for( BlockAnnotation key : keys )
        {
            if( genus.matches( key.genusPattern() ))
            {
                try
                {
                    Constructor c = knownBlocks.get( key ).getConstructor( Node.class );
                    return (Block)c.newInstance( blockNode );
                } catch( NoSuchMethodException e ) {
                    System.err.println( knownBlocks.get( key ).toString() + " does not have a constructor accepting a Node parameter." );
                    System.exit( 1 );
                } catch( Exception e ) {
                    System.err.println( e );
                    System.exit( 1 );
                }
            }
        }
        
        return new Block( blockNode );
    }
}
