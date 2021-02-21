package com.example.travelapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class JsonDetails {

    private static final String TAG = "JsonDetails";

    ;

    public static String loadDataFromFile(Context context, String fileName) {
        // get the JSON file from the

        String jsonString;
        try {
            // open the file
            InputStream fileData = context.getAssets().open(fileName);

            // get information about the file
            int fileSize = fileData.available();

            // set up a array to store each piece of data in the file
            // the size of the array is the same size as the file
            byte[] buffer = new byte[fileSize];

            // get all the data from the file
            fileData.read(buffer);

            // close the file
            fileData.close();

            // convert the data to json
            jsonString = new String(buffer, "UTF-8");

            return jsonString;

        } catch (IOException e) {
            Log.d(TAG, "Error opening file.");
            e.printStackTrace();
            return null;
        }
    }

    public static String loadDataFromSDCard(Context context, String fileName) {
        // get the JSON file from the

        String jsonString;
        try {
            // open the file
            File file = new File(context.getFilesDir(), fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String responce = stringBuilder.toString();
            return responce;

        } catch (IOException e) {
            Log.d(TAG, "Error opening file.");
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray convertToJSONArray(String fileData) {
        JSONArray jsonData;
        try {
            // 1. try to convert the string to the JSONArray data type
            jsonData = new JSONArray(fileData);
            // 2. if successful return it
            return jsonData;

        } catch (JSONException e) {
            // 2. if conversion fails, then print error message and return
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(String filename, String data, Context context) {
        try {
            // 1. Try to open the file on the phone's internal storage
            // - internal storage, external storage (sd card)
            OutputStreamWriter outputStreamWriter
                    = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));

            // DEBUG: If you wnat to see the path where the file is stored, you can do it
            Log.d(TAG, "File is saved: " + context.getFilesDir().getAbsolutePath());

            // 2. Put the data into the file
            outputStreamWriter.write(data);

            // 3. Save the file
            outputStreamWriter.close();

            // 4. Output a success message
            Log.d(TAG, "File written");
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
