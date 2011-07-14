/*
   appinventor-java-translation

   Originally authored by Joshua Swank at the University of Alabama
   Work supported in part by NSF award #0702764 and a Google 2011 CS4HS award

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package android.java;

import android.java.blocks.BlocksPage;
import android.java.code.*;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

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

    protected CodeSegment generateCode( ArrayList<BlocksPage> blocks, String projectName )
    {
        String type = getType();
        CodeSegment block = null;

        if( type.equals( "Form" ))
            block = generateFormBlock( blocks, projectName );

        return block;
    }

    protected Statement declaration()
    {
        String type = getType();

        if( !type.equals( "Form" ))
            return new DeclarationStatement( JavaBridgeConstants.COMPONENT_PREFIX.concat( type ), getName(), CodeVisibility.PRIVATE );
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
            inst.add( new AssignmentStatement( getName(), new ConstructorCall( JavaBridgeConstants.COMPONENT_PREFIX.concat( type ), params )) );

            Set<String> keys = (Set<String>)(properties.keySet());
            for( String key : keys )
                if( !key.startsWith( "$" ) && !key.equals( "Uuid" ))
                {
                    String functionName = String.format( "%s.%s", getName(), key );

                    Class paramType = getParamType( JavaBridgeConstants.COMPONENT_PREFIX.concat( getType() ), key );
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
    
    private CodeSegment generateFormBlock( ArrayList<BlocksPage> pages, String projectName )
    {
        ClassSegment block;
        boolean hasEvents = hasEvents( pages );
        
        if( hasEvents )
            block = new ClassSegment( getName(), CodeVisibility.PUBLIC, JavaBridgeConstants.FORM, JavaBridgeConstants.EVENT_HANDLING_INTERFACES );
        else
            block = new ClassSegment( getName(), CodeVisibility.PUBLIC, JavaBridgeConstants.FORM );

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
            defineFunction.add( registerEvents( pages, projectName ));

        block.add( defineFunction );
        //End $define

        //Start dispatchEvent
        FunctionSegment dispatchFunction = new FunctionSegment( "dispatchEvent", CodeVisibility.PUBLIC, "void", new Parameter( "Object", "component" ), new Parameter( "java.lang.String", "id" ), new Parameter( "Object[]", "params" ));

        for( BlocksPage page : pages )
            dispatchFunction.add( page.createDispatchSegment( getName() ));

        block.add( dispatchFunction );
        //End dispatchEvent

        //Start body
        for( BlocksPage page : pages )
            block.add( page.toCode() );
        //End body

        return block;
    }

    private CodeSegment registerEvents( ArrayList<BlocksPage> pages, String projectName )
    {
        TreeSet<String> events = new TreeSet<String>();
        CodeSegment segment = new CodeSegment();

        for( BlocksPage page : pages )
            events.addAll( page.getEvents() );

        for( String event : events )
            segment.add( new ValueStatement( new ClassStaticFunctionCall( JavaBridgeConstants.EVENT_DISPATCHER, "registerEventForDelegation", new Value( "this" ), new Value( String.format( "\"%s\"", projectName )), new Value( String.format( "\"%s\"", event )))));

        return segment;
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
