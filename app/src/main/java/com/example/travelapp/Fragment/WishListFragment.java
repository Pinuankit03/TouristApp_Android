package com.example.travelapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.travelapp.Adapter.WishListAdapter;
import com.example.travelapp.JsonDetails;
import com.example.travelapp.Model.TouristListData;
import com.example.travelapp.PreferenceSettings;
import com.example.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WishListFragment extends Fragment {

    private static final String TAG = "WishListFragment";
    private PreferenceSettings mPreferenceSettings;
    private ArrayList<TouristListData> touristListData;
    private TextView mTextWishList;
    private ArrayList<String> hoursList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        mPreferenceSettings = new PreferenceSettings(getActivity());
        touristListData = new ArrayList<TouristListData>();
        hoursList = new ArrayList<>();
        ListView mListWishList = (ListView) view.findViewById(R.id.wishList_list);
        mTextWishList = (TextView) view.findViewById(R.id.tvNoWishlist);

        String wishListData = mPreferenceSettings.getWishList(getActivity());
        Log.d("wishListData", "" + wishListData);

        if (wishListData != null && wishListData != "") {
            JSONArray jsonArray = JsonDetails.convertToJSONArray(wishListData);
            ArrayList<TouristListData> listData = parseJSONDataWishList(jsonArray);

            if (listData.size() > 0) {
                mListWishList.setVisibility(View.VISIBLE);
                mTextWishList.setVisibility(View.GONE);
                WishListAdapter adapter = new WishListAdapter(getActivity(), listData);
                mListWishList.setAdapter(adapter);
            } else {
                mListWishList.setVisibility(View.GONE);
                mTextWishList.setVisibility(View.VISIBLE);
            }

        } else {
            mListWishList.setVisibility(View.GONE);
            mTextWishList.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public ArrayList<TouristListData> parseJSONDataWishList(JSONArray jsonArray) {

        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);
                    int id = currentObject.getInt("id");
                    String placeName = currentObject.getString("placeName");
                    String address = currentObject.getString("address");
                    String phone = currentObject.getString("phoneNo");


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

                    TouristListData data = new TouristListData(id, placeName, address, photosList, phone);

                    touristListData.add(data);
                    Log.d(TAG, "touristListData: " + touristListData.size());
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();

        }
        return touristListData;
    }

}