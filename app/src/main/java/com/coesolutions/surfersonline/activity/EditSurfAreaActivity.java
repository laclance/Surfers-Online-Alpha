package com.coesolutions.surfersonline.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coesolutions.surfersonline.R;

public class EditSurfAreaActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_surf_area);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notabs);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.addSurfArea);
            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        nameEditText = (EditText) findViewById(R.id.name);
        descriptionEditText = (EditText) findViewById(R.id.description);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);

        if (id != 0)
        {
            if (ab != null)
                ab.setTitle(R.string.editSurfArea);
            final Button button = (Button)findViewById(R.id.submit);
            button.setText(R.string.editSurfArea);
            nameEditText.setText(intent.getStringExtra("NAME"));
            descriptionEditText.setText(intent.getStringExtra("DESCRIPTION"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_surf_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                if (!getSupportFragmentManager().popBackStackImmediate()) {
                    supportFinishAfterTransition();
                }
                return true;
            case R.id.action_settings :
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view){
        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameEditText.requestFocus();
            nameEditText.setError("Please enter a name");
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("ID", id);
            intent.putExtra("NAME", name);
            intent.putExtra("DESCRIPTION", descriptionEditText.getText().toString().trim());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
