package org.translator.java.manifest;

import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.translator.java.AppInventorScreen;
import org.translator.java.TranslatorConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Joshua
 */
public abstract class ManifestBuilder
{
    public static ManifestFile generateManifest( String projectName, Collection<AppInventorScreen> activities )
    {
        Document doc = null;

        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            doc = builder.newDocument();

            Element root = createRoot( TranslatorConstants.PACKAGE_PREFIX.concat( projectName ), doc );
            root.appendChild( createApplicationElement( activities.toArray( new AppInventorScreen[0] ), projectName, doc ));
            
        } catch( Exception e ) {
            System.err.println( "Error generating manifest: ".concat( e.toString() ));
        }

        return new ManifestFile(doc);
    }

    private static Element createRoot( String packageName, Document doc )
    {
        Element root = doc.createElement( "manifest" );

        root.setAttribute( "xmlns:android", TranslatorConstants.MANIFEST_XMLNS_ANDROID );
        root.setAttribute( "android:versionCode", TranslatorConstants.MANIFEST_ANDROID_VERSIONCODE );
        root.setAttribute( "android:versionName", TranslatorConstants.MANIFEST_ANDROID_VERSIONNAME );
        root.setAttribute( "package", packageName );

        doc.appendChild( root );

        return root;
    }

    private static Element createApplicationElement( AppInventorScreen[] activities, String label, Document doc )
    {
        Element applicationElement = doc.createElement( "application" );

        applicationElement.setAttribute( "android:label", label );
        applicationElement.setAttribute( "android:icon", TranslatorConstants.MANIFEST_ANDROID_ICON );

        for( int i = 0; i < activities.length; i++ )
        {
            //TODO: Determine whether the activity is the one being launched at start of application
            applicationElement.appendChild( createActivityElement( activities[i], (i == 0), doc ));
        }

        return applicationElement;
    }

    private static Element createActivityElement( AppInventorScreen activity, boolean isMain, Document doc )
    {
        Element activityElement = doc.createElement( "activity" );

        activityElement.setAttribute( "android:name", ".".concat( activity.getName() ));

        if( isMain )
        {
            Element actionElement = doc.createElement( "action" );
            Element categoryElement = doc.createElement( "category" );

            actionElement.setAttribute( "android:name", TranslatorConstants.MANIFEST_ACTION_MAIN );
            categoryElement.setAttribute( "android:name", TranslatorConstants.MANIFEST_CATEGORY_LAUNCHER );

            activityElement.appendChild( actionElement );
            activityElement.appendChild( categoryElement );
        }

        return activityElement;
    }
}