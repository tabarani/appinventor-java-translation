/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package android.java;

import android.java.code.SourceFile;

import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author jswank
 */
public class AppInventorProject
{
    private final ArrayList<SourceFile> files = new ArrayList<SourceFile>();
    private final ArrayList<String> assets = new ArrayList<String>();
    private final HashMap<String, AppInventorScreen> screens = new HashMap<String, AppInventorScreen>();
    
    public AppInventorProject( ZipInputStream inputStream ) throws IOException
    {
        load( inputStream );
    }
    
    public AppInventorProject( String directory ) throws IOException
    {
        load( directory );
    }
    
    public void load( ZipInputStream inputStream ) throws IOException
    {
        clear();
        
        ZipEntry ze = null;
        while( (ze = inputStream.getNextEntry()) != null )
        {
            String name =  ze.getName();
            
            if( name.startsWith("assets"))
                assets.add( name );
            else if( name.endsWith( ".blk" ) || name.endsWith( ".scm") || name.endsWith( ".yail" ))
                loadSourceFile( name, inputStream );
        }

        generateSource();
    }
    
    public void load( String directory ) throws IOException
    {
        clear();

        //TODO: Load from directory (not just zip)
        generateSource();
    }
    
    public void clear()
    {
        assets.clear();
        screens.clear();
    }

    //TODO: Clean this up
    private String getFolder( String path )
    {
        int lastSlash = path.lastIndexOf( '/' );

        return path.substring( path.lastIndexOf( '/', lastSlash - 1) + 1, lastSlash );
    }
    
    private void loadSourceFile( String path, InputStream inputStream ) throws IOException
    {
        String projectName = getFolder( path );
        AppInventorScreen screen = screens.get( projectName );
        
        if( screen == null )
            screen = new AppInventorScreen( projectName );

        if( path.endsWith( ".blk" ))
            screen.loadBlocksFile( inputStream );
        else if( path.endsWith( ".scm" ))
            screen.loadComponentFile( inputStream );
        else if( path.endsWith( ".yail" ))
            screen.loadInterfaceFile( inputStream );

        screens.put( projectName, screen );
    }

    private void generateSource()
    {
        files.clear();

        for( AppInventorScreen screen : screens.values() )
            files.add( screen.generateJavaFile() );

        //////////DEBUG//////////////
        for( SourceFile f : files )
            System.out.println( f.toString() );
    }
}
