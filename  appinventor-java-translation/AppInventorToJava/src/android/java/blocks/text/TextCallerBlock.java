package android.java.blocks.text;

import android.java.blocks.CallerBlock;
import android.java.code.CodeSegment;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class TextCallerBlock extends CallerBlock
{
    public TextCallerBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "string-.*|caller.*";
    }

    public CodeSegment generateCode()
    {
        return createFunction();
    }
}
