package com.example.travelapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.travelapp.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<String> imageList;

    public ImageAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.imageList = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.pagerimage, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        String imageName = imageList.get(position);

        int resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        imageView.setImageResource(resID);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}

