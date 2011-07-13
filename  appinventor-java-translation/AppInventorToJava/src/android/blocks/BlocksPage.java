package android.blocks;

import android.java.code.CodeSegment;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class BlocksPage
{
    private final ArrayList<Block> blocks = new ArrayList<Block>();

    public BlocksPage( Node page )
    {
        NodeList children = page.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
            if( children.item( i ).getNodeName().equals( "PageBlocks" ))
            {
                blocks.addAll( BlocksLoader.loadBlocks( children.item( i ).getChildNodes() ));
                break;
            }
    }

    public CodeSegment declaration()
    {
        CodeSegment code = new CodeSegment();

        for( Block block : blocks )
            code.add( block.declare() );

        return code;
    }

    public CodeSegment definition()
    {
        CodeSegment code = new CodeSegment();

        for( Block block : blocks )
            code.add( block.define() );

        return code;
    }

    public CodeSegment toCode()
    {
        CodeSegment codeBlock = new CodeSegment();

        for( Block b : blocks )
            codeBlock.add( b.toCode() );

        return codeBlock;
    }

    public boolean hasEvents()
    {
        for( Block b : blocks )
            if( b instanceof EventDefinitionBlock )
                return true;

        return false;
    }

    public SortedSet<String> getEvents()
    {
        SortedSet<String> eventSet = new TreeSet<String>();

        for( Block b : blocks )
            eventSet.addAll( b.getEvents() );

        return eventSet;
    }
}
