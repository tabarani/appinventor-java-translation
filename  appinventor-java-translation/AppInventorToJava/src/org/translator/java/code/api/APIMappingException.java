package org.translator.java.code.api;

/**
 *
 * @author Joshua
 */
public class APIMappingException extends RuntimeException
{
    public APIMappingException( String message )
    {
        super( "Exception while loading API mapping:".concat( message ));
    }
}
