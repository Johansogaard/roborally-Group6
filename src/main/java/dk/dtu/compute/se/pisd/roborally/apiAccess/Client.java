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
import java.util.ArrayList;

/**
 * @author Johan s224324
 * This is the client that holds all the methods used to communicate with the server
 *
 */
public class Client {
    private static final String API_BASE_URL = "http://localhost:8080/game"; // Replace with your API's base URL so the ip4 for your server http://[IP]:8080/game



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



    public String getStatusProg(int id,int playernumb)
    {
            return getDataFromApi("GET", API_BASE_URL + "/" + id + "/statpost/"+playernumb);

    }
    public String getFileFromName(String fileName)
    {
       return getDataFromApi("GET",API_BASE_URL+"/files/"+fileName);
    }
    public String[] getStart(int id)
    {
        String data = getDataFromApi("GET", API_BASE_URL + "/" + id + "/players");
        String[] datas = data.split(",");
       return datas;
    }
    public void setStart(int id)
    {
        postDataToApi(API_BASE_URL+"/"+id+"/"+"start","true");

    }
    public String[] getFilesString()
    {
        String data =getDataFromApi("GET",API_BASE_URL+"/files");
        String[] files = data.split(",");
        return files;
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
    public void postGameSaveInstance(String jsonData,String gameName)
    {
        postDataToApi(API_BASE_URL+"/savegame/"+gameName,jsonData);
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

