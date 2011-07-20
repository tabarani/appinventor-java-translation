package org.translator.java.code.api;

import org.translator.java.code.api.util.APIUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class TypeDefault
{
    private String name, defaultValue = "null";

    public TypeDefault( Node type )
    {
        NamedNodeMap fields = type.getAttributes();

        this.name = APIUtil.getField( fields, "name" );

        String defaultValue = APIUtil.getField( fields, "default" );
        if( !defaultValue.isEmpty() )
            this.defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return new String( defaultValue );
    }

    public String getName()
    {
        return new String( name );
    }
}
