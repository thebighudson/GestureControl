package the.big.hudson.gesturecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Button backButton = (Button) findViewById(R.id.homeButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
        // allow user to record a 5 second video of them performing the gesture

        // upload the video to the server
    }
}