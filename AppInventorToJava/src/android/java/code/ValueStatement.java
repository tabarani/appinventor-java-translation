package android.java.code;

import java.util.SortedMap;

/**
 *
 * @author Joshua
 */
public class ValueStatement extends Statement
{
    private Value value;

    public ValueStatement( Value value )
    {
        this.value = value;
    }

    public String toString()
    {
        return value.toString().concat( ";" );
    }

    protected SortedMap<String, String> getDependencies()
    {
        return value.getDependencies();
    }
}
