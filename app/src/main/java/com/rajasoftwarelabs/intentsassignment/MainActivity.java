package com.rajasoftwarelabs.intentsassignment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // region Constants
    @NonNull private static final String[] EMAIL_ADDRESS = new String[] { "coffee@dummyCoffeeShop.com" };

    private static final int MIN_COFFEES = 1;
    private static final int MAX_COFFEES = 20; // Varies from developer to developer :)
    // endregion

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private int numCoffees = MIN_COFFEES;

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupCameraIntent() {
        final Button takePhotoButton = findViewById(R.id.take_photo_button);

        // Open the camera when the "Take photo" button is clicked.
        takePhotoButton.setOnClickListener(view -> fireCameraIntent());
    }

    private void fireExplicitIntent(@NonNull String name) {
        // TODO (1): Create and fire an explicit intent to open NameActivity
        Intent nameIntent = new Intent(MainActivity.this,NameActivity.class);
        nameIntent.putExtra("name",name);
        startActivity(nameIntent);
    }

    private void fireEmailIntent(@NonNull String name) {
        // Populate the subject and body template strings with the name and number of coffees.
        String subject = "Coffee order for "+name;
        String body = "Coffee order for " + name + " of "+numCoffees + " coffees ";

        // TODO (4): Create and fire an implicit intent to open the email app. The email address to send to is the
        //           EMAIL_ADDRESS constant.
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, EMAIL_ADDRESS);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fireCameraIntent() {
        // TODO (5): Fire an implicit intent to open the camera and get an image as a result from it.
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            setImage(photo);
        }
    }

    // TODO (6): Capture the response from the camera. You can use the setImage method to show the returned thumbnail.

    private void setImage(@NonNull Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.camera_image);
        imageView.setImageBitmap(bitmap);
    }
}