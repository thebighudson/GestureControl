package the.big.hudson.gesturecontrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Activity for recording and uploading videos
 */
public class RecordActivity extends AppCompatActivity {

    /**
     * Video Capture request code
     */
    private static final int VIDEO_CAPTURE = 101;

    /**
     * Uri of local video
     */
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
               uploadVideo();
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
      //  intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    /**
     *
     * @param requestCode VIDEO_CAPTURE request code 101
     * @param resultCode Capture result code; OK, CANCELED
     * @param data Video Data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        videoUri = data.getData();

        // Example Uri - content://media/external/video/media/1000001184
        //Open file and rename
        // Do we have which gesture was selected
        // need to get last instance of / and replace text after that with...
        // [GESTURE NAME]_PRACTICE_[practice number].mp4
        // ex LightOn_PRACTICE_[practice number]

        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" +
                        videoUri, Toast.LENGTH_LONG).show();

                //DO NOTHING
                //Wait for user to select upload or some other action

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Upload video to Firebase
     */
    private void uploadVideo() {
        if (videoUri!= null) {

            //Firebase references
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            //Setup progress dialog
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference videoRef
                    = storageRef.child(videoUri.getPath());

            //Call put on file
            videoRef.putFile(videoUri)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast.makeText(RecordActivity.this,
                                                "Uploaded to Firebase",
                                                Toast.LENGTH_SHORT).show();
                            })

                    .addOnFailureListener(e -> {
                        // Error,
                        progressDialog.dismiss();
                        Toast.makeText(RecordActivity.this,
                                        "Upload Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage((int) progress + "%" + "uploaded");
                            });
        }// End uploadVideo
    }
}