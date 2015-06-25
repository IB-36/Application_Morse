package com.michael.morseapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MorseActivity extends Activity {

    private Camera camera;
    private Button buttonSend;
    private Button button2;
    private EditText editText;
    private TextView textView;
    private MediaPlayer mediaPlayer;
    private Parameters params;
    private SeekBar seekBar;
    private Spinner frequency_spinner;
    private ArrayAdapter<CharSequence> adapter;
    private CheckBox checkBoxBeep;
    private CheckBox checkBoxLight;
    private boolean isFlashlightOn;
    private boolean endWord;
    private boolean high_frequency_beep;
    private boolean medium_frequency_beep;
    private boolean low_frequency_beep;
    private boolean Switch;
    private boolean cameraOpen;
    private boolean defaultTime = true;
    private String TAG = "Method_Sequence";
    private String message;
    private float progress;
    private float time;
    private float defaultWPM = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);
        initializeLayoutVariables();
        checkBoxLight.setChecked(true);
        seekBar.setMax(25);
        textView.setText(("5") + " Word per minute");

        adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequency_spinner.setAdapter(adapter);
        frequency_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String frequency = parent.getItemAtPosition(position).toString();
                if (frequency.equals("High frequency")) {
                    high_frequency_beep = true;
                    medium_frequency_beep = false;
                    low_frequency_beep = false;
                    Log.d(TAG, "HIGH");
                } else if (frequency.equals("Medium frequency")) {
                    medium_frequency_beep = true;
                    high_frequency_beep = false;
                    low_frequency_beep = false;
                    Log.d(TAG, "MEDIUM");
                } else // (frequency.equals("Low"))
                {
                    low_frequency_beep = true;
                    medium_frequency_beep = false;
                    high_frequency_beep = false;
                    Log.d(TAG, "LOW");
                }
            }//onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                medium_frequency_beep = true;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                if (progressValue <= defaultWPM) {
                    progressValue = (int)defaultWPM;
                }
                progress = progressValue;
                time = 240 * (defaultWPM / progress);
                Log.d(TAG,(Float.toString(time)));
                defaultTime = false;
                textView.setText(Float.toString(progress) + " Words per minute");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        boolean isCameraFlash = this.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(!isCameraFlash) {
            showCameraAlert();
        } else {
            camera = Camera.open();
            params = camera.getParameters();
            Log.d(TAG, "CALLED params = camera.getParameters()");
            cameraOpen = true;
        }
        if(defaultTime) {
            time = 240;
        }
    }//onCreate

    private void initializeLayoutVariables() {
        buttonSend = (Button) findViewById(R.id.button_send);
        //button2 = (Button) findViewById(R.id.sos);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.print_message);
        frequency_spinner = (Spinner) findViewById(R.id.frequency_spinner);
        checkBoxBeep = (CheckBox) findViewById(R.id.beep);
        checkBoxLight = (CheckBox) findViewById(R.id.light);
    }

    public void dot(){
        Log.d(TAG, "dot() called");
        if (checkBoxLight.isChecked() && checkBoxBeep.isChecked()) {
            setFlashlightOn();
            playBeep();
            SystemClock.sleep(Math.round(time));
            setFlashlightOff();
            stopBeep();
            SystemClock.sleep(Math.round(time));
        }
        else if (checkBoxBeep.isChecked() && !checkBoxLight.isChecked()) {
            playBeep();
            SystemClock.sleep(Math.round(time));
            stopBeep();
            SystemClock.sleep(Math.round(time));
        }
        else {
            if(checkBoxLight.isChecked()) {
                setFlashlightOn();
                SystemClock.sleep(Math.round(time));
                setFlashlightOff();
                SystemClock.sleep(Math.round(time));
            }
            else {
                    Toast.makeText(this,"Choose Light and/or Beep", Toast.LENGTH_LONG).show();
            }

        }
    }//dot

    public void dash(){
        Log.d(TAG, "dash() called");
        if (checkBoxLight.isChecked() && checkBoxBeep.isChecked()) {
            setFlashlightOn();
            playBeep();
            SystemClock.sleep(Math.round(time) * 3);
            setFlashlightOff();
            stopBeep();
            SystemClock.sleep(Math.round(time));
        }
        else if (checkBoxBeep.isChecked() && !checkBoxLight.isChecked()) {
            playBeep();
            SystemClock.sleep(Math.round(time) * 3);
            stopBeep();
            SystemClock.sleep(Math.round(time));
        }
        else {
            if(checkBoxLight.isChecked()) {
                setFlashlightOn();
                SystemClock.sleep(Math.round(time) * 3);
                setFlashlightOff();
                SystemClock.sleep(Math.round(time));
            }
            else {
                Toast.makeText(this,"Choose Light and/or Beep", Toast.LENGTH_LONG).show();
            }
        }
    }//dash

    public void setFlashlightOn() {
        if(!cameraOpen) {
            camera = Camera.open();
            params = camera.getParameters();
            cameraOpen = true;
        }
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        isFlashlightOn = true;
    }

    public void setFlashlightOff() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        isFlashlightOn = false;
    }

    public void playBeep(){
        if(high_frequency_beep) {
            mediaPlayer = MediaPlayer.create(this, R.raw.high_frequency_beep);
        }
        if(medium_frequency_beep) {
            mediaPlayer = MediaPlayer.create(this, R.raw.medium_frequency_beep);
        }
        if(low_frequency_beep) {
            mediaPlayer = MediaPlayer.create(this, R.raw.low_frequency_beep);
        }
        mediaPlayer.start();
    }

    public void stopBeep(){
        mediaPlayer.stop();
    }

    public void showCameraAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Error: No Camera Flash!")
                .setMessage("Camera flashlight not available in this Android device!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }//showCameraAlert

//    public void SOS(View view){
//        for(;;) {
//            dot();
//            dot();
//            dot();
//            dash();
//            dash();
//            dash();
//            dot();
//            dot();
//            dot();
//            SystemClock.sleep(Math.round(time) * 7);
//        }
//    }//sos

//    public void stop(){
//        new AlertDialog.Builder(this)
//                .setTitle("STOP")
//                .setMessage("Click OK to stop")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        onPause();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }

    public void sendMorseMessage(View view){
        editText = (EditText) findViewById(R.id.enter_message);
        message = editText.getText().toString().toLowerCase();
        Log.d(TAG, message);
        for(int i = 0;i < message.length();i++) {
            endWord = false;
            char letter = message.charAt(i);
            switch(letter) {
                case 'a':
                    dot();
                    dash();
                    Log.d(TAG, "A");
                    break;
                case 'b':
                    dash();
                    dot();
                    dot();
                    dot();
                    Log.d(TAG, "B");
                    break;
                case 'c':
                    dash();
                    dot();
                    dash();
                    dot();
                    Log.d(TAG, "C");
                    break;
                case 'd':
                    dash();
                    dot();
                    dot();
                    Log.d(TAG, "D");
                    break;
                case 'e':
                    dot();
                    Log.d(TAG, "E");
                    break;
                case 'f':
                    dot();
                    dot();
                    dash();
                    dot();
                    Log.d(TAG, "F");
                    break;
                case 'g':
                    dash();
                    dash();
                    dot();
                    Log.d(TAG, "G");
                    break;
                case 'h':
                    dot();
                    dot();
                    dot();
                    dot();
                    Log.d(TAG, "H");
                    break;
                case 'i':
                    dot();
                    dot();
                    Log.d(TAG, "I");
                    break;
                case 'j':
                    dot();
                    dash();
                    dash();
                    dash();
                    Log.d(TAG, "J");
                    break;
                case 'k':
                    dash();
                    dot();
                    dash();Log.d(TAG, "K");
                    break;
                case 'l':
                    dot();
                    dash();
                    dot();
                    dot();
                    Log.d(TAG, "L");
                    break;
                case 'm':
                    dash();
                    dash();
                    Log.d(TAG, "M");
                    break;
                case 'n':
                    dash();
                    dot();
                    Log.d(TAG, "N");
                    break;
                case 'o':
                    dash();
                    dash();
                    dash();
                    Log.d(TAG, "O");
                    break;
                case 'p':
                    dot();
                    dash();
                    dash();
                    dot();
                    Log.d(TAG, "P");
                    break;
                case 'q':
                    dash();
                    dash();
                    dot();
                    dash();
                    Log.d(TAG, "Q");
                    break;
                case 'r':
                    dot();
                    dash();
                    dot();
                    Log.d(TAG, "R");
                    break;
                case 's':
                    dot();
                    dot();
                    dot();
                    Log.d(TAG, "S");
                    break;
                case 't':
                    dash();
                    Log.d(TAG, "T");
                    break;
                case 'u':
                    dot();
                    dot();
                    dash();
                    Log.d(TAG, "U");
                    break;
                case 'v':
                    dot();
                    dot();
                    dot();
                    dash();
                    Log.d(TAG, "V");
                    break;
                case 'w':
                    dot();
                    dash();
                    dash();
                    Log.d(TAG, "W");
                    break;
                case 'x':
                    dash();
                    dot();
                    dot();
                    dash();
                    Log.d(TAG, "X");
                    break;
                case 'y':
                    dash();
                    dot();
                    dash();
                    dash();
                    Log.d(TAG, "Y");
                    break;
                case 'z':
                    dash();
                    dash();
                    dot();
                    dot();
                    Log.d(TAG, "Z");
                    break;
                case '0':
                    dash();
                    dash();
                    dash();
                    dash();
                    dash();
                    break;
                case '1':
                    dot();
                    dash();
                    dash();
                    dash();
                    dash();
                    break;
                case '2':
                    dot();
                    dot();
                    dash();
                    dash();
                    dash();
                    dash();
                    break;
                case '3':
                    dot();
                    dot();
                    dot();
                    dash();
                    dash();
                    break;
                case '4':
                    dot();
                    dot();
                    dot();
                    dot();
                    dash();
                    break;
                case '5':
                    dot();
                    dot();
                    dot();
                    dot();
                    dot();
                    break;
                case '6':
                    dash();
                    dot();
                    dot();
                    dot();
                    dot();
                    break;
                case '7':
                    dash();
                    dash();
                    dot();
                    dot();
                    dot();
                    break;
                case '8':
                    dash();
                    dash();
                    dash();
                    dot();
                    dot();
                    break;
                case '9':
                    dash();
                    dash();
                    dash();
                    dash();
                    dot();
                    break;
                case '.':
                    dot();
                    dash();
                    dot();
                    dash();
                    dot();
                    dash();
                    break;
                case ',':
                    dash();
                    dash();
                    dot();
                    dot();
                    dash();
                    dash();
                    break;
                case ':':
                    dash();
                    dash();
                    dash();
                    dot();
                    dot();
                    dot();
                    break;
                case '?':
                    dot();
                    dot();
                    dash();
                    dash();
                    dot();
                    dot();
                    break;
                case '-':
                    dash();
                    dot();
                    dot();
                    dot();
                    dot();
                    dash();
                    break;
                case '/':
                    dash();
                    dot();
                    dot();
                    dash();
                    dot();
                    break;
                case '@':
                    dot();
                    dash();
                    dash();
                    dot();
                    dash();
                    dot();
                    break;
                case '=':
                    dash();
                    dot();
                    dot();
                    dot();
                    dash();
                    break;
                case '(':
                    dash();
                    dot();
                    dash();
                    dash();
                    dot();
                    dash();
                    break;
                case ')':
                    dash();
                    dot();
                    dash();
                    dash();
                    dot();
                    dash();
                    break;
                case ' ':
                    endWord = true;
                    break;
                default:
                    SystemClock.sleep(Math.round(time) * 3);
                    break;
            }//switch
            if(endWord) {
                SystemClock.sleep(Math.round(time) * 7);
            }//if
            else {
                SystemClock.sleep(Math.round(time) * 3);
                Log.d(TAG, "END CHARACTER");
            }
        }//for
    }//sendMessage
    //        public void dot(){
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                new CountDownTimer(500, 500) {
//                    public void onTick(long millisUntilFinished) {
//                        Log.d(TAG, "dot() called");
//                        setFlashlightOn();
//                    }
//                    public void onFinish() {
//                        Log.d(TAG, "end dot() called");
//                        setFlashlightOff();
//                    }
//                }.start();//CountdownTimer
//            }//runnable
//        }, 500);
//    }//dot
    @Override
    protected void onStop() {
        super.onStop();
        if(camera != null) {
            camera.release();
            camera = null;
            cameraOpen = false;
        }
    }//onStop
}//Class
