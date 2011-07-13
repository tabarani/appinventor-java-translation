/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package android.java;

import android.blocks.BlocksPage;

import android.java.code.ClassSegment;
import android.java.code.SourceFile;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jswank
 */
class AppInventorScreen
{
    private String projectName;
    private final HashMap<String, String> data = new HashMap<String, String>();
    private final ArrayList<BlocksPage> blocksPages = new ArrayList<BlocksPage>();
    private AppInventorProperties form;

    private final String PACKAGE_PREFIX = "com.android.";

    protected AppInventorScreen( String projectName ) throws IOException
    {
        this.projectName = projectName;
    }
    
    protected void loadBlocksFile( InputStream inputStream ) throws IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setValidating( false );
//        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ));
//        System.out.println( reader.readLine() );

        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Ignores dtd files
            builder.setEntityResolver( new EntityResolver() {
                public InputSource resolveEntity( String publicId, String systemId ) throws SAXException, IOException
                {
                    return new InputSource( new ByteArrayInputStream("".getBytes()));
                }
            });

            Scanner s = new Scanner( inputStream );
            s.useDelimiter( "\\Z" );

            Document blocksDoc = builder.parse( new ByteArrayInputStream( s.next().getBytes() ));
            Element e = blocksDoc.getDocumentElement();

            NodeList nl = e.getChildNodes();

            for( int i = 0; i < nl.getLength(); i++ )
                if( nl.item( i ).getNodeName().equals( "Pages" ))
                {
                    loadPages( nl.item( i ));
                    break;
                }
        } catch( Exception e ) {
            System.err.println( e );
        }
    }

    private void loadPages( Node pagesNode )
    {
        NodeList pages = pagesNode.getChildNodes();

        for( int i = 0; i < pages.getLength(); i++ )
            if( pages.item( i ).getNodeName().equals( "Page" ))
                blocksPages.add( new BlocksPage( pages.item( i )));
    }
    
    protected void loadComponentFile( InputStream inputStream ) throws IOException
    {
        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ));

        reader.skip( 9 );
        parseComponentJSON( new JsonReader( reader ));
    }

    protected SourceFile generateJavaFile()
    {
        SourceFile file = new SourceFile( PACKAGE_PREFIX.concat( projectName ));

        if( form != null )
            file.setMainClass( (ClassSegment)form.generateCode( blocksPages, projectName ));

        return file;
    }
    
    protected void loadInterfaceFile( InputStream inputStream ) throws IOException
    {
        //TODO: Implement
    }

    private void parseComponentJSON( JsonReader reader ) throws IOException
    {
        reader.beginObject();

        while( reader.peek() == JsonToken.NAME )
        {
            String name = reader.nextName();
            if( name.equals( "Properties" ) )
                form = new AppInventorProperties( reader );
            else
                data.put( name, reader.nextString() );
        }

        reader.endObject();
    }
}
