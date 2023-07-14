import java.io.File;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Program {
	public static void main(String[] args) {
        try {
            // Get the project directory
            String projectDirectory = System.getProperty("user.dir");

            // Get the list of files in the project directory
            File directory = new File(projectDirectory);
            String[] fileList = directory.list();

            // Filter XML file names
            String[] xmlFiles = filterXMLFiles(fileList);

            // Loop through the XML files
            for (String fileName : xmlFiles) {
                // Construct the file path
                String filePath = projectDirectory + File.separator + fileName;

                // Create a new File object
                File xmlFile = new File(filePath);

                // Create a DocumentBuilderFactory
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                // Create a DocumentBuilder
                DocumentBuilder builder = factory.newDocumentBuilder();

                // Parse the XML file into a Document object
                Document document = builder.parse(xmlFile);

                // Get the root element of the XML
                document.getDocumentElement().normalize();

                // Access the XML data
                NodeList nodeList = document.getElementsByTagName("note");

                // Iterate over the nodes and display their content
                
                for (int i = 0; i < nodeList.getLength(); i++) {
                	System.out.println(fileName);
                    System.out.println("Node " + i + " in " + fileName + ": " + nodeList.item(i).getTextContent());
                }
            }

            // Close any resources if necessarynew
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Filter XML file names from a list of files
    private static String[] filterXMLFiles(String[] fileList) {
        return Arrays.stream(fileList)
                .filter(fileName -> fileName.toLowerCase().endsWith(".xml"))
                .toArray(String[]::new);
    }
}
