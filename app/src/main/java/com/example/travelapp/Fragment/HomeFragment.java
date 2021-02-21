package com.example.travelapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.Adapter.TouristAdapter;
import com.example.travelapp.JsonDetails;
import com.example.travelapp.Model.TouristListData;
import com.example.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private ListView mListTouristPlace;
    private ArrayList<TouristListData> touristListData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mListTouristPlace = (ListView) view.findViewById(R.id.touristPlace_list);


        touristListData = new ArrayList<TouristListData>();
        String fileData = JsonDetails.loadDataFromFile(getActivity(), "TouristPlaceList.json");
        Log.d(TAG, fileData);
        if (fileData == null) {
            Log.d(TAG, "Error reading data from file");
        }
        JSONArray jsonArray = JsonDetails.convertToJSONArray(fileData);
        if (jsonArray == null) {
            Log.d(TAG, "Error converting to JSON");
        }

        ArrayList<TouristListData> listData = parseJSONDataList(jsonArray);

        TouristAdapter adapter = new TouristAdapter(getActivity(), listData);
        mListTouristPlace.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

    }

    public ArrayList<TouristListData> parseJSONDataList(JSONArray jsonArray) {
        String timeData;

        Log.d(TAG, "Parsing json: " + jsonArray.toString());
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObject = jsonArray.getJSONObject(i);
                // 1. Parse out the id, pt, en data from the JSON file
                int id = currentObject.getInt("id");
                String placeName = currentObject.getString("placeName");
                String description = currentObject.getString("description");
                String website = currentObject.getString("website");
                String phoneNo = currentObject.getString("phoneNo");
                String address = currentObject.getString("address");
                int status = currentObject.getInt("status");

                ArrayList<String> priceList = new ArrayList<>();
                try {
                    JSONArray objPriceList = currentObject.getJSONArray("pricing");
                    for (int j = 0; j < objPriceList.length(); j++) {
                        JSONObject objPrice = objPriceList.getJSONObject(j);
                        String type = objPrice.getString("type");
                        String price = objPrice.getString("price");

                        timeData = type + " : " + price;
                        priceList.add(timeData);

                        Log.d("hoursList", "data" + priceList.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ArrayList<String> photosList = new ArrayList<>();
                try {
                    JSONArray objPhotoList = currentObject.getJSONArray("photo");
                    for (int j = 0; j < objPhotoList.length(); j++) {
                        String objPhoto = objPhotoList.getString(j);
                        Log.d(TAG, "photo " + j + ": " + objPhoto);
                        photosList.add(objPhoto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayList<String> hoursList = new ArrayList<>();
                try {
                    JSONArray objHoursList = currentObject.getJSONArray("hours");
                    for (int k = 0; k < objHoursList.length(); k++) {
                        JSONObject objHours = objHoursList.getJSONObject(k);
                        String day = objHours.getString("day");
                        String time = objHours.getString("time");

                        timeData = day + " : " + time;
                        hoursList.add(timeData);

                        Log.d("hoursList", "data" + hoursList.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TouristListData data = new TouristListData(id, placeName, description, website, phoneNo, address, priceList, photosList, hoursList, status);
                touristListData.add(data);
                Log.d(TAG, "touristListData: " + touristListData.size());
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return touristListData;
    }


}
