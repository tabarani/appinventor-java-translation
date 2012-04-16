package org.translator.java.blocks;

import java.util.LinkedList;

import org.translator.java.code.CodeSegment;
import org.translator.java.code.FunctionCall;
import org.translator.java.code.Value;
import org.translator.java.code.ValueStatement;
import org.w3c.dom.Node;

public class FinishBlock extends Block {
	
    public FinishBlock( Node node )
    {
        super( node );
    }

    public static String getGenusPattern()
    {
        return "close-screen";
    }
    
    public CodeSegment generateCode() {
    	return new ValueStatement(new FunctionCall("finishActivity", new LinkedList<Value>()));
    }
}
