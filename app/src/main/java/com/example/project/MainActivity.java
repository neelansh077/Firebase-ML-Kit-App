package com.example.project;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.IdentifiedLanguage;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

public class MainActivity extends AppCompatActivity {
    EditText langString;
    Button button;
    TextView result;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        langString = findViewById(R.id.langString);
        button = findViewById(R.id.button);
        result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String langText = langString.getText().toString();
                if(langText.equals("")){
                    Toast.makeText(MainActivity.this, "Enter Text", Toast.LENGTH_SHORT).show();
                }else {
                    detectLang(langText);
                }
            }
        });
    }

    private void detectLang(String langText){
        LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(langText)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String languageCode) {
                        if(languageCode.equals("und")){
                            result.setText("Cant identify language");
                        }else{
                            result.setText("Language is "+languageCode);
                        }
                        IdentifiedLanguage[] identifiedLanguages = new IdentifiedLanguage[0];
                        for (IdentifiedLanguage identifiedLanguage : identifiedLanguages) {
                            String language = identifiedLanguage.getLanguageTag();
                            float confidence = identifiedLanguage.getConfidence();
                            Log.i(TAG, language + " (" + confidence + ")");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.setText("Execption"+e.getMessage());
                    }
                });
    }
}


