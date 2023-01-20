package the.big.hudson.gesturecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        // allow user to record a 5 second video of them performing the gesture

        // upload the video to the server
    }
}