package com.example.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> ImageName = new LinkedList<>(Arrays.asList("bunny", "bunny", "pngtreesky_blue_white_moon_moon_4625017", "ren", "sparklingstar", "elephant", "panda", "bicycle",
            "cat", "cat", "pngtreesky_blue_white_moon_moon_4625017", "ren", "sparklingstar", "elephant", "panda", "bicycle"));

    List<String> ImagesOpened = new LinkedList<>();
    List<String> cardIdArray = new LinkedList<>();
    List<Integer> IdActive = new LinkedList<Integer>();
    int i = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Collections.shuffle(ImageName);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < ImageName.size(); i++) {
                    String ImageID = "image" + (i + 1);
                    int car = getResources().getIdentifier(ImageID, "id", getPackageName());
                    ImageView image = findViewById(car);
                    String imgName = ImageName.get(i);
                    int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    image.setImageResource(id);
                }
            }

            public void onFinish() {
                for (int i = 0; i < ImageName.size(); i++) {
                    String ImageID = "image" + (i + 1);
                    int car = getResources().getIdentifier(ImageID, "id", getPackageName());
                    ImageView image = findViewById(car);

                    image.setImageResource(R.drawable.pinkcloud);
                }
            }

        }.start();
    }

    public void flipCard(View view) {

        ImageView card = (ImageView) view;
        card.setImageResource(R.drawable.pinkcloud);

        Animation flashAnimation = new RotateAnimation(90, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        flashAnimation.setDuration(100);
        flashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                int cardTag = Integer.parseInt(card.getTag().toString());
                String imgName = ImageName.get(cardTag);
                ImagesOpened.add(ImageName.get(cardTag));

                String idString = view.getResources().getResourceEntryName(view.getId());
                cardIdArray.add(idString);

                int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
                card.setImageResource(id);
                i++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (i % 2 == 0) {
                    if (ImagesOpened.get(0) == ImagesOpened.get(1)) {
                        Log.i("match", "found a match");
                        TextView textView = (TextView) findViewById(R.id.textView);
                        score++;

                        textView.setText(score + "");
                        if (score == 8) {
                            textView.setVisibility(View.INVISIBLE);
                            TextView textView2 = (TextView) findViewById(R.id.textView2);
                            textView2.setVisibility(View.VISIBLE);
                            Button play = (Button) findViewById(R.id.button2);
                            play.setVisibility(View.VISIBLE);

                        }
                        String image1ID = cardIdArray.get(0);
                        int resID = getResources().getIdentifier(image1ID, "id", getPackageName());
                        IdActive.add(resID);
                        Log.i("IDactive", IdActive.get(0) + "");
                        String image2ID = cardIdArray.get(1);
                        int res2ID = getResources().getIdentifier(image2ID, "id", getPackageName());
                        IdActive.add(res2ID);
                        Log.i("IDactive", IdActive.get(1) + "");
                        cardIdArray.remove(0);
                        cardIdArray.remove(0);
                        ImagesOpened.remove(0);
                        ImagesOpened.remove(0);

                    } else {
                        Log.i("match", "No match");
                        String image1ID = cardIdArray.get(0);
                        int resID = getResources().getIdentifier(image1ID, "id", getPackageName());
                        ImageView image1 = findViewById(resID);
                        image1.setImageResource(R.drawable.pinkcloud);
                        String image2ID = cardIdArray.get(1);
                        int res2ID = getResources().getIdentifier(image2ID, "id", getPackageName());
                        ImageView image2 = findViewById(res2ID);
                        image2.setImageResource(R.drawable.pinkcloud);
                        cardIdArray.remove(0);
                        cardIdArray.remove(0);
                        ImagesOpened.remove(0);
                        ImagesOpened.remove(0);

                    }
                }
            }

        });
        card.startAnimation(flashAnimation);
    }

    public void onClickImage(View view) {
        ImageView card = (ImageView) view;
        String cardId = card.getId() + "";
        int resourcesId = getResources().getIdentifier(cardId, "id", getPackageName());
        Log.i("resourceid", resourcesId + "");
        boolean Flip = true;
        if (IdActive.size() >= 1) {
            for (int i = 0; i < IdActive.size(); i++) {
                if (IdActive.get(i) == resourcesId) {
                    Flip = false;
                }
            }
        }
        if (Flip == true) {
            flipCard(view);
        }
        Flip = true;

    }

    public void PlayAgain(View view) {
        score = 0;
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.VISIBLE);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setVisibility(View.INVISIBLE);
        Button play = (Button) findViewById(R.id.button2);
        play.setVisibility(View.INVISIBLE);
        IdActive.removeAll(IdActive);
        Collections.shuffle(ImageName);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < ImageName.size(); i++) {
                    String ImageID = "image" + (i + 1);
                    int car = getResources().getIdentifier(ImageID, "id", getPackageName());
                    ImageView image = findViewById(car);
                    String imgName = ImageName.get(i);
                    int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    image.setImageResource(id);
                }
            }

            public void onFinish() {
                for (int i = 0; i < ImageName.size(); i++) {
                    String ImageID = "image" + (i + 1);
                    int car = getResources().getIdentifier(ImageID, "id", getPackageName());
                    ImageView image = findViewById(car);

                    image.setImageResource(R.drawable.pinkcloud);
                }
            }

        }.start();


    }
}