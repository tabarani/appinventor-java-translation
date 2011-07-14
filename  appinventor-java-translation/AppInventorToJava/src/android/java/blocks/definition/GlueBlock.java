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

import android.java.blocks.Block;
import android.java.blocks.BlockConnector;
import android.java.blocks.annotation.BlockAnnotation;
import android.java.blocks.annotation.StringRelationship;
import android.java.code.CodeSegment;
import android.java.code.Value;
import android.java.code.ValueStatement;
import org.w3c.dom.Node;

@BlockAnnotation(
    genus = "glue",
    genusRelation = StringRelationship.EQUALS )

/**
 *
 * @author Joshua
 */
public class GlueBlock extends Block
{
    public GlueBlock( Node block )
    {
        super( block );
    }

    public CodeSegment toCode()
    {
        BlockConnector toCall = connectors.get( 0 );

        if( toCall.hasConnectedBlock() )
            return new ValueStatement( (Value)toCall.getConnectedBlock().toCode() );
        else
            return null;
    }
}
