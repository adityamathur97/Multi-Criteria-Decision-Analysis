package aditya.com.dba;

/**
 * Created by Aditya Mathur on 06-Oct-17.
 */
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

public class ComparisionMatrixActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparision_matrix);

        final int rowNum = DataBasedApproach.getRow();
        final int colNum = DataBasedApproach.getCol();


        TextView[][] criteria = new TextView[2][colNum];

        final EditText[][] editTexts = new EditText[colNum][colNum];


        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout2);

        //define how many rows and columns to be used in the layout
        gridLayout.setRowCount(rowNum + 1);
        gridLayout.setColumnCount(colNum + 1);

        for (int i = 0; i < colNum; i++) {
            criteria[0][i] = new TextView(this);
            criteria[0][i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            criteria[0][i].setText("Criteria #" + String.valueOf(i+1));
            setPos(criteria[0][i], i+1, 0);
            gridLayout.addView(criteria[0][i]);
        }

        for (int i = 0; i < colNum; i++) {
            criteria[1][i] = new TextView(this);
            criteria[1][i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            criteria[1][i].setText("Criterion #" + String.valueOf(i+1));
            setPos(criteria[1][i], 0, i+1);
            gridLayout.addView(criteria[1][i]);
        }

        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < colNum; j++) {
                editTexts[i][j] = new EditText(this);
                editTexts[i][j].setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                setPos(editTexts[i][j], i+1, j+1);
                gridLayout.addView(editTexts[i][j]);
            }
        }

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double comparisionMatrix[][] = new double[colNum][colNum];
                for(int i = 0; i < colNum; i++) {
                    for(int j=0; j < colNum; j++) {
                        if(i<j)
                            comparisionMatrix[i][j] = Double.parseDouble(editTexts[i][j].getText().toString());
                        else if(i==j)
                            comparisionMatrix[i][j]=1;
                        else
                            comparisionMatrix[i][i]=comparisionMatrix[j][i];

                    }
                }
                DataBasedApproach.setComparisionMatrix(comparisionMatrix);

                Intent intent2 = new Intent(getApplicationContext(), DataMatrixActivity.class);
                startActivity(intent2);
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
