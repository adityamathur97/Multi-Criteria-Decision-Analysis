package aditya.com.dba;

import android.app.ActionBar;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

public class DataMatrixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_matrix);

        final int rowNum = DataBasedApproach.getRow();
        final int colNum = DataBasedApproach.getCol();

        TextView[] alternatives = new TextView[rowNum];
        TextView[] criteria = new TextView[colNum];

        final EditText[][] editTexts = new EditText[rowNum][colNum];

        final CheckBox[] optimalSetCheckBox = new CheckBox[colNum];

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);

        //define how many rows and columns to be used in the layout
        gridLayout.setRowCount(rowNum + 2);
        gridLayout.setColumnCount(colNum + 1);

        for (int i = 0; i < rowNum; i++) {
            alternatives[i] = new TextView(this);
            alternatives[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            alternatives[i].setText("Alternative #" + String.valueOf(i+1));
            setPos(alternatives[i], i+1, 0);
            gridLayout.addView(alternatives[i]);
        }

        TextView maxTextView = new TextView(this);
        maxTextView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        maxTextView.setText("Maximize?");
        setPos(maxTextView, rowNum+1, 0);
        gridLayout.addView(maxTextView);

        for (int i = 0; i < colNum; i++) {
            criteria[i] = new TextView(this);
            criteria[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            criteria[i].setText("Criterion #" + String.valueOf(i+1));
            setPos(criteria[i], 0, i+1);
            gridLayout.addView(criteria[i]);
        }

        for (int i = 0; i < colNum; i++) {
            optimalSetCheckBox[i] = new CheckBox(this);
            setPos(optimalSetCheckBox[i], rowNum+1, i+1);
            gridLayout.addView(optimalSetCheckBox[i]);
        }

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                editTexts[i][j] = new EditText(this);
                editTexts[i][j].setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                setPos(editTexts[i][j], i+1, j+1);
                gridLayout.addView(editTexts[i][j]);
            }
        }

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double dataset[][] = new double[rowNum][colNum];
                for(int i = 0; i < rowNum; i++) {
                    for(int j=0; j < colNum; j++) {
                        dataset[i][j] = Double.parseDouble(editTexts[i][j].getText().toString());
                    }
                }
                DataBasedApproach.setDataSet(dataset);

                int optimalset[] = new int[colNum];
                for(int i = 0; i < colNum; i++) {
                    optimalset[i] = optimalSetCheckBox[i].isChecked() ? 1 : 0;
                }
                DataBasedApproach.setOptimalSet(optimalset);

                int result = DataBasedApproach.begin();

                AlertDialog alertDialog = new AlertDialog.Builder(
                        DataMatrixActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Result");

                // Setting Dialog Message
                alertDialog.setMessage("The best alternative is Alternative #" + String.valueOf(result));

                // Showing Alert Message
                alertDialog.show();
            }
        });
    }

    private void setPos(View view, int row, int column) {
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.setGravity(Gravity.CENTER);
        param.rowSpec = GridLayout.spec(row);
        param.columnSpec = GridLayout.spec(column);
        param.setMargins(4, 4, 4, 4);
        view.setLayoutParams(param);
    }
}
