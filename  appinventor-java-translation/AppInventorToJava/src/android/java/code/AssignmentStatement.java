package android.java.code;

import java.util.SortedMap;

/**
 *
 * @author Joshua
 */
public class AssignmentStatement extends Statement
{
    private String identifier;
    private Value value;

    public AssignmentStatement( String identifier, Value value )
    {
        this.identifier = identifier;
        this.value = value;
    }

    public String toString()
    {
        return String.format( "%s = %s;", identifier, value.toString() );
    }

    protected SortedMap<String, String> getDependencies()
    {
        return value.getDependencies();
    }
}
