package edu.neu.madcourse.crack_it_up;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Headings go here
    public String[] slide_headings = {
        "HEADING1",
        "HEADING2",
        "HEADING3"
    };

    //Descriptions go here
    public String[] slide_descriptions = {
            "Czech Marketa Vondrousova set up an Olympic women's tennis final against Belinda Bencic by hammering Ukraine's Elina Svitolina on Thursday in Tokyo. Svitolina was the highest-ranked player left in the draw after shock early exits for Naomi Osaka, beaten by Vondrousova, and Ashleigh Barty, but crashed to a 6-3, 6-1 defeat. Vondrousova, the 2019 French Open runner-up, will take on Switzerland's Bencic for the title on Saturday. (Reuters Photo)",
            "I don't understand this scoring system, how did she [Mary Kom] lose the first round 1-4 when there was hardly anything separating these two. It is a disappointment but that's luck I guess.",
            "India's Mary Kom loses her round of 16 bout against Ingrit Valencia of Colombia 2-3 to bow out of the Games. Mary won the third and final round 3-2, but the margin of defeat in the first round was too much to recover for the six-time world champion."
    };


    //count of slides go here
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull  View view, @NonNull  Object object) {
        return view == (ConstraintLayout) object;
    }


    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {
      layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
      View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

      //Initialize and set dynamic values for each slide
        TextView slideHeading = (TextView) view.findViewById(R.id.heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.description);

        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        container.addView(view);

      return view;
    }

    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position, @NonNull  Object object) {
       container.removeView((ConstraintLayout) object);
    }
}
