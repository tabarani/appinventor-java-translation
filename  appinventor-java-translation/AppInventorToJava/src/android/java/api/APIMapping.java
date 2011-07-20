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

package android.java.api;

import android.java.JavaBridgeConstants;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class APIMapping extends HashMap<String, APIEntry>
{
    public APIMapping()
    {
        load( JavaBridgeConstants.API_MAPPING_FILE );
    }

    private void load( String fileName )
    {
        try
        {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( fileName );

            NodeList nodes = doc.getDocumentElement().getChildNodes();

            for( int i = 0; i < nodes.getLength(); i++ )
                addEntry( nodes.item( i ));
        } catch( Exception e ) {
            System.err.println( "Error reading API mapping: ".concat( e.toString() ));
        }
    }

    private void addEntry( Node n )
    {
        if( n.getNodeName().equals( "Entry" ))
        {
            APIEntry entry = new APIEntry( n );
            put( entry.toString(), entry );
        }
    }
}
