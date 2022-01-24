package com.rajasoftwarelabs.intentsassignment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

// TODO (3): Add back navigation to the Toolbar. You might find some hints in the reading material provided in the
//           training document.
public class NameActivity extends AppCompatActivity {

    TextView message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO (2): Get the name in the intent sent from MainActivity
        String name = null;
        TextView nameTextView = findViewById(R.id.name_text_view);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        nameTextView.setText("Hello "+name);
    }
}
