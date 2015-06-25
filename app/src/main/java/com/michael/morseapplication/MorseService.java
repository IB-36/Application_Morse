package com.michael.morseapplication;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Camera;
import android.os.SystemClock;
import android.util.Log;


/**
 * Created by Michael on 2015-05-29.
 */

@SuppressWarnings("deprecation")
public class MorseService extends IntentService {

    private Camera camera;
    private boolean isFlashlightOn;
    private boolean isCameraFlash;
    private Camera.Parameters params;
    private String message;
    private String TAG;
    boolean endWord;
    public static final String MESSAGE_TO_TRANSLATE = "com.michael.morseapplication";

    public MorseService() {
        super("MorseService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        message = intent.getStringExtra(MESSAGE_TO_TRANSLATE);
        message.toLowerCase();
        for(int i = 0;i < message.length();i++) {
            endWord = false;
            char letter = message.charAt(i);
            switch(letter) {
                case 'a':
                    dot();
                    dash();
                case 'b':
                    dash();
                    dot();
                    dot();
                    dot();
                case 'c':
                    dash();
                    dot();
                    dash();
                    dot();
                case 'd':
                    dash();
                    dot();
                    dot();
                case 'e':
                    dot();
                case 'f':
                    dot();
                    dot();
                    dash();
                    dot();
                case 'g':
                    dash();
                    dash();
                    dot();
                case 'h':
                    dot();
                    dot();
                    dot();
                    dot();
                case 'i':
                    dot();
                    dot();
                case 'j':
                    dot();
                    dash();
                    dash();
                    dash();
                case 'k':
                    dash();
                    dot();
                    dash();
                case 'l':
                    dot();
                    dash();
                    dot();
                    dot();
                case 'm':
                    dash();
                    dash();
                case 'n':
                    dash();
                    dot();
                case 'o':
                    dash();
                    dash();
                    dash();
                case 'p':
                    dot();
                    dash();
                    dash();
                    dot();
                case 'q':
                    dash();
                    dash();
                    dot();
                    dash();
                case 'r':
                    dot();
                    dash();
                    dot();
                case 's':
                    dot();
                    dot();
                    dot();
                case 't':
                    dash();
                case 'u':
                    dot();
                    dot();
                    dash();
                case 'v':
                    dot();
                    dot();
                    dot();
                    dash();
                case 'w':
                    dot();
                    dash();
                    dash();
                case 'x':
                    dash();
                    dot();
                    dot();
                    dash();
                case 'y':
                    dash();
                    dot();
                    dash();
                    dash();
                case 'z':
                    dash();
                    dash();
                    dot();
                    dot();
                case '0':
                    dash();
                    dash();
                    dash();
                    dash();
                    dash();
                case '1':
                    dot();
                    dash();
                    dash();
                    dash();
                    dash();
                case '2':
                    dot();
                    dot();
                    dash();
                    dash();
                    dash();
                    dash();
                case '3':
                    dot();
                    dot();
                    dot();
                    dash();
                    dash();
                case '4':
                    dot();
                    dot();
                    dot();
                    dot();
                    dash();
                case '5':
                    dot();
                    dot();
                    dot();
                    dot();
                    dot();
                case '6':
                    dash();
                    dot();
                    dot();
                    dot();
                    dot();
                case '7':
                    dash();
                    dash();
                    dot();
                    dot();
                    dot();
                case '8':
                    dash();
                    dash();
                    dash();
                    dot();
                    dot();
                case '9':
                    dash();
                    dash();
                    dash();
                    dash();
                    dot();
                case '.':
                    dot();
                    dash();
                    dot();
                    dash();
                    dot();
                    dash();
                case ',':
                    dash();
                    dash();
                    dot();
                    dot();
                    dash();
                    dash();
                case ':':
                    dash();
                    dash();
                    dash();
                    dot();
                    dot();
                    dot();
                case '?':
                    dot();
                    dot();
                    dash();
                    dash();
                    dot();
                    dot();
                case '-':
                    dash();
                    dot();
                    dot();
                    dot();
                    dot();
                    dash();
                case '/':
                    dash();
                    dot();
                    dot();
                    dash();
                    dot();
                case '@':
                    dot();
                    dash();
                    dash();
                    dot();
                    dash();
                    dot();
                case '=':
                    dash();
                    dot();
                    dot();
                    dot();
                    dash();
                case '(':
                    dash();
                    dot();
                    dash();
                    dash();
                    dot();
                    dash();
                case ')':
                    dash();
                    dot();
                    dash();
                    dash();
                    dot();
                    dash();
                case ' ':
                    dot();
                    dot();
                    dot();
                    dot();
                    dot();
                    dot();
                    dot();
                    endWord = true;
            }//switch
            if(endWord) {
                SystemClock.sleep(2331);
            }//if
            if(i>0 && !endWord) {
                SystemClock.sleep(1000);
            }
        }//for
    }//onHandleIntent

    public void dot(){
        Log.d(TAG, "dot() called");
        setFlashlightOn();
        SystemClock.sleep(333);
        setFlashlightOff();
        Log.d(TAG, "end dot() called");
        SystemClock.sleep(333);
    }
    public void dash(){
        Log.d(TAG, "dash() called");
        setFlashlightOn();
        SystemClock.sleep(1000);
        setFlashlightOff();
        Log.d(TAG, "end dash() called");
        SystemClock.sleep(333);
    }
    public void setFlashlightOn() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        isFlashlightOn = true;
    }//setFlashlightOn
    public void setFlashlightOff() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        isFlashlightOn = false;
    }//setFlashlightOff
}//Class
