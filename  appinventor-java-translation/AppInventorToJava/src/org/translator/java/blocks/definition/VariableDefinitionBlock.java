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

package org.translator.java.blocks.definition;

import org.translator.java.blocks.BlockConnector;
import org.translator.java.blocks.DefinitionBlock;
import org.translator.java.code.AssignmentStatement;
import org.translator.java.code.CodeSegment;
import org.translator.java.code.CodeVisibility;
import org.translator.java.code.ConstructorCall;
import org.translator.java.code.DeclarationStatement;
import org.translator.java.code.Value;
import org.translator.java.code.util.CodeUtil;
import java.util.Collection;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class VariableDefinitionBlock extends DefinitionBlock
{
    public VariableDefinitionBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "def";
    }

    public CodeSegment declare()
    {
        return new DeclarationStatement( connectors.get( 0 ).getDataType(), label, CodeVisibility.PRIVATE );
    }

    public CodeSegment define()
    {
        BlockConnector connector = connectors.get( 0 );
        String dataType = connector.getDataType();
        Collection<Value> constructorParameters = connector.getConstructorParameters();

        try
        {
            if( !Class.forName( CodeUtil.removeGeneric( dataType )).isPrimitive() )
                return new AssignmentStatement( label, new ConstructorCall( dataType, constructorParameters ));
        } catch( ClassNotFoundException e ) {
            System.err.println( e );
        }

        return null;
    }
}
