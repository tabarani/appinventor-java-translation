package org.translator.java.eclipseproject;

import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class ProjectBuilder {
	public static ProjectFile generateProject(String projectName) {
		Document doc = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			doc = builder.newDocument();
			Element root = doc.createElement("projectDescription");
			root.appendChild(createName(doc, projectName));
			root.appendChild(createComment(doc));
			root.appendChild(createProjects(doc));
			root.appendChild(createBuildSpec(doc));
			root.appendChild(createNatures(doc));
			doc.appendChild(root);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return new ProjectFile(doc);
	}
	
	private static Element createComment(Document d) {
		return d.createElement("comment");
	}
	
	private static Element createName(Document d, String name) {
		Element root = d.createElement("name");
		Text child = d.createTextNode(name);
		root.appendChild(child);
		return root;
	}
	
	private static Element createProjects(Document d) {
		return d.createElement("projects");
	}
	
	private static Element createBuildSpec(Document d) {
		Element root = d.createElement("buildSpec");
		List<Element> buildCommands = createBuildCommands(d);
		for (Element e : buildCommands) root.appendChild(e);
		return root;
	}
	
	private static List<Element> createBuildCommands(Document d) {
		final String[] commandNames = {
				"com.android.ide.eclipse.adt.ResourceManagerBuilder",
				"com.android.ide.eclipse.adt.PrecompilerBuilder",
				"org.eclipse.jdt.core.javabuilder",
				"com.android.ide.eclipse.adt.ApkBuilder"
		};
		List<Element> elements = new LinkedList<Element>();
		for (String commandName : commandNames) {
			Element root = d.createElement("buildCommand");
			Element name = d.createElement("name");
			name.setTextContent(commandName);
			root.appendChild(name);
			Element arguments = d.createElement("arguments");
			root.appendChild(arguments);
			elements.add(root);
		}
		return elements;
	}
	
	private static Element createNatures(Document d) {
		Element root = d.createElement("natures");
		List<Element> natures = createNatureList(d);
		for (Element e : natures) root.appendChild(e);
		return root;
	}
	
	private static List<Element> createNatureList(Document d) {
		final String[] natureNames = {
				"com.android.ide.eclipse.adt.AndroidNature",
				"org.eclipse.jdt.core.javanature",
		};
		List<Element> elements = new LinkedList<Element>();
		for (String natureName : natureNames) {
			Element root = d.createElement("nature");
			root.setTextContent(natureName);
			elements.add(root);
		}
		return elements;
	}

}
