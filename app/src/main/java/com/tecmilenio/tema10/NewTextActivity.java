package com.tecmilenio.tema10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class NewTextActivity extends AppCompatActivity {

    private EditText editText, editTextPerson;
    private Button addButton, getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_text);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = (EditText) findViewById(R.id.edit_text);
        editTextPerson = (EditText) findViewById(R.id.edit_text_person);
        addButton = (Button) findViewById(R.id.add_button);
        getButton = (Button) findViewById(R.id.get_button);

        addButton.setOnClickListener(new View.OnClickListener(){    @Override    public void onClick(View view){        String texto = editText.getText().toString();        String textoPerson = editTextPerson.getText().toString();        if (texto.isEmpty()) {            editText.setText("Favor de llenar los datos");            return;        }        if (textoPerson.isEmpty()) {            editTextPerson.setText("Favor de llenar los datos");            return;        }        addTextToDB(texto,textoPerson);    }});

        /*getButton.setOnClickListener( v -> {


        });*/

    }

    public void addTextToDB(String texto, String textoPerson) {
        HashMap<String, Object> textHashMap = new HashMap<>();
        textHashMap.put("text",texto);
        textHashMap.put("textPerson",textoPerson);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference textoRef = database.getReference("textos");

        String key = textoRef.push().getKey();
        textHashMap.put("key", key);

        textoRef.child(key).setValue(textHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(NewTextActivity.this, "Añadido", Toast.LENGTH_SHORT).show();
                editText.getText().clear();
                editTextPerson.getText().clear();
            }
        });
    }
}