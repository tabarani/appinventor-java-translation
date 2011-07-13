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

package android.blocks;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Joshua
 */
public class BlockStub extends Block
{
    String stubParentName = null, stubParentGenus = null;
    
    public BlockStub( Node block )
    {
        NodeList children = block.getChildNodes();

        for( int i = 0; i < children.getLength(); i++ )
        {
            String name = children.item( i ).getNodeName();

            if( name.equals( "StubParentName" ))
                stubParentName = children.item( i ).getTextContent();
            else if( name.equals( "StubParentGenus" ))
                stubParentGenus = children.item( i ).getTextContent();
            else if(name.equals("Block"))
                load( children.item( i ));
        }
    }
}
