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

package org.translator.java.code.api;

import org.translator.java.code.api.util.APIUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import org.translator.java.code.CodeSegment;
import org.translator.java.code.Value;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class APIEntry
{
    private String genus;
    private int minParams = -1, maxParams = -1;
    private boolean isStatic = false;
    private ArrayList<ActionEntry> actions = new ArrayList<ActionEntry>();

    public APIEntry( Node entry )
    {
        NamedNodeMap fields = entry.getAttributes();

        this.genus = APIUtil.getField( fields, "genus" );

        String nParams = APIUtil.getField( fields, "nParams" );
        if( !nParams.isEmpty() )
            this.minParams = this.maxParams = Integer.valueOf( nParams );
        else
        {
            String max = APIUtil.getField( fields, "maxParams" );
            String min = APIUtil.getField( fields, "minParams" );

            if( !max.isEmpty() )
                this.maxParams = Integer.valueOf( max );
            if( !min.isEmpty() )
                this.minParams = Integer.valueOf( min );
        }

        String stat = APIUtil.getField( fields, "static" );
        if( !stat.isEmpty() )
            this.isStatic = Boolean.valueOf( stat );

        String simpleFunction = APIUtil.getField( fields, "simpleFunction" );

        if( !simpleFunction.isEmpty() )
            this.actions.add( new FunctionEntry( simpleFunction ));
        else
            loadActions( entry );
    }

    public String getGenus()
    {
        return new String( genus );
    }

    public String toString()
    {
        return getGenus();
    }

    public CodeSegment generateCode( APIMapping mapping, LinkedList<Value> p )
    {
        LinkedList<Value> params = (LinkedList<Value>)p.clone();
        Value target = isStatic?null:params.removeFirst();

        return generateCode( mapping, target, params );
    }

    protected CodeSegment generateCode( APIMapping mapping, Value target, LinkedList<Value> params )
    {
        CodeSegment segment = new CodeSegment();

        for( ActionEntry action : actions )
            segment.add( action.generateCode( mapping, target, params ));

        return segment;
    }

    protected boolean matches( Collection<Value> params )
    {
        int size = params.size();

        if( !isStatic )
            size--;

        if( minParams >= 0 )
            if( size < minParams )
                return false;

        if( maxParams >= 0 )
            if( size > maxParams )
                return false;

        return true;
    }

    private void loadActions( Node entry )
    {
        NodeList children = entry.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
        {
            ActionEntry e = ActionEntryFactory.create( children.item( i ));
            if( entry != null )
                actions.add( e );
        }
    }
}
