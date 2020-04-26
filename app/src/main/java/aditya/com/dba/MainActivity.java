package aditya.com.dba;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import static aditya.com.dba.R.id.alternatives;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText alternativesEditText = (EditText) findViewById(alternatives);
                String alt_string = alternativesEditText.getText().toString();
                int alternatives;
                if (alt_string.equals("") || Integer.parseInt(alt_string) <= 0) {
                    alternativesEditText.setError("Please fill valid input");
                    return;
                } else {
                    alternatives = Integer.parseInt(alt_string);
                }
                EditText criteriaEditText = (EditText) findViewById(R.id.criteria);
                String cri_string = criteriaEditText.getText().toString();
                int criteria;
                if(cri_string.equals("") || Integer.parseInt(cri_string) <= 0) {
                    criteriaEditText.setError("Please fill valid input");
                    return;
                } else {
                    criteria = Integer.parseInt(cri_string);
                }

                DataBasedApproach.initialize(alternatives, criteria);

                Intent intent = new Intent(getApplicationContext(), ComparisionMatrixActivity.class);
                startActivity(intent);
            }
        });
    }
}
