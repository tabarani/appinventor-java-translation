package android.java.code;

/**
 *
 * @author Joshua
 */
public enum CodeVisibility
{
    PUBLIC, PRIVATE, PROTECTED, DEFAULT;

    public String toString()
    {
        switch( this )
        {
            case PUBLIC:
                return "public ";
            case PRIVATE:
                return "private ";
            case PROTECTED:
                return "protected ";
            default:
                return "";
        }
    }
}
