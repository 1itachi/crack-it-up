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

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<FlashCard> listOfFlashcards = new ArrayList<>();

    public SliderAdapter(Context context,ArrayList<FlashCard> flashCards ){
        this.context = context;
        this.listOfFlashcards = flashCards;
    }


    //count of slides go here
    @Override
    public int getCount() {
        return listOfFlashcards.size();
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

        slideHeading.setText(listOfFlashcards.get(position).getHeading());
        slideDescription.setText(listOfFlashcards.get(position).getContent());

        container.addView(view);

      return view;
    }

    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position, @NonNull  Object object) {
       container.removeView((ConstraintLayout) object);
    }
}
