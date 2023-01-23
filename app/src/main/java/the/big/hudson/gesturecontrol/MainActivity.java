package the.big.hudson.gesturecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean gesturesInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().getDecorView().setBackgroundColor(Color.BLUE);

        Spinner gestures = findViewById(R.id.gestures);

        //Create adapter so we can attach our gesture items to the spinner widget
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(
                this, R.array.gestures, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //Set spinner items
        gestures.setAdapter(adapter);

        gestures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //This is called when the activity is loaded so we will just return in that case
                if (!gesturesInitialized) {
                    gesturesInitialized = true;
                    return;
                }

                // Call to ViewActivity with selected gesture
                Intent viewIntent = new Intent(MainActivity.this, ViewActivity.class);
                viewIntent.putExtra("gesture_name", parentView.getItemAtPosition(position).toString());
                viewIntent.putExtra("id", id);
                startActivity(viewIntent);
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
}