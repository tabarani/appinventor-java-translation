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

package org.translator.java.code;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Joshua
 */
public class Operation extends Value
{
    private OperationType type;
    private final ArrayList<Value> params = new ArrayList<Value>();

    public Operation( String type, String style, Value... params )
    {
        this.type = OperationType.fromString( type, style );
        
        for( Value v : params )
            this.params.add( v );
    }

    public Operation( String type, String style, Collection<Value> params )
    {
        this.type = OperationType.fromString( type, style );
        this.params.addAll( params );
    }

    public Operation( String type, Value... params )
    {
        this.type = OperationType.fromString( type );

        for( Value v : params )
            this.params.add( v );
    }

    public Operation( String type, Collection<Value> params )
    {
        this.type = OperationType.fromString( type );
        this.params.addAll( params );
    }

    public String toString()
    {
        return type.format( params );
    }

    public enum OperationType
    {
        SUM( "+", OperationStyle.INFIX),
        DIFFERENCE( "-", OperationStyle.INFIX),
        PRODUCT( "*", OperationStyle.INFIX),
        QUOTIENT( "/", OperationStyle.INFIX),
        MOD( "%", OperationStyle.INFIX),
        SHIFT_LEFT( "<<", OperationStyle.INFIX),
        SHIFT_RIGHT( ">>", OperationStyle.INFIX),
        SHIFT_RIGHT_UNSIGNED( ">>>", OperationStyle.INFIX),
        LESS_THAN( "<", OperationStyle.INFIX),
        GREATER_THAN( ">", OperationStyle.INFIX),
        LESS_THAN_EQUAL_TO( "<=", OperationStyle.INFIX),
        GREATER_THAN_EQUAL_TO( ">=", OperationStyle.INFIX),
        INSTANCE_OF( "instanceof", OperationStyle.INFIX),
        EQUAL_TO( "==", OperationStyle.INFIX),
        NOT_EQUAL_TO( "!=", OperationStyle.INFIX),
        BITWISE_AND( "&", OperationStyle.INFIX),
        BITWISE_OR( "|", OperationStyle.INFIX),
        BITWISE_XOR( "^", OperationStyle.INFIX),
        LOGICAL_AND( "&&", OperationStyle.INFIX),
        LOGICAL_OR( "||", OperationStyle.INFIX),
        ASSIGN( "=", OperationStyle.INFIX),
        ASSIGN_SUM( "+=", OperationStyle.INFIX),
        ASSIGN_DIFFERENCE( "-=", OperationStyle.INFIX),
        ASSIGN_PRODUCT( "*=", OperationStyle.INFIX),
        ASSIGN_QUOTIENT( "/=", OperationStyle.INFIX),
        ASSIGN_MOD( "%=", OperationStyle.INFIX),
        ASSIGN_AND( "&=", OperationStyle.INFIX),
        ASSIGN_OR( "|=", OperationStyle.INFIX),
        ASSIGN_XOR( "^=", OperationStyle.INFIX),
        ASSIGN_SHIFT_LEFT( "<<=", OperationStyle.INFIX),
        ASSIGN_SHIFT_RIGHT( ">>=", OperationStyle.INFIX),
        ASSIGN_SHIFT_RIGHT_UNSIGNED( ">>>=", OperationStyle.INFIX),
        INCREMENT_UNARY( "++", OperationStyle.UNARY),
        DECREMENT_UNARY( "--", OperationStyle.UNARY),
        POSITIVE( "+", OperationStyle.UNARY),
        NEGATIVE( "-", OperationStyle.UNARY),
        BITWISE_NOT( "~", OperationStyle.UNARY),
        LOGICAL_NOT( "!", OperationStyle.UNARY),
        INCREMENT_POSTFIX( "++", OperationStyle.POSTFIX),
        DECREMENT_POSTFIX( "--", OperationStyle.POSTFIX),
        TERNARY( "?", OperationStyle.TERNARY);

        private String value;
        private OperationStyle style;

        private OperationType( String value, OperationStyle style)
        {
            this.value = value.toLowerCase();
            this.style = style;
        }

        public static OperationType fromString( String operator, String style )
        {
            OperationStyle s = OperationStyle.fromString( style );

            for( OperationType t : OperationType.values() )
                if( t.value.equals( operator.toLowerCase() ) && t.style.equals( s ))
                    return t;

            return null;
        }

        public static OperationType fromString( String operator )
        {
            for( OperationType t : OperationType.values() )
                if( t.value.equals( operator.toLowerCase() ))
                    return t;

            return null;
        }

        public String format( Value... params )
        {
            return style.format( value, params );
        }

        public String format( Collection<Value> params )
        {
            return format( params.toArray( new Value[0] ));
        }

        private enum OperationStyle
        {
            INFIX( 2, "infix" ), UNARY( 1, "unary" ), POSTFIX( 1, "postfix" ), TERNARY( 3, "ternary" );

            private int nParams;
            private String name;

            private OperationStyle( int nParams, String name )
            {
                this.nParams = nParams;
                this.name = name.toLowerCase();
            }

            String format( String operator, Value... params )
            {
                String s = null;

                if( params.length >= nParams )
                    switch( this )
                    {
                        case UNARY:
                            s = String.format( "%s%s", operator, params[0] );
                        break;

                        case INFIX:
                            s = String.format( "%s %s %s", params[0], operator, params[1] );
                        break;

                        case POSTFIX:
                            s = String.format( "%s%s", params[0], operator );
                        break;

                        case TERNARY:
                            s = String.format( "%s%s%s:%s", params[0], operator, params[1], params[2] );
                        break;
                    }

                return s;
            }

            String format( String operation, Collection<Value> params )
            {
                return format( operation, params.toArray( new Value[0] ));
            }

            static OperationStyle fromString( String style )
            {
                for( OperationStyle s : OperationStyle.values() )
                    if( s.name.equals( style.toLowerCase() ))
                        return s;

                return null;
            }
        }
    }
}
