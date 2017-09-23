package inc.miki.sql_dtdparser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     TextView inputText;
     TextView convertedText;
     Button convertButton;
     Button clearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        convertedText = findViewById(R.id.convertedText);
        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.GONE);

        initializeData();

    }

    public void convertData(View view) {
        String pcData = " (#PCDATA)>";
        String xmlSigning = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + "<!DOCTYPE ";
        String element = "<!ELEMENT ";
        String attribute = "<!ATTLIST ";
        String newLine = "\n";
        String id = " ID #REQUIRED";
        String idref = " IDREF #REQUIRED";
        String end = "\"]>\"";

        String[] separatedCommand = inputText.getText().toString().split("\\(");
            String tableName = separatedCommand[0];


            String[] separatedColumns = separatedCommand[1].split(",");
            String[] columns = new String[separatedColumns.length];


            for (int i = 0; i < separatedColumns.length; i++) {
                String[] separatedLine = separatedColumns[i].split(" ");

                if (separatedLine.length >= 3) {
                    if (separatedLine[2].equals("PRIMARY")) {
                        columns[i] = separatedLine[0].replaceAll("\"", "") + id;
                    } else if (separatedLine[2].equals("REFERENCES")) {
                        columns[i] = separatedLine[0].replaceAll("\"", "") + idref;
                    }
                } else if (separatedLine.length == 2) {
                    columns[i] = separatedLine[0].replaceAll("\"", "");
                }
            }

            StringBuilder columns1 = new StringBuilder();
            StringBuilder convertedTextPart2 = new StringBuilder();
            StringBuilder convertedTextPart3 = new StringBuilder();
        for (String column : columns) {
            String s[] = column.split(" ");
            if (s.length == 1) {
                columns1.append(s[0]).append(",");
                convertedTextPart3.append(element).append(column).append(pcData).append(newLine);
            } else {
                convertedTextPart2.append(attribute).append(tableName).append(" ").append(column).append(">").append(newLine);
            }
        }
            columns1 = new StringBuilder(columns1.substring(0, columns1.length() - 1));

            String convertedTextPart1 = xmlSigning + tableName + "[" + newLine +
                    element + tableName + "(" + columns1 + ") >" + newLine;


            String text = convertedTextPart1 + convertedTextPart2 + convertedTextPart3 + end;

            convertedText.setText(text);

        convertButton = findViewById(R.id.a);
        convertButton.setVisibility(View.GONE);
        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.VISIBLE);

    }
    @SuppressLint("SetTextI18n")
    public void initializeData()
    {
        inputText.setText("STUDENTS (\n" +
                "\"ID\" NUMBER PRIMARY KEY,\n" +
                "\"SGROUP\" NUMBER REFERENCES GROUPS,\n" +
                "\"NEPTUNID\" VARCHAR2,\n" +
                "\"NAME\" VARCHAR2,\n" +
                "\"EMAIL\" VARCHAR2,\n" +
                "\"PROGRAM\" VARCHAR2\n");
     }

    public void clearData(View view) {
        convertedText.setText("");
        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.GONE);
        convertButton = findViewById(R.id.a);
        convertButton.setVisibility(View.VISIBLE);

    }
}
