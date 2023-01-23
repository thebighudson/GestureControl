package the.big.hudson.gesturecontrol;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {

    private static final int VIDEO_CAPTURE = 101;
    private Uri videoUri;

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

        Button recordButton = (Button) findViewById(R.id.recordButton2);
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startRecording();
            }
        });

        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Get the current videoUri and then upload that file to
                // my PythonAnywhere server
            }
        });

        if (!hasCamera())
            recordButton.setEnabled(false);

    }

    /**
     * Validate the device has a camera
     * @return boolean indicating whether the device has a camera or not
     */
    private boolean hasCamera() {
        return (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY));
    }

    /**
     * Start the video recording using the VIDEO_CAPTURE Intent
     */
    public void startRecording()
    {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    /**
     *
     * @param requestCode VIDEO_CAPTURE request code 101
     * @param resultCode Capture result code; OK, CANCELED
     * @param data Video Data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        videoUri = data.getData();

        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" +
                        videoUri, Toast.LENGTH_LONG).show();
                // TODO: Upload video

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}