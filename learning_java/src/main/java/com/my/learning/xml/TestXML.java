package com.my.learning.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TestXML {
	public static void main(String[] args) {
		if (true)

			System.out.println("-");

		System.out.println(TestXML.class.getResource("./").getFile());
		System.out.println(TestXML.class.getClassLoader().getResource("./"));

		String testXMLFile = TestXML.class.getClassLoader().getResource("test.xml").getFile();
		System.out.println(testXMLFile);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(TestXML.class.getClassLoader().getResourceAsStream("test.xml"));
			Element rootEle = document.getDocumentElement();
			Attr attr = rootEle.getAttributeNode("android:layout_width");
			System.out.println(attr);
			attr.setValue("new Value");

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(new DOMSource(document),
					new StreamResult(TestXML.class.getClassLoader().getResource("test.xml").getFile()));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
