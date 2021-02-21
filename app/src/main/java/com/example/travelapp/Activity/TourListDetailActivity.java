package com.example.travelapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.example.travelapp.Adapter.ImageAdapter;
import com.example.travelapp.JsonDetails;
import com.example.travelapp.Model.TouristListData;
import com.example.travelapp.Model.UserRating;
import com.example.travelapp.PreferenceSettings;
import com.example.travelapp.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TourListDetailActivity extends AppCompatActivity {

    private static final String TAG = "TourListDetailActivity";
    private ArrayList<String> imageList;
    private float ratingForPlace;
    private PreferenceSettings mPreferenceSettings;
    private TouristListData tourListData;
    private RatingBar mRatingBar;
    private JSONArray jsonArray;
    private String fileData;
    private ArrayList<UserRating> ratingArrayList;
    private String data;
    private TextView mTvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list_detail);
        mPreferenceSettings = new PreferenceSettings(TourListDetailActivity.this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        imageList = new ArrayList<String>();
        ratingArrayList = new ArrayList<>();
        Intent intent = getIntent();
        tourListData = (TouristListData) intent.getSerializableExtra("tourListData");
        // Log.d("d", " " + tourListData.getId());

        getSupportActionBar().setTitle(tourListData.getPlaceName());

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        CardView cardViewCall = (CardView) findViewById(R.id.cardView_call);
        mTvPhone = (TextView) findViewById(R.id.tvPhone);
        TextView mTvPlaceName = (TextView) findViewById(R.id.tv_placeName);
        TextView mTvDescription = (TextView) findViewById(R.id.tv_description);
        TextView mTvAddress = (TextView) findViewById(R.id.tv_address);
        TextView mTvWebsite = (TextView) findViewById(R.id.tv_website);
        TextView mTvStatus = (TextView) findViewById(R.id.tv_status);
        LinearLayout mLinearTime = (LinearLayout) findViewById(R.id.linear_time);
        LinearLayout mlinearPrice = (LinearLayout) findViewById(R.id.linear_price);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);


        mTvDescription.setText(tourListData.getDescription());
        mTvPlaceName.setText(tourListData.getPlaceName());

        if (tourListData.getPhoneNo() != null) {
            mTvPhone.setText(tourListData.getPhoneNo());
        } else {
            mTvPhone.setText("N/A");
        }

        mTvAddress.setText(tourListData.getAddress());
        mTvWebsite.setText(tourListData.getWebsite());

        int status = tourListData.getStatus();
        if (status == 1) {
            mTvStatus.setVisibility(View.VISIBLE);
        }

        fileData = JsonDetails.loadDataFromSDCard(TourListDetailActivity.this, "test.json");
        if (fileData != null) {
            Log.d(TAG, fileData);
            if (fileData == null) {
                Log.d(TAG, "Error reading data from file");
            }
            jsonArray = JsonDetails.convertToJSONArray(fileData);
        }

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                ratingArrayList = new ArrayList<UserRating>();
                ratingForPlace = ratingBar.getRating();
                UserRating userRating = new UserRating(mPreferenceSettings.getUserId(), tourListData.getId(), ratingForPlace);
                ratingArrayList.add(userRating);


                if (fileData != null && fileData != "") {
                    JSONArray array = JsonDetails.convertToJSONArray(fileData);
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            int userId = obj.getInt("userId");
                            int placeId = obj.getInt("placeId");

                            if (userId != userRating.getUserId() || placeId != tourListData.getId()) {
                                float placeRating = (float) obj.getDouble("placeRating");

                                UserRating t = new UserRating(userId, placeId, placeRating);
                                ratingArrayList.add(t);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    data = setRatingDataToJson();
                    JsonDetails.writeToFile("test.json", data, TourListDetailActivity.this);
                    Log.d(TAG, "Data: " + data);
                } else {
                    data = setRatingDataToJson();

                }
                Log.d("dataaaaa", "Data: " + data);
                JsonDetails.writeToFile("test.json", data, TourListDetailActivity.this);
            }

        });
        imageList = tourListData.getPhoto();

        final ImageAdapter adapter = new ImageAdapter(this, imageList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        cardViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callToPlace();
            }
        });
        setRatingBar();

        mTvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opnWebview();
            }
        });

        mLinearTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(tourListData.getHours(), "Hours Details");
            }
        });

        mlinearPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(tourListData.getPricing(), "Pricing Details");
            }
        });
    }

    public void callToPlace() {
        String phone = mTvPhone.getText().toString();
        try {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(TourListDetailActivity.this, "Please allow Call Permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public void opnWebview() {
        Intent intentWebView = new Intent(TourListDetailActivity.this, WebsiteDetail.class);
        intentWebView.putExtra("url", tourListData.getWebsite());
        startActivity(intentWebView);
    }

    public String setRatingDataToJson() {

        JSONArray arr = new JSONArray();
        for (int i = 0; i < ratingArrayList.size(); i++) {
            // 5c. Represent that word as a JSON object
            UserRating userRating1 = ratingArrayList.get(i);

            JSONObject object = null;
            try {
                object = new JSONObject();
                object.put("userId", userRating1.getUserId());
                object.put("placeId", userRating1.getPlaceId());
                object.put("placeRating", userRating1.getPlaceRating());


                // 5d. Add that object to the JSON array
                arr.put(i, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        data = arr.toString();
        return data;

    }

    public void setRatingBar() {

        if (jsonArray != null) {
            ArrayList<UserRating> listData = parseJSONDataList(jsonArray);
            for (int k = 0; k < listData.size(); k++) {

                if (tourListData.getId() == listData.get(k).getPlaceId()) {
                    if (listData.get(k).getPlaceRating() > 0 && mPreferenceSettings.getUserId() == listData.get(k).getUserId()) {
                        float y = listData.get(k).getPlaceRating();
                        mRatingBar.setRating(y);
                        Log.d("rating", "" + y);
                    }
                }
            }
        }
    }

    public void displayDialog(ArrayList<String> list, String msg) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(TourListDetailActivity.this);

        builderSingle.setTitle(msg);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TourListDetailActivity.this, android.R.layout.select_dialog_item);
        for (int i = 0; i < list.size(); i++) {
            arrayAdapter.add(list.get(i));
        }
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(TourListDetailActivity.this);
                builderInner.setMessage(strName);

            }
        });
        builderSingle.show();

    }

    private ArrayList<UserRating> parseJSONDataList(JSONArray jsonArray) {
        ArrayList<UserRating> userRatingArrayList = new ArrayList<>();
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObject = jsonArray.getJSONObject(i);

                int userId = currentObject.getInt("userId");
                int placeId = currentObject.getInt("placeId");
                float placeRating = (float) currentObject.getDouble("placeRating");
                UserRating userRating = new UserRating(userId, placeId, placeRating);
                userRatingArrayList.add(userRating);


            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return userRatingArrayList;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}