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

import org.translator.java.blocks.Block;
import org.translator.java.blocks.BlockConnector;
import org.translator.java.blocks.literal.MathLiteralBlock;
import org.translator.java.code.CodeSegment;
import org.translator.java.code.CodeVisibility;
import org.translator.java.code.FunctionCall;
import org.translator.java.code.FunctionSegment;
import org.translator.java.code.Parameter;
import org.translator.java.code.ReturnStatement;
import org.translator.java.code.Value;
import org.translator.java.code.ValueStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class FunctionDefinitionBlock extends DefinitionBlock
{
    private final ArrayList<Parameter> parameters = new ArrayList<Parameter>();

    public FunctionDefinitionBlock( Node block )
    {
        super( block );
    }

    public static String getGenusPattern()
    {
        return "define.*";
    }

    public Collection<Integer> getParameterNumbers()
    {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for( Parameter p : parameters )
            numbers.add( p.getIndex() );

        return numbers;
    }

    public CodeSegment declare()
    {
        return createDeclaration();
    }

    public CodeSegment define()
    {
        return null;
    }

    public CodeSegment generateCode()
    {
        return null;
    }

    public String getFunctionName()
    {
        return new String( label );
    }

    public String getFunctionReturnType()
    {
        if( genusName.contains( "-" ))
            return genusName.substring( genusName.indexOf( "-" )+1 );
        else
            return "Object";
    }

    public final Collection<Parameter> getDeclarationParameters()
    {
        return parameters;
    }

    protected boolean setReferences( HashMap<Integer, Block> blocksMap )
    {
        boolean returnValue = super.setReferences( blocksMap );
        int iArg = 0;

        for( BlockConnector connector : connectors )
        {
            Parameter p = connector.getParameter( iArg );

            if( p != null )
                parameters.add( p );

            iArg++;
        }

        return returnValue;
    }

    private Collection<Value> getCallParameters()
    {
        ArrayList<Value> values = new ArrayList<Value>();

        for( BlockConnector connector : connectors )
        {
            Block connected = connector.getConnectedBlock();
            if( connected instanceof MathLiteralBlock )
                values.add( (Value)connected.generateCode() );
            else
                values.add( new Value( connected.getLabel() ));
        }

        return values;
    }

    private CodeSegment createDeclaration()
    {
        FunctionSegment function = new FunctionSegment( getFunctionName(), CodeVisibility.PRIVATE, getFunctionReturnType(), getDeclarationParameters().toArray( new Parameter[0] ));

        function.add( getContainedCode()[0] );

        ReturnStatement[] returns = getReturnStatements();

        if( returns.length > 0 )
            function.add( returns[0] );

        return function;
    }
}
