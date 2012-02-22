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

package org.translator.java.blocks;

import org.translator.java.blocks.definition.DefinitionBlock;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
class BlocksFactory
{
    private final HashMap<String, Class<? extends Block>> knownBlocks = new HashMap<String, Class<? extends Block>>();

    protected BlocksFactory()
    {
        String packageName = getClass().getPackage().getName();
        Reflections reflections = new Reflections( packageName );

        Set<Class<? extends Block>> blocks = reflections.getSubTypesOf( Block.class );

        for( Class<? extends Block> c : blocks )
        {
            try
            {
                String genusPattern = (String)c.getMethod( "getGenusPattern" ).invoke( null );
                
                if( genusPattern != null )
                    knownBlocks.put( genusPattern, c );
            } catch( Exception e )
            {
                System.err.println( e );
                System.exit( 1 );
            }
        }
    }

    protected ArrayList<DefinitionBlock> loadBlocks( NodeList blocks )
    {
        ArrayList<DefinitionBlock> blocksList = new ArrayList<DefinitionBlock>();
        HashMap<Integer, Block> blocksMap = getBlocksMap( blocks );

        for( Block b : blocksMap.values() )
        {
            b.setReferences( blocksMap );
            if( DefinitionBlock.class.isAssignableFrom( b.getClass() ))
                blocksList.add( (DefinitionBlock)b );
        }

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
        String genus = blockNode.getAttributes().getNamedItem( "genus-name" ).getNodeValue();

        Set<String> keys = knownBlocks.keySet();
        for( String key : keys )
        {
            if( genus.matches( key ))
            {
                if( isDefinition( blockNode ) ^ !DefinitionBlock.class.isAssignableFrom( knownBlocks.get( key )) )
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

    private static boolean isDefinition( Node blockNode )
    {
        NodeList children = blockNode.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
        {
            String name = children.item( i ).getNodeName();

            if( name.equals( "BeforeBlockId" ) || name.equals( "Plug" ))
                return false;
        }

        return true;
    }
}
