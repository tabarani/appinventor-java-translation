package android.java.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author jswank
 */
class ZipFilter extends FileFilter
{
    public ZipFilter()
    {
        
    }
    
    public boolean accept( File f )
    {
        String name = f.getName().toLowerCase();
        
        if( name.endsWith( ".zip" ) || f.isDirectory() )
            return true;
        else
            return false;
    }
    
    public String getDescription()
    {
        return "Zip files";
    }
}
