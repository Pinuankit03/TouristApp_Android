package com.example.travelapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.Model.TouristListData;
import com.example.travelapp.R;

import java.util.ArrayList;

public class WishListAdapter extends ArrayAdapter<TouristListData> {

    private ArrayList<TouristListData> list;
    private Context context;

    public WishListAdapter(Context context, ArrayList<TouristListData> users) {
        super(context, 0, users);
        this.list = users;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final TouristListData data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_wishlist, viewGroup, false);
        }
        TextView mTextPlaceName = (TextView) view.findViewById(R.id.tvWishPlaceName);
        TextView mTextAddress = (TextView) view.findViewById(R.id.tvWishAddress);
        ImageView mImagePlace = (ImageView) view.findViewById(R.id.img_place);

        LinearLayout mLinearCall = (LinearLayout) view.findViewById(R.id.linear_call);

        mTextAddress.setText(data.getAddress());
        mTextPlaceName.setText(data.getPlaceName());
        // ArrayList<String> placeImageList = list.get(position).getPhoto();

        int resID = context.getResources().getIdentifier(list.get(position).getPhoto().get(0), "drawable", context.getPackageName());
        mImagePlace.setImageResource(resID);

        mLinearCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + data.getPhoneNo()));
                    context.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Please allow Call Permission.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }
}
