package com.example.travelapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.travelapp.Activity.TourListDetailActivity;
import com.example.travelapp.JsonDetails;
import com.example.travelapp.Model.TouristListData;
import com.example.travelapp.PreferenceSettings;
import com.example.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TouristAdapter extends ArrayAdapter<TouristListData> {

    private PreferenceSettings mPreferenceSettings;
    private Context context;
    private ArrayList<TouristListData> list;


    public TouristAdapter(Context context, ArrayList<TouristListData> users) {
        super(context, 0, users);
        mPreferenceSettings = new PreferenceSettings(context);
        this.context = context;
        this.list = users;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TouristListData data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_tourist, parent, false);
        }

        TextView mTextPlaceName = (TextView) convertView.findViewById(R.id.tvPlaceName);
        TextView mTextAddress = (TextView) convertView.findViewById(R.id.tvaddress);
        CardView mTouristList = (CardView) convertView.findViewById(R.id.cardViewList);
        final ImageView mImgWishlistAdd = (ImageView) convertView.findViewById(R.id.img_wishlistAdd);
        final ImageView mImgWishlistRemove = (ImageView) convertView.findViewById(R.id.img_wishlistRemove);
        final ImageView mImgPlace = (ImageView) convertView.findViewById(R.id.img);

        mTextPlaceName.setText(data.getPlaceName());
        mTextAddress.setText(data.getAddress());
        ArrayList<String> placeImageList = data.getPhoto();

        int resID = context.getResources().getIdentifier(placeImageList.get(0), "drawable", context.getPackageName());
        mImgPlace.setImageResource(resID);

        String wishListData = mPreferenceSettings.getWishList(context);
        JSONArray jsonArray = JsonDetails.convertToJSONArray(wishListData);
        int pos = -1;
        if (jsonArray != null) {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonArray.getJSONObject(i);

                    if (obj.getInt("id") == getItem(position).getId()) {
                        pos = position;
                        Log.d("id", "same" + getItem(position).getId());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (pos == position) {
            mImgWishlistRemove.setVisibility(View.VISIBLE);
            mImgWishlistAdd.setVisibility(View.GONE);
        } else {
            mImgWishlistRemove.setVisibility(View.GONE);
            mImgWishlistAdd.setVisibility(View.VISIBLE);
        }


        mTouristList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Log.d("selected list id", "" + mPreferenceSettings.getWishList(context));
                Intent intent = new Intent(context, TourListDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tourListData", data);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        mImgWishlistRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<TouristListData>();
                if (mImgWishlistRemove.getVisibility() == View.VISIBLE) {
                    mImgWishlistRemove.setVisibility(View.GONE);
                    mImgWishlistAdd.setVisibility(View.VISIBLE);

                    String wishListData = mPreferenceSettings.getWishList(context);
                    JSONArray jsonArray = JsonDetails.convertToJSONArray(wishListData);
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                ArrayList<String> objPhotoList = new ArrayList<>();
                                JSONObject obj = jsonArray.getJSONObject(i);
                                int id = obj.getInt("id");

                                if (id != data.getId()) {
                                    String placename = obj.getString("placeName");
                                    String address = obj.getString("address");
                                    String phone = obj.getString("phoneNo");
                                    JSONArray photoArray = obj.getJSONArray("photo");
                                    if (photoArray != null) {
                                        for (int h = 0; h < photoArray.length(); h++) {
                                            objPhotoList.add(photoArray.getString(h));
                                        }
                                    }
                                    TouristListData t = new TouristListData(id, placename, address, objPhotoList, phone);
                                    list.add(t);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String wishData = writeDataToPref();
                        if (wishData != null) {
                            mPreferenceSettings.putWishPreference(wishData);
                        }
                    }


                }
            }
        });

        mImgWishlistAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<TouristListData>();
                if (mImgWishlistAdd.getVisibility() == View.VISIBLE) {
                    mImgWishlistAdd.setVisibility(View.GONE);
                    mImgWishlistRemove.setVisibility(View.VISIBLE);

                    String wishListData = mPreferenceSettings.getWishList(context);
                    String wishData;
                    list.add(data);

                    if (wishListData != null && wishListData != "") {

                        JSONArray array = JsonDetails.convertToJSONArray(wishListData);
                        for (int i = 0; i < array.length(); i++) {
                            ArrayList<String> objPhotoList = new ArrayList<>();
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                int id = obj.getInt("id");

                                if (id != data.getId()) {
                                    String placename = obj.getString("placeName");
                                    String address = obj.getString("address");
                                    String phone = obj.getString("phoneNo");
                                    JSONArray photoArray = obj.getJSONArray(("photo"));
                                    if (photoArray != null) {
                                        for (int h = 0; h < photoArray.length(); h++) {

                                            objPhotoList.add(photoArray.getString(i));
                                        }
                                    }
                                    TouristListData t = new TouristListData(id, placename, address, objPhotoList, phone);
                                    list.add(t);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        wishData = writeDataToPref();

                    } else {

                        wishData = writeDataToPref();
                    }
                    if (wishData != null) {
                        mPreferenceSettings.putWishPreference(wishData);
                    }

                }
            }
        });

        return convertView;
    }

    public String writeDataToPref() {
        JSONArray arr = new JSONArray();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TouristListData touristListData = list.get(i);

                JSONObject object = null;
                try {
                    object = new JSONObject();
                    object.put("id", touristListData.getId());
                    object.put("placeName", touristListData.getPlaceName());
                    object.put("address", touristListData.getAddress());
                    object.put("phoneNo", touristListData.getPhoneNo());

                    ArrayList<String> array = touristListData.getPhoto();
                    JSONArray jsonArray1 = new JSONArray(array);
                    object.put("photo", jsonArray1);
                    // 5d. Add that object to the JSON array
                    arr.put(i, object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String wishData = arr.toString();
        return wishData;
    }

}
