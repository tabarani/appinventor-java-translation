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
import android.java.blocks.annotation.BlockAnnotation;
import android.java.code.AssignmentStatement;
import android.java.code.CodeSegment;
import android.java.code.CodeVisibility;
import android.java.code.ConstructorCall;
import android.java.code.DeclarationStatement;
import android.java.code.Value;
import android.java.util.CodeUtil;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Node;

@BlockAnnotation( genusPattern = "def" )

/**
 *
 * @author Joshua
 */
public class VariableDefinitionBlock extends Block
{
    public VariableDefinitionBlock( Node block )
    {
        super( block );
    }

    public CodeSegment declare()
    {
        return new DeclarationStatement( connectors.get( 0 ).getDataType(), label, CodeVisibility.PRIVATE );
    }

    public CodeSegment define()
    {
        String dataType = connectors.get( 0 ).getDataType();
        Collection<Value> constructorParameters = getConstructorParameters();

        try
        {
            if( !Class.forName( CodeUtil.removeGeneric( dataType )).isPrimitive() )
                return new AssignmentStatement( label, new ConstructorCall( dataType, constructorParameters ));
        } catch( ClassNotFoundException e ) {
            System.err.println( e );
        }

        return null;
    }

    private Collection<Value> getConstructorParameters()
    {
        ArrayList<Value> parameters = new ArrayList<Value>();

        return parameters;
    }
}
