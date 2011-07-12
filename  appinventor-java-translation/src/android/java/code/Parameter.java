package android.java.code;

/**
 *
 * @author Joshua
 */
public class Parameter
{
    private String type, identifier;

    public Parameter( String type, String identifier )
    {
        this.type = type;
        this.identifier = identifier;
    }

    protected String getType()
    {
        return type;
    }

    protected String getIdentifier()
    {
        return identifier;
    }
}
