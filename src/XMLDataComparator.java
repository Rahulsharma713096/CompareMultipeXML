
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;
import java.util.Arrays;

public class XMLDataComparator {
	public static void main(String[] args) {
		try {
			// Get the project directory
			String projectDirectory = System.getProperty("user.dir");
			// Get the list of files in the project directory
			File directory = new File(projectDirectory);
			String[] fileList = directory.list();

			// Filter XML file names
			String[] xmlFiles = filterXMLFiles(fileList);
			System.out.println("********************************************************");

			// Create a DocumentBuilder using DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			for (int i = 0; i < xmlFiles.length - 1; i++) {
				for (int j = i + 1; j < xmlFiles.length; j++) {
					// Load the first XML file
					Document document1 = builder.parse(new File(xmlFiles[i]));
					System.out.print(xmlFiles[i]);
					document1.getDocumentElement().normalize();
					// Load the second XML file
					Document document2 = builder.parse(new File(xmlFiles[j]));
					document2.getDocumentElement().normalize();
					System.out.println(":	"+xmlFiles[j]);
System.out.println(i +"------"+j);
					extractCommonData(document1, document2);
					System.out.println("-------------------------------------");
				}
			}
			System.out.println("********************************************************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Filter XML file names from a list of files
	private static String[] filterXMLFiles(String[] fileList) {
		return Arrays.stream(fileList).filter(fileName -> fileName.toLowerCase().endsWith(".xml"))
				.toArray(String[]::new);
	}

	// Extract common data from two XML files
	private static void extractCommonData(Document document1, Document document2) {
		// Get the root elements of the XML files
		Element rootElement1 = document1.getDocumentElement();
		Element rootElement2 = document2.getDocumentElement();
		compareTags(rootElement1, rootElement2, "to");
		List<String> tagsList = new ArrayList<>();

		// Adding string data to the list
		tagsList.add("from");
		tagsList.add("heading");
		tagsList.add("body");

		// Accessing string data from the list
		for (String str : tagsList) {

			compareTags(rootElement1, rootElement2, str);
		}

	}

	private static void compareTags(Element rootElement1, Element rootElement2, String tag) {

		// Extract and compare data from the XML files
		NodeList nodeList1 = rootElement1.getElementsByTagName(tag);

		NodeList nodeList2 = rootElement2.getElementsByTagName(tag);

		// Iterate over the elements and compare
		for (int i = 0; i < nodeList1.getLength(); i++) {
			Element element1 = (Element) nodeList1.item(i);
			String data1 = element1.getTextContent();

			for (int j = 0; j < nodeList2.getLength(); j++) {
				Element element2 = (Element) nodeList2.item(j);
				String data2 = element2.getTextContent();

				// Compare the data and extract common elements
				if (data1.equals(data2)) {
					// Process the common data as per your requirements
					System.out.println("Common data: " + data1);

				}
			}
		}
	}

}
