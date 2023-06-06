package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.apiAccess.Client;

import java.io.FileWriter;
import java.io.IOException;

public class ClientController {
    int id;
    private static final String API_BASE_URL = "http://localhost:8080/game"; // Replace with your API's base URL

    public final Client client;
    public ClientController(Client client)
    {
        this.client = client;
    }
    public void createGame() {
        int id = client.getID();

       // client.postDataToApi(API_BASE_URL)
    }



    public static void saveStringAsJsonFile(String jsonString, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
            System.out.println("JSON file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
