package css.heartrate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextPulse;
    EditText editTextAge;
    EditText editTextDisplay;           // used to display the heart rates from the databas
    // TODO: In Unit 5 will will replace the editText with a RecycleView
    Button buttonInsert;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        editTextAge = findViewById(R.id.editTextAge);
        editTextPulse = findViewById(R.id.editTextPulse);
        editTextDisplay = findViewById(R.id.editTextDisplay);

        setupInsertButton();            // Set up the OnClickListener for the insert button
        setupLiveDataObserver();
    }

    private void setupLiveDataObserver() {
        // Create the observer for the list of heart rates
        mainViewModel.getAllHeartrates().observe(this, new Observer<List<Heartrate>>() {
            @Override
            public void onChanged(@Nullable List<Heartrate> allHeartrates) {
                Log.d("CIS 3334", "MainActivity -- LiveData Observer -- Number of Pizzas = "+allHeartrates.size());
                editTextDisplay.setText("Number of heartrates = "+allHeartrates.size());
                // TODO: update the RecycleView Array Adapter
            }
        });
    }

    /**
     *  Set up the Insert Heartrate button so it adds a new heart rate reading to the database
     */
    private void setupInsertButton() {
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pulse = Integer.parseInt(editTextPulse.getText().toString());
                Integer age = Integer.parseInt(editTextAge.getText().toString());
                mainViewModel.insert(pulse, age);
            }
        });
    }
}