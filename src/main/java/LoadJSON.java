import cricket.Match;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadJSON {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        // Take filepath from user
        Scanner inputScan = new Scanner(System.in);
        System.out.println("Enter input filepath:");
        String inputFilePath = inputScan.next();
        System.out.println("Parsing file " + inputFilePath + " ...");

        // Open and read specified file
        File file = new File(inputFilePath);
        Scanner scan = new Scanner(file);
        String fileContent = "";
        while (scan.hasNextLine()) {
            fileContent = fileContent.concat(scan.nextLine() + "\n");
        }

        // Parse the file
        JSONParser jsonParser = new JSONParser();
        JSONObject data = (JSONObject) jsonParser.parse(fileContent);

        Match cricketMatch = Match.parseFromJson(data);
        System.out.println(cricketMatch.printMatch());
    }
}
