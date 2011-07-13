package android.java.code;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class Value extends Statement
{
    private String value;

    public Value()
    {
        value = "";
    }

    public Value( String value )
    {
        this.value = value;
    }

    public String toString()
    {
        return value;
    }

    protected SortedMap<String, String> getDependencies()
    {
        return new TreeMap<String, String>();
    }
}
