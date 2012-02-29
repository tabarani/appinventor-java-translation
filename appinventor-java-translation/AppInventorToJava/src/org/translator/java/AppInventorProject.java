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

package org.translator.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.translator.java.code.SourceFile;
import org.translator.java.eclipseproject.ProjectBuilder;
import org.translator.java.eclipseproject.ProjectFile;
import org.translator.java.manifest.ManifestBuilder;
import org.translator.java.manifest.ManifestFile;

/**
 *
 * @author jswank
 */
public class AppInventorProject
{
    private final ArrayList<SourceFile> files = new ArrayList<SourceFile>();
    private ManifestFile manifest = null;
    private ProjectFile project = null;
    private final ArrayList<String> assets = new ArrayList<String>();
    private final HashMap<String, AppInventorScreen> screens = new HashMap<String, AppInventorScreen>();
    private String projectName = null;
    
    public AppInventorProject( File inputFile ) throws IOException
    {
        load( inputFile );
    }

    private void load( File inputFile ) throws IOException
    {
        InputStream inputStream = null;

            if( inputFile.getName().toLowerCase().endsWith( ".zip" ))
            {
                inputStream = new ZipInputStream( new FileInputStream( inputFile ));
                load( (ZipInputStream)inputStream );
            } else {
            
            }

            inputStream.close();
    }

    private void load( ZipInputStream inputStream ) throws IOException
    {
        clear();
        
        ZipEntry ze = null;
        while( (ze = inputStream.getNextEntry()) != null )
        {
            String name =  ze.getName();
            
            if( name.startsWith("assets"))
                assets.add( name );
            else if( name.endsWith( ".blk" ) || name.endsWith( ".scm" ) || name.endsWith( ".yail" ))
                loadSourceFile( name, inputStream );
        }

        generateSource();
    }
    
    public void clear()
    {
        assets.clear();
        screens.clear();
    }
    
    public void writeOutput( ZipOutputStream outputStream )
    {
    }
    
    public void writeOutput( String directory ) throws IOException
    {
        //////////DEBUG//////////////
        /*for( SourceFile f : files )
            System.out.println( f.toString() );*/

        /*
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult( sw );
        DOMSource source = new DOMSource( manifest );

        try
        {
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            trans.transform( source, result );

            System.out.println( sw.toString() );
        } catch( Exception e ) {
            System.err.println( e.toString() );
        }*/

        File f = new File(directory);

        int i = 0;
        if( f.isDirectory() ) {
            for(AppInventorScreen screen : screens.values())
            {
                File screenFile = new File(getScreenFilePath(f.getAbsolutePath(), screen));

                screenFile.getParentFile().mkdirs();
                screenFile.createNewFile();
                
                FileWriter out = new FileWriter(screenFile);
                out.write(files.get(i).toString());
                out.close();

                i++;
            }
            File manifestFile = new File(getManifestFilePath(f.getAbsolutePath(), manifest));
            manifestFile.getParentFile().mkdirs();
            manifestFile.createNewFile();
            FileWriter out = new FileWriter(manifestFile);
            out.write(manifest.toString());
            out.close();
            
            File projectFile = new File(getProjectFilePath(f.getAbsolutePath(), project));
            projectFile.getParentFile().mkdirs();
            projectFile.createNewFile();
            out = new FileWriter(projectFile);
            out.write(project.toString());
            out.close();
        }
    }

    private String getScreenFilePath(String prefix, AppInventorScreen screen)
    {
        StringBuilder builder = new StringBuilder(prefix);
        String s = File.separator;

        builder.append(s).append("src").append(s).append("org");
        builder.append(s).append(projectName.toLowerCase());
        builder.append(s).append(screen.getName()).append(".java");

        return builder.toString();
    }
    
    private String getManifestFilePath(String prefix, ManifestFile m) {
        StringBuilder builder = new StringBuilder(prefix);
        String s = File.separator;
    	builder.append(s).append(m.getFileName());
    	return builder.toString();
    }
    
    private String getProjectFilePath(String prefix, ProjectFile p) {
        StringBuilder builder = new StringBuilder(prefix);
        String s = File.separator;
    	builder.append(s).append(p.getFileName());
    	return builder.toString();
    }

    //TODO: Clean this up
    private String getFolder( String path )
    {
        int lastSlash = path.lastIndexOf( '/' );

        return path.substring( path.lastIndexOf( '/', lastSlash - 1) + 1, lastSlash );
    }
    
    private void loadSourceFile( String path, InputStream inputStream ) throws IOException
    {
        projectName = getFolder( path );
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

        manifest = ManifestBuilder.generateManifest(projectName, screens.values());
        project = ProjectBuilder.generateProject(projectName);
    }


}
