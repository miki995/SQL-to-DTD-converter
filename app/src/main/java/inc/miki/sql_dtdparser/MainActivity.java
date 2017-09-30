package inc.miki.sql_dtdparser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView inputText;
    TextView convertedText;
    Button convertButton;
    Button clearButton;
    TextView databaseText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseText = findViewById(R.id.databaseText);
        inputText = findViewById(R.id.inputText);
        convertedText = findViewById(R.id.convertedText);
        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.GONE);

        initializeData();

    }

    public void convertData(View view) {
        String pcData = " (#PCDATA)>";
        String tableName = "";
        String xmlSigning = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + "<!DOCTYPE ";
        String element = "<!ELEMENT ";
        String attribute = "<!ATTLIST ";
        String newLine = "\n";
        String id = " ID #REQUIRED";
        String idref = " IDREF #REQUIRED";
        String end = "]>";


        String dataBaseName = "";
        if (databaseText.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter name for your database!", Toast.LENGTH_SHORT).show();
        } else {
            dataBaseName = databaseText.getText().toString();

            if (!inputText.getText().toString().equals("")) {
                try {
                    String[] sepCommand = inputText.getText().toString().split(";");

                    String convertedTextDataBaseName = xmlSigning + dataBaseName + "[" + newLine;
                    StringBuilder finalText = new StringBuilder();
                    finalText.append(convertedTextDataBaseName);

                    for (int i = 0; i < sepCommand.length; i++) {
                        String convertedTextPart1 = "";
                        String convertedTextPart2 = "";
                        String convertedTextPart3 = "";

                        String[] sepCreate = sepCommand[i].split("\\(");
                        String[] sep = sepCreate[0].split(" ");
                        tableName = sep[2];
                        String[] sepColumns = sepCreate[1].split(",");
                        String[] columns = new String[sepColumns.length];

                        for (int k = 0; k < sepColumns.length; k++) {
                            String[] sepLine = sepColumns[k].split(" ");

                            if (sepLine.length >= 3) {
                                if (sepLine[2].equals("PRIMARY")) {
                                    columns[k] = sepLine[0].replaceAll("\"", "") + id;
                                } else if (sepLine[2].equals("REFERENCES")) {
                                    columns[k] = sepLine[0].replaceAll("\"", "") + idref;
                                }
                            } else if (sepLine.length == 2) {
                                columns[k] = sepLine[0].replaceAll("\"", "");
                            } else {
                                columns[k] = "Invalid column!";
                            }
                        }
                        String columns1 = "";
                        for (int j = 0; j < columns.length; j++) {
                            String s[] = columns[j].split(" ");
                            if (s.length == 1) {
                                columns1 += s[0] + ",";
                                convertedTextPart2 += newLine + element + columns[j] + pcData;
                            } else {
                                convertedTextPart3 += newLine + attribute + tableName + " " + columns[j] + ">";
                            }
                        }

                        columns1 = columns1.substring(0, columns1.length() - 1);
                        convertedTextPart1 = element + tableName + "*" + "(" + columns1 + ") >";

                        finalText.append(newLine)
                                .append(convertedTextPart1).append(newLine)
                                .append(convertedTextPart2).append(newLine)
                                .append(convertedTextPart3).append(newLine).append(newLine).append(newLine);
                    }


                    finalText.append(end);
                    convertedText.setText(finalText);

                    convertButton = findViewById(R.id.a);
                    convertButton.setVisibility(View.GONE);
                    clearButton = findViewById(R.id.a1);
                    clearButton.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Toast.makeText(this, "Invalid or misspelled SQL commands", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @SuppressLint("SetTextI18n")
    public void initializeData() {
        inputText.setText(
                "CREATE TABLE EMPLOYEE (" +
                        "\"ID\" NUMBER PRIMARY KEY," +
                        "\"PID\" NUMBER REFERENCES GROUPS," +
                        "\"ADDRESS\" VARCHAR2," +
                        "\"NAME\" VARCHAR2," +
                        "\"EMAIL\" VARCHAR2," +
                        "\"DEPARTMENT\" VARCHAR2);" +
                        "CREATE TABLE EMPLOYEE (" +
                        "\"ID\" NUMBER PRIMARY KEY," +
                        "\"PID\" NUMBER REFERENCES GROUPS," +
                        "\"ADDRESS\" VARCHAR2," +
                        "\"NAME\" VARCHAR2," +
                        "\"EMAIL\" VARCHAR2," +
                        "\"DEPARTMENT\" VARCHAR2);" +
                        "CREATE TABLE EMPLOYEE (" +
                        "\"ID\" NUMBER PRIMARY KEY," +
                        "\"PID\" NUMBER REFERENCES GROUPS," +
                        "\"ADDRESS\" VARCHAR2," +
                        "\"NAME\" VARCHAR2," +
                        "\"EMAIL\" VARCHAR2," +
                        "\"DEPARTMENT\" VARCHAR2);"

        );
    }

    public void clearData(View view) {
        convertedText.setText("");
        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.GONE);
        convertButton = findViewById(R.id.a);
        convertButton.setVisibility(View.VISIBLE);

    }
}
