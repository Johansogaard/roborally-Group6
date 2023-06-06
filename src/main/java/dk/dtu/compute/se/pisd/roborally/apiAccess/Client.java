package dk.dtu.compute.se.pisd.roborally.apiAccess;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Johan s224324
 *
 */
public class Client {
    private static final String API_BASE_URL = "http://localhost:8080/game"; // Replace with your API's base URL
    private static final String curr_Game_Path = "src/main/resources/currGameInstance/currGame.json";
    public String getGameInstance(String gameID)
    {
        return getDataFromApi("GET",API_BASE_URL+"/"+gameID);
    }
    public String postDataToApi(String API_BASE_URL,String filePath)
    { try {
        // Read the content of the JSON file
        String jsonContent = readJsonFile(filePath);

        // Send POST request to API with JSON content
        URL url = new URL(API_BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Write the JSON content to the request body
        try (OutputStream outputStream = connection.getOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write(jsonContent);
        }

        // Get the response from the API
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Process the response
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            return response.toString();
        } else {
            // Handle error response
            System.out.println("Error response: " + responseCode);
            return Integer.toString(responseCode);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
        return null;
    }


    private String readJsonFile(String filePath) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            return new String(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void postGameInstance(int id)
    {
        postDataToApi(API_BASE_URL+"/"+id,curr_Game_Path);
    }
    public int CreateGameInstance(int maxNumbOfPlayers)
    {
        return Integer.parseInt(getDataFromApi("GET",API_BASE_URL+"/id/"+maxNumbOfPlayers));
    }
    public String getGameInstance(int id)
    {
        String data =getDataFromApi("GET",API_BASE_URL+"/"+id);
        saveStringAsJsonFile(data,curr_Game_Path);
        return data;
    }
    //returns the player number you are in the game
    public int joinGame(int id)
    {
      return Integer.parseInt(getDataFromApi("GET",API_BASE_URL+"/"+id+"/join"));
    }
    public String getDataFromApi(String request,String API_BASE_URL)
    {
        try {
            // Send GET request to retrieve game state
            URL url = new URL(API_BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request);

            // Get the response from the API
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Process the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);

                }
                reader.close();
                return response.toString();

            } else {
                // Handle error response
                System.out.println("Error response: " + responseCode);
                return Integer.toString(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

