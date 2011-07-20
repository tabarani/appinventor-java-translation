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

import android.java.api.util.APIUtil;
import java.util.ArrayList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Joshua
 */
public class APIEntry
{
    private String genus;
    private ArrayList<OutputEntry> outputs = new ArrayList<OutputEntry>();

    public APIEntry( Node entry )
    {
        NamedNodeMap fields = entry.getAttributes();

        this.genus = APIUtil.getField( fields, "genus" );

        String simpleFunction = APIUtil.getField( fields, "simpleFunction" );

        if( !simpleFunction.equals( "" ))
            this.outputs.add( new FunctionOutputEntry( simpleFunction ));
        else
            loadOutputs();
    }

    public String getString()
    {
        return new String( genus );
    }

    public String toString()
    {
        return getString();
    }

    private void loadOutputs()
    {
        
    }
}
