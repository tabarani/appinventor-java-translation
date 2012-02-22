import java.util.Collection;
import java.util.Random;

public abstract class AppInventorUtility
{
    private static final Random random = new Random();

    public static String joinRegex( Collection<String> strings )
    {
        StringBuilder builder = new StringBuilder();

        for( String s : strings )
        {
            if( builder.length() > 0 )
                builder.append( "|" );
            builder.append( s );
        }

        return builder.toString();
    }

    public static Object[] join( Object... o )
    {
        return o;
    }

    public Random getRandom()
    {
        return random;
    }
}