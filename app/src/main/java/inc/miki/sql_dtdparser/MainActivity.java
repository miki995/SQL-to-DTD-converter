package inc.miki.sql_dtdparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     TextView textView1;
     TextView textView2;
     TextView textView3;
     TextView textView4;
     TextView textView5;
     TextView textView6;
     TextView textView7;
    TextView textView8;
    TextView textParsedd;
    Button parseButton;
    Button clearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textParsedd= findViewById(R.id.textParsed);

         clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.GONE);
        initializeData();
    }

    public void convertData(View view) {
        String a1 = textView1.getText().toString();
        String a2 = textView2.getText().toString();
        String a3 = textView3.getText().toString();
        String a4 = textView4.getText().toString();
        String a5 = textView5.getText().toString();
        String a6 = textView6.getText().toString();
        String a7 = textView7.getText().toString();

        String tableName = a1.substring(12,20);
        String id = a2.substring(1,3);
        String sgroup = a3.substring(1,7);
        String neptun = a4.substring(1,9);
        String name = a5.substring(1,5);
        String email = a6.substring(1,6);
        String program = a7.substring(1,8);
        String pcData = " (#PCDATA)>";
        String xmlSigning = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String element = "<!ELEMENT ";
        String attribute = "<!ATTLIST ";
        String newLine = "\n";

        String convertedText = xmlSigning + newLine
                + "<!DOCTYPE " + tableName  +  "[" + newLine
                + element   + tableName  +  "(" + neptun + "?, " + name + "+, " + email + "?, " + program + "+) >"  + newLine
                + attribute + tableName  + " " +  id + " ID #REQUIRED >" + newLine
                + attribute + tableName  +  " " + sgroup +  " IDREF #REQUIRED >" + newLine
                + element   + neptun     +  pcData + newLine
                + element   + name       +  pcData + newLine
                + element   + email      +  pcData + newLine
                + element   + program    +  pcData + newLine
                + "]>";



        textParsedd.setText(convertedText);

        parseButton = findViewById(R.id.a);
        parseButton.setVisibility(View.GONE);
        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.VISIBLE);

    }
    public void initializeData()
    {
        textView1.setText("CREATE TABLE Students (");
        textView2.setText("\"id\" NUMBER(*,0) PRIMARY KEY,");
        textView3.setText("\"sgroup\" NUMBER(*,0) REFERENCES GROUPS");
        textView4.setText("\"neptunid\" VARCHAR2(255 BYTE) ,");
        textView5.setText("\"name\" VARCHAR2(255 BYTE) ,");
        textView6.setText("\"email\" VARCHAR2(255 BYTE) ,");
        textView7.setText("\"program\" VARCHAR2(255 BYTE)");
        textView8.setText(");");
    }

    public void clearData(View view) {
        textParsedd.setText("");

        clearButton = findViewById(R.id.a1);
        clearButton.setVisibility(View.GONE);
        parseButton = findViewById(R.id.a);
        parseButton.setVisibility(View.VISIBLE);

    }
}
