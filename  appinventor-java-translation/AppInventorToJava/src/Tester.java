import java.util.LinkedList;
import org.translator.java.JavaBridgeConstants;
import org.translator.java.code.CodeSegment;
import org.translator.java.code.Value;

/**
 *
 * @author Joshua
 */
public class Tester
{
    public static void main( String[] args )
    {
        LinkedList<Value> params = new LinkedList<Value>();

        params.add( new Value( "\"target\"" ));
        //params.add( new Value( "\"string1\"" ));
        //params.add( new Value( "\"string2\"" ));
        //params.add( new Value( "\"string3\"" ));

        CodeSegment segment = JavaBridgeConstants.API.generateCode( "string-upcase", params );
        System.out.println( segment.toString() );
    }
}
