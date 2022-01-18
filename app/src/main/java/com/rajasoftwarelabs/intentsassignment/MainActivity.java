package com.rajasoftwarelabs.intentsassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // region Constants
    @NonNull private static final String[] EMAIL_ADDRESS = new String[] { "coffee@dummyCoffeeShop.com" };

    private static final int MIN_COFFEES = 1;
    private static final int MAX_COFFEES = 20; // Varies from developer to developer :)
    // endregion

    private int numCoffees = MIN_COFFEES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupExplicitIntent();
        setupEmailIntent();
        setupCameraIntent();
    }

    private void setupExplicitIntent() {
        // Get the Button and EditText with the respective ids using findViewById from the layout to manipulate them
        // programmatically.
        final Button explicitIntentButton = findViewById(R.id.explicit_intent_button);
        final EditText nameEditText = findViewById(R.id.name_edit_text);

        // Set an OnClickListener on the Button. The onClick method of this anonymous object will be called when the
        // button is clicked.
        explicitIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                // Get the name from the EditText. If it is empty, show an error message. Otherwise, navigate to
                // NameActivity.
                String name = nameEditText.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    String errorMessage = getString(R.string.name_not_entered_error_message);
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    fireExplicitIntent(name);
                }
            }
        });
    }

    private void setupEmailIntent() {
        // Initialise the different views from the UI using findViewById.
        final EditText nameEditText = findViewById(R.id.coffee_name_edit_text);
        final Button plusButton = findViewById(R.id.plus_button);
        final Button minusButton = findViewById(R.id.minus_button);
        final TextView numCoffeesText = findViewById(R.id.num_coffees_text);
        final Button sendEmailButton = findViewById(R.id.send_email_button);

        // Show the number of coffees
        numCoffeesText.setText(String.valueOf(numCoffees));

        // Set an OnClickListener on the "+" and "-" buttons to increment and decrement the number of coffees.
        // Notice the use of lambdas instead of anonymous objects for conciseness.
        plusButton.setOnClickListener(view -> {
            if (numCoffees < MAX_COFFEES) {
                numCoffees++;
                numCoffeesText.setText(String.valueOf(numCoffees));
            }
        });

        minusButton.setOnClickListener(view -> {
            if (numCoffees > MIN_COFFEES) {
                numCoffees--;
                numCoffeesText.setText(String.valueOf(numCoffees));
            }
        });

        sendEmailButton.setOnClickListener(view -> {
            // Get the name from the EditText. If it is empty, show an error message. Otherwise, navigate to the email
            // app.
            String name = nameEditText.getText().toString();
            if (TextUtils.isEmpty(name)) {
                String errorMessage = getString(R.string.name_not_entered_error_message);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                fireEmailIntent(name);
            }
        });
    }

    private void setupCameraIntent() {
        final Button takePhotoButton = findViewById(R.id.take_photo_button);

        // Open the camera when the "Take photo" button is clicked.
        takePhotoButton.setOnClickListener(view -> fireCameraIntent());
    }

    private void fireExplicitIntent(@NonNull String name) {
        // TODO (1): Create and fire an explicit intent to open NameActivity
    }

    private void fireEmailIntent(@NonNull String name) {
        // Populate the subject and body template strings with the name and number of coffees.
        String subject = getString(R.string.email_subject, name);
        String body = getResources().getQuantityString(R.plurals.email_body, numCoffees, numCoffees, name);

        // TODO (4): Create and fire an implicit intent to open the email app. The email address to send to is the
        //           EMAIL_ADDRESS constant.
    }

    private void fireCameraIntent() {
        // TODO (5): Fire an implicit intent to open the camera and get an image as a result from it.
    }

    // TODO (6): Capture the response from the camera. You can use the setImage method to show the returned thumbnail.

    private void setImage(@NonNull Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.camera_image);
        imageView.setImageBitmap(bitmap);
    }
}