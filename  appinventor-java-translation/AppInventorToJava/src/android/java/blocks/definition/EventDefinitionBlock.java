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

package android.java.blocks.definition;

import java.util.HashSet;

import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class EventDefinitionBlock extends FunctionDefinitionBlock
{
    public EventDefinitionBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "[A-Z].*-.*";
    }

    public String getFunctionName()
    {
        return new String( label.replace( ".", "_" ) );
    }

    public String getFunctionReturnType()
    {
        return "void";
    }

    public HashSet<String> getEvents()
    {
        HashSet<String> eventSet = new HashSet<String>();

        eventSet.add( getEventName() );

        return eventSet;
    }

    public String getEventName()
    {
        return label.substring( label.lastIndexOf( "." ) + 1 );
    }
}
