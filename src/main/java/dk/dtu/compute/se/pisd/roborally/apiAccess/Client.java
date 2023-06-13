package dk.dtu.compute.se.pisd.roborally.apiAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import dk.dtu.compute.se.pisd.roborally.fileaccess.Adapter;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction;

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
    public String postDataToApiFromFile(String API_BASE_URL,String filePath)
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
    public String postDataToApi(String API_BASE_URL,String jsonData)
    { try {

        // Send POST request to API with JSON content
        URL url = new URL(API_BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Write the JSON content to the request body
        try (OutputStream outputStream = connection.getOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write(jsonData);
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
    public String getStatusProg(int id,int playernumb)
    {
            return getDataFromApi("GET", API_BASE_URL + "/" + id + "/statpost/"+playernumb);

    }
    public String getStatusAct(int id,int playernumb)
    {
        return getDataFromApi("GET", API_BASE_URL + "/" + id + "/statact/"+playernumb);
    }
    public void postGameInstanceActivationPhase(int id,int playerNumb,String jsonData)
    {
        postDataToApi(API_BASE_URL+"/"+id+"/step/"+playerNumb,jsonData);
    }
    public void postGameInstanceProgrammingPhase(int id,String jsonData,int playerNumb)
    {
        postDataToApi(API_BASE_URL+"/"+id+"/prog/"+playerNumb,jsonData);
    }
    public void postGameInstance(int id,String jsonData)
    {
        postDataToApi(API_BASE_URL+"/"+id,jsonData);
    }
    public void postGameSaveInstance(int id,String jsonData,String gameName)
    {
        postDataToApi(API_BASE_URL+"/"+id+"/savegame/"+gameName,jsonData);
    }
    public int CreateGameInstance(int maxNumbOfPlayers)
    {
        return Integer.parseInt(getDataFromApi("GET",API_BASE_URL+"/id/"+maxNumbOfPlayers));
    }
    public String getGameInstance(int id)
    {
        String data =getDataFromApi("GET",API_BASE_URL+"/"+id);
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
    public static Board loadGameInstanceFromString(String jsonString)
    {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = gsonBuilder.create();

        try (JsonReader reader = new JsonReader(new StringReader(jsonString))) {
            BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);
            return template.toBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getGameInstanceAsString(Board board)
    {
        BoardTemplate template = new BoardTemplate().fromBoard(board);

        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>())
                .setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        return gson.toJson(template);
    }


}

