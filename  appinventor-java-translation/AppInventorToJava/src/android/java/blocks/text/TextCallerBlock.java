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

package android.java.blocks.text;

import android.java.JavaBridgeConstants;
import android.java.blocks.BlockConnector;
import android.java.blocks.CallerBlock;
import android.java.blocks.Plug;
import android.java.code.ClassStaticFunctionCall;
import android.java.code.CodeSegment;
import android.java.code.FunctionCall;
import android.java.code.Value;
import java.util.LinkedList;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class TextCallerBlock extends CallerBlock
{
    public TextCallerBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "string-.*|caller.*";
    }

    public CodeSegment generateCode()
    {
        return createFunction();
    }

    private FunctionCall createFunction()
    {
        LinkedList<Value> params = new LinkedList<Value>();

        for( BlockConnector c : connectors )
            if( !(c instanceof Plug ))
                if( c.hasConnectedBlock())
                    params.add( (Value)(c.getConnectedBlock().generateCode()) );
                else
                    params.add( new Value( "\"\"" ));

        
        //TODO: Impliment this in a cleaner way
        if( getGenus().equals( "string-split-at-spaces" ))
        {
            setGenus( "string-split" );
            params.add( new Value( "\"\\s\"" ));
        }

        if( getGenus().equals( "string-vappend" ))
        {
            StringBuilder formatString = new StringBuilder( "\"" );

            for( int i = 0; i < params.size(); i++ )
                formatString.append( "%s" );

            formatString.append( "\"" );
            params.addFirst( new Value( formatString.toString() ));
            
            return new ClassStaticFunctionCall( "java.lang.String", "format", params );
        } else {
            Value target = params.removeFirst();
            String function = (JavaBridgeConstants.API.containsKey( getGenus() ))?JavaBridgeConstants.API.get( getGenus() ):getLabel();
            return new FunctionCall( target.toString(), function, params );
        }
    }
}
