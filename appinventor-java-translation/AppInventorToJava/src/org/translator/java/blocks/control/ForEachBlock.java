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

package org.translator.java.blocks.control;

import org.translator.java.blocks.Block;
import org.translator.java.blocks.BlockConnector;
import org.translator.java.code.CodeSegment;
import org.translator.java.code.ForEachSegment;
import org.translator.java.code.Value;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class ForEachBlock extends Block
{
    public ForEachBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "foreach";
    }

    public CodeSegment generateCode()
    {
        ForEachSegment segment = new ForEachSegment( getVariable(), getInList() );

        segment.add( getContainedCode()[0] );

        return segment;
    }

    private Value getVariable()
    {
        for( BlockConnector connector : connectors )
            if( connector.getLabel().equals( "variable" ))
                if( connector.hasConnectedBlock() )
                    return new Value( connector.getConnectedBlock().generateCode().toString() );

        //TODO: Make sure this doesn't conflict
        return new Value( "Object o" );
    }

    private Value getInList()
    {
        for( BlockConnector connector : connectors )
            if( connector.getLabel().equals( "in list" ))
                if( connector.hasConnectedBlock() )
                    return (Value)connector.getConnectedBlock().generateCode();

        return new Value( "null" );
    }
}
