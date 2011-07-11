package android.java;

import android.blocks.BlocksPage;
import android.java.code.*;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Joshua
 */
class AppInventorProperties
{
    private final HashMap<String, String> properties = new HashMap<String, String>();
    private final ArrayList<AppInventorProperties> components = new ArrayList<AppInventorProperties>();

    protected AppInventorProperties( JsonReader reader ) throws IOException
    {
        reader.beginObject();

        while( reader.peek() == JsonToken.NAME )
        {
            String name = reader.nextName();
            if( name.equals( "$Components" ) )
            {
                reader.beginArray();

                while( reader.peek() == JsonToken.BEGIN_OBJECT )
                    components.add( new AppInventorProperties( reader ));

                reader.endArray();
            } else
                properties.put( name, reader.nextString() );
        }

        reader.endObject();
    }

    protected CodeSegment generateCode( ArrayList<BlocksPage> blocks )
    {
        String type = getType();
        CodeSegment block = null;

        if( type.equals( "Form" ))
            block = generateFormBlock( blocks );

        return block;
    }

    protected Statement declaration()
    {
        String type = getType();

        if( !type.equals( "Form" ))
            return new DeclarationStatement( AndroidJavaConstants.COMPONENT_PREFIX.concat( type ), getName(), CodeVisibility.PRIVATE );
        else
            return null;
    }

    protected CodeSegment instantiation( String parent )
    {
        String type = getType();

        if( !type.equals( "Form" ))
        {
            CodeSegment inst = new CodeSegment();

            Value[] params = { new Value( parent ) };
            inst.add( new AssignmentStatement( getName(), new ConstructorCall( AndroidJavaConstants.COMPONENT_PREFIX.concat( type ), params )) );

            Set<String> keys = (Set<String>)(properties.keySet());
            for( String key : keys )
                if( !key.startsWith( "$" ) && !key.equals( "Uuid" ))
                {
                    String functionName = String.format( "%s.%s", getName(), key );

                    Class paramType = getParamType( AndroidJavaConstants.COMPONENT_PREFIX.concat( getType() ), key );
                    Value[] value = { new Value( getValueString( paramType, properties.get( key )))};

                    inst.add( new ValueStatement( new FunctionCall( functionName, value )));
                }

            inst.add( new Value( "" )); //To add a new line

            for( AppInventorProperties c : components )
                inst.add( c.instantiation( getName() ));

            return inst;
        }
        else
            return null;
    }
    
    private CodeSegment generateFormBlock( ArrayList<BlocksPage> pages )
    {
        ClassSegment block;
        boolean hasEvents = hasEvents( pages );
        
        if( hasEvents )
            block = new ClassSegment( getName(), CodeVisibility.PUBLIC, AndroidJavaConstants.FORM, AndroidJavaConstants.EVENT_HANDLING_INTERFACES );
        else
            block = new ClassSegment( getName(), CodeVisibility.PUBLIC, AndroidJavaConstants.FORM );

        //Start declarations
        for( AppInventorProperties c : components )
            block.add( c.declaration() );

        block.add( new Value() );

        for( BlocksPage page : pages )
            block.add( page.declaration() );
        //End declarations

        //Start $define
        FunctionSegment defineFunction = new FunctionSegment( "$define" );

        for( BlocksPage page : pages )
            defineFunction.add( page.definition() );

        defineFunction.add( new Value() );
        
        for( AppInventorProperties c : components )
            defineFunction.add( c.instantiation( "this" ));

        if( hasEvents )
            defineFunction.add( registerEvents( pages ));

        block.add( defineFunction );
        //End $define

        //Start body
        for( BlocksPage page : pages )
            block.add( page.toCode() );
        //End body

        return block;
    }

    private CodeSegment registerEvents( ArrayList<BlocksPage> pages )
    {
        HashSet<String> eventSet = new HashSet<String>();

        for( BlocksPage page : pages )
            eventSet.addAll( page.getEvents() );

        return null;
    }

    private boolean hasEvents( ArrayList<BlocksPage> pages )
    {
        for( BlocksPage page : pages )
            if( page.hasEvents() )
                return true;

        return false;
    }

    private String getType()
    {
        return properties.get( "$Type" );
    }

    private String getName()
    {
        return properties.get( "$Name" );
    }

    private Class<?> getParamType( String className, String functionName )
    {
        try
        {
            return Class.forName( className ).getMethod( functionName, new Class[0] ).getReturnType();
        } catch( Exception e ) {
            System.err.println( e );
            return null;
        }
    }

    private String getValueString( Class paramType, String value )
    {
        if( paramType.equals( String.class ))
            return String.format( "\"%s\"", value );
        else
            return value.replace( "&H", "0x" );
    }
}
