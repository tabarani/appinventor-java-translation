package org.translator.java.manifest;

import java.io.StringWriter;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class ManifestFile {
	private Document d;
	public ManifestFile(Document d) {
		this.d = d;
	}
	
	public String toString() {
		DOMImplementationLS domLoadSave = (DOMImplementationLS) d.getImplementation();		
		LSOutput output = domLoadSave.createLSOutput();
		StringWriter sw = new StringWriter();
		output.setCharacterStream(sw);
		LSSerializer lss = domLoadSave.createLSSerializer();
		lss.write(d, output);
		return sw.toString();
	}
	
	public String getFileName() {
		return "AndroidManifest.xml";
	}

}
