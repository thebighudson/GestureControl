package the.big.hudson.gesturecontrol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity has a VideoView to play the chosen gesture video.
 * It also contains 3 buttons
 * -Back: return to the main view where you can select a new gesture
 * -Replay: allows user to replay the video again
 * -Record: Will send user to the record activity where they can record their own gesture
 */
public class ViewActivity extends AppCompatActivity {

    private String videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        this.handleButtons();
        this.handleVideoPlayback();
    }

    /**
     * Setup button listeners for the 3 buttons on the view activity.
     */
    private void handleButtons() {
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(ViewActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

        Button recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("TEST", "WHAT THE FUCK");
                Intent recordActivity = new Intent(ViewActivity.this, RecordActivity.class);
                startActivity(recordActivity);
            }
        });

        Button replayButton = (Button) findViewById(R.id.replayButton);
        replayButton.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View v){
                playVideo();
            }
        });
    }

    /**
     * Determines which gester has been selected from the dropdown and sets the class variable
     * videoUri.
     */
    private void handleVideoPlayback() {
        // Get the video name from the main activity and play it from the server
        String selectedGesture = getIntent().getStringExtra("gesture_name");
     //   String id = getIntent().getStringExtra("id");
        //use a switch to get video URL based on selected gesture
        StringBuilder uriString = new StringBuilder("https://scaryguy.pythonanywhere.com/static/files/");

        switch (selectedGesture) {
            case "Zero":
                uriString.append("H-0.mp4");
                break;
            case "One":
                uriString.append("H-1.mp4");
                break;
            case "Two":
                uriString.append("H-2.mp4");
                break;
            case "Three":
                uriString.append("H-3.mp4");
                break;
            case "Four":
                uriString.append("H-04.mp4");
                break;
            case "Five":
                uriString.append("H-5.mp4");
                break;
            case "Six":
                uriString.append("H-6.mp4");
                break;
            case "Seven":
                uriString.append("H-7.mp4");
                break;
            case "Eight":
                uriString.append("H-8.mp4");
                break;
            case "Nine":
                uriString.append("H-9.mp4");
                break;
            case "Decrease Fan":
                uriString.append("H-DecreaseFanSpeed.mp4");
                break;
            case "Increase Fan":
                uriString.append("H-IncreaseFanSpeed.mp4");
                break;
            case "Fan Off":
                uriString.append("H-FanOff.mp4");
                break;
            case "Fan On":
                uriString.append("H-FanOn.mp4");
                break;
            case "Light On":
                uriString.append("H-LightOn.mp4");
                break;
            case "Light Off":
                uriString.append("H-LightOff.mp4");
                break;
            case "Set Thermostat":
                uriString.append("H-SetThermo.mp4");
                break;
            default:
                break;
        }


        VideoView videoView = findViewById(R.id.videoView);

        this.videoUri = String.valueOf(uriString);

        playVideo();
    }

    /**
     * This function uses the set videoUri and plays the selected gesture video.
     *
     * This is also used by the replay button.
     */
    public void playVideo(){
        VideoView videoView = findViewById(R.id.videoView);

        Uri uri = Uri.parse(this.videoUri);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}