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
import java.util.SortedMap;

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

    protected SortedMap<String, String> getDependencies()
    {
        return buildDependencies( params.toArray( new CodeSegment[0] ));
    }

    public String toString()
    {
        return type.format( params );
    }

    public enum OperationType
    {
        SUM( "+", OperationStyle.INFIX, 3 ),
        DIFFERENCE( "-", OperationStyle.INFIX, 3 ),
        PRODUCT( "*", OperationStyle.INFIX, 2 ),
        QUOTIENT( "/", OperationStyle.INFIX, 2 ),
        MOD( "%", OperationStyle.INFIX, 2 ),
        SHIFT_LEFT( "<<", OperationStyle.INFIX, 4 ),
        SHIFT_RIGHT( ">>", OperationStyle.INFIX, 4 ),
        SHIFT_RIGHT_UNSIGNED( ">>>", OperationStyle.INFIX, 4 ),
        LESS_THAN( "<", OperationStyle.INFIX, 5 ),
        GREATER_THAN( ">", OperationStyle.INFIX, 5 ),
        LESS_THAN_EQUAL_TO( "<=", OperationStyle.INFIX, 5 ),
        GREATER_THAN_EQUAL_TO( ">=", OperationStyle.INFIX, 5 ),
        INSTANCE_OF( "instanceof", OperationStyle.INFIX, 5 ),
        EQUAL_TO( "==", OperationStyle.INFIX, 6 ),
        NOT_EQUAL_TO( "!=", OperationStyle.INFIX, 6 ),
        BITWISE_AND( "&", OperationStyle.INFIX, 7 ),
        BITWISE_OR( "|", OperationStyle.INFIX, 9 ),
        BITWISE_XOR( "^", OperationStyle.INFIX, 8 ),
        LOGICAL_AND( "&&", OperationStyle.INFIX, 10 ),
        LOGICAL_OR( "||", OperationStyle.INFIX, 11 ),
        ASSIGN( "=", OperationStyle.INFIX, 13 ),
        ASSIGN_SUM( "+=", OperationStyle.INFIX, 13 ),
        ASSIGN_DIFFERENCE( "-=", OperationStyle.INFIX, 13 ),
        ASSIGN_PRODUCT( "*=", OperationStyle.INFIX, 13 ),
        ASSIGN_QUOTIENT( "/=", OperationStyle.INFIX, 13 ),
        ASSIGN_MOD( "%=", OperationStyle.INFIX, 13 ),
        ASSIGN_AND( "&=", OperationStyle.INFIX, 13 ),
        ASSIGN_OR( "|=", OperationStyle.INFIX, 13 ),
        ASSIGN_XOR( "^=", OperationStyle.INFIX, 13 ),
        ASSIGN_SHIFT_LEFT( "<<=", OperationStyle.INFIX, 13 ),
        ASSIGN_SHIFT_RIGHT( ">>=", OperationStyle.INFIX, 13 ),
        ASSIGN_SHIFT_RIGHT_UNSIGNED( ">>>=", OperationStyle.INFIX, 13 ),
        INCREMENT_UNARY( "++", OperationStyle.UNARY, 1 ),
        DECREMENT_UNARY( "--", OperationStyle.UNARY, 1 ),
        POSITIVE( "+", OperationStyle.UNARY, 1 ),
        NEGATIVE( "-", OperationStyle.UNARY, 1 ),
        BITWISE_NOT( "~", OperationStyle.UNARY, 1 ),
        LOGICAL_NOT( "!", OperationStyle.UNARY, 1 ),
        INCREMENT_POSTFIX( "++", OperationStyle.POSTFIX, 0 ),
        DECREMENT_POSTFIX( "--", OperationStyle.POSTFIX, 0 ),
        TERNARY( "?:", OperationStyle.TERNARY, 12 ),
        PARENTHESES( "()", OperationStyle.SURROUND, 14 );

        private String value;
        private OperationStyle style;
        private int precedence;

        private OperationType( String value, OperationStyle style, int precedence )
        {
            this.value = value.toLowerCase();
            this.style = style;
            this.precedence = precedence;
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
            INFIX( 2, "infix" ), UNARY( 1, "unary" ), POSTFIX( 1, "postfix" ), TERNARY( 3, "ternary" ), SURROUND( 1, "surround" );

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
                            s = String.format( "%s%c%s%c%s", params[0], operator.charAt( 0 ), params[1], operator.charAt( 1 ), params[2] );
                        break;

                        case SURROUND:
                            s = String.format( "%c%s%c", operator.charAt( 0 ), params[0], operator.charAt( 1 ));
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
