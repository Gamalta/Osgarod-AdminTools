package fr.gamalta.osgarod.admintools.utils;

import fr.gamalta.osgarod.admintools.AdminTools;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Utils {

    AdminTools main;

    public Utils(AdminTools main) {

        this.main = main;
    }

    public ArrayList<JSONObject> getPlayerNames(UUID uuid) {

        ArrayList<JSONObject> names = new ArrayList<>();

        try {
            URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names");
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonString = "";
            String line = null;

            while ((line = in.readLine()) != null) {
                jsonString += line;
            }
            if (!jsonString.isEmpty()) {

                JSONArray nameArray = new JSONArray(jsonString);
                for (int i = 0; i < nameArray.length(); ++i) {

                    JSONObject obj = nameArray.getJSONObject(i);

                    if (obj != null) {

                        names.add(obj);

                    }
                }
            }
        } catch (Exception e) {
        }

        return names;
    }

    public static HashMap<String, String> getIpInformation(String ip) throws IOException {

        HashMap<String, String> info = new HashMap<>();

        @SuppressWarnings("resource")
		String request = new Scanner(new URL("http://ip-api.com/json/" + ip + "?fields=1796601").openStream(), "UTF-8").useDelimiter("\\A").next();
        String[] entry = request.substring(1, request.length() - 1).replace("\"", "").split(",");

        for(String string : entry){

            String[] args = string.split(":");
            info.put(args[0], args.length == 1 ? "" : args[1]);

        }

        return info;
    }
}