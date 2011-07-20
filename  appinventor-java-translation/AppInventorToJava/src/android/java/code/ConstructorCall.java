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

package android.java.code;

import android.java.code.util.CodeUtil;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Joshua
 */
public class ConstructorCall extends Value
{
    private FunctionCall functionCall;
    private String identifier;

    public ConstructorCall( String identifier )
    {
        this.identifier = identifier;
        functionCall = new FunctionCall( CodeUtil.className( identifier ));
    }

    public ConstructorCall( String identifier, Value[] parameters )
    {
        this.identifier = identifier;
        functionCall = new FunctionCall( CodeUtil.className( identifier ), parameters );
    }

    public ConstructorCall( String identifier, Collection<Value> parameters )
    {
        this.identifier = identifier;
        functionCall = new FunctionCall( CodeUtil.className( identifier ), parameters );
    }

    public String toString()
    {
        return "new ".concat( functionCall.toString() );
    }

    protected SortedMap<String, String> getDependencies()
    {
        TreeMap<String, String> dependencies = new TreeMap<String, String>();

        dependencies.put( CodeUtil.removeGeneric( identifier ), CodeUtil.removeGeneric( CodeUtil.className( identifier )));
        dependencies.putAll( functionCall.getDependencies() );

        return dependencies;
    }
}
