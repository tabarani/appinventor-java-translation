import java.util.ArrayList;

/**
 *
 * @author Joshua
 */
public class Tester
{
    public static void main( String[] args )
    {
        ArrayList<String> test = new ArrayList<String>();

        test.add( "a" );
        test.add( "(b|C)" );
        test.add( "d*" );

        System.out.println( AppInventorUtility.joinRegex( test ));
    }
}