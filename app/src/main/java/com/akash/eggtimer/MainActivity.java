package com.akash.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    boolean counterIsActive = false;
    Button goButton;
    Button button;
    CountDownTimer countDownTimer;
    ImageView imageView1, imageView2;
    public void resetTimer(){
        //seekBar.setEnabled(true);
        textView.setText("0.30");
      //  seekBar.setProgress(30);
        countDownTimer.cancel();
        goButton.setText("Go");
        counterIsActive=false;
    }
    public void fadeIn(View view){

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.animate().alpha(0).setDuration(100);
        imageView1.animate().alpha(1).setDuration(100);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.VISIBLE);
        seekBar.setEnabled(true);
        seekBar.setProgress(30);

    }
    public void buttonClicked(View view) {
        if (counterIsActive) {
            resetTimer();
        } else {

            counterIsActive = true;
            seekBar.setEnabled(false);
            goButton.setText("Stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                    seekBar.setProgress((int) l /1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.breaking);
                    mediaPlayer.start();
                    imageView1.animate().alpha(0).setDuration(100);
                    imageView2.animate().alpha(1).setDuration(100);
                    button.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    goButton.setVisibility(View.INVISIBLE);
                    seekBar.setEnabled(false);



                    resetTimer();
                }
            }.start();
        }
    }
    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String minuteString = Integer.toString(minutes);
        String secondString = Integer.toString(seconds);
        if(seconds<=9)
            secondString = "0" + secondString;

        String string1 = minuteString + ":" + secondString;
        textView.setText(string1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        goButton = findViewById(R.id.button);
         button= findViewById(R.id.button2);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        seekBar.setMax(600);        //60 seconds ina minute and we are setting it to 10minutes
        seekBar.setProgress(30);    //Current position is 30 seconds


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
