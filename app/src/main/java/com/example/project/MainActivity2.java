package com.example.project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
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
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private EditText editText;
    private TextView textView6;
    private MaterialButton sourceLangChooseBtn;
    private MaterialButton finalLangChooseBtn;
    private MaterialButton translateBtn;

    //Translator options to set source and final languages, English -> Urdu
    private TranslatorOptions translatorOptions;
    //Translator object, for configuring it with the source and the target languages
    private Translator translator;

    private ProgressDialog progressDialog;

    private ArrayList<ModelLanguage>languageArrayList;

    private static final String TAG = "MyTag";

    private String sourceLanguageCode = "en";
    private String sourceLanguageTitle = "English";
    private String finalLanguageCode = "ur";
    private String finalLanguageTitle = "Urdu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText = findViewById(R.id.editText);
        textView6 = findViewById(R.id.textView6);
        sourceLangChooseBtn = findViewById(R.id.sourceLangChooseBtn);
        finalLangChooseBtn = findViewById(R.id.finalLangChooseBtn);
        translateBtn = findViewById(R.id.translateBtn);

        //initiate setup process dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadAvailableLanguages();

        sourceLangChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceLanguageChoose();
            }
        });

        finalLangChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalLangChoose();
            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDate();
            }
        });
    }

    private String sourceLanguageText = "";
    private void validateDate() {
        //input text to be translated
        sourceLanguageText = editText.getText().toString().trim();
        //show logs
        Log.d(TAG, "validateDate: sourceLanguageText: "+sourceLanguageText);

        //validate data, if empty show error message, otherwise start translation
        if (sourceLanguageText.isEmpty()){
            Toast.makeText(this, "Enter text to translate....", Toast.LENGTH_SHORT).show();
        } else {
            startTranslations();
        }
    }

    private void startTranslations() {
        //set progress message and show
        progressDialog.setMessage("Processing language model...");
        progressDialog.show();

        //Initiate TranslatorOptions with source and target lang eg. end and ur
        translatorOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(finalLanguageCode)
                .build();
        translator = Translation.getClient(translatorOptions);

        //init DownloadConditions with option to require Wifi (optional)
        DownloadConditions downloadConditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        //start downloading translation model if required (will download at 1st time)
        translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //translation model ready to be translated
                        Log.d(TAG, "onSuccess: model ready, staring to translate");
                        //change progress message to translating
                        progressDialog.setMessage("Translating....");

                        //start translation process
                        translator.translate(sourceLanguageText)
                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(String translatedText) {
                                        //translated successfully
                                        Log.d(TAG, "onSuccess: translatedText: "+translatedText);
                                        progressDialog.dismiss();
                                        textView6.setText(translatedText);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //failed to translate
                                        progressDialog.dismiss();
                                        Log.d(TAG, "onFailure: ",e);
                                        Toast.makeText(MainActivity2.this, "Failed to Translate due to "+e.getMessage()+" please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to ready translation model, cant proceed to translation
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: ",e);
                        Toast.makeText(MainActivity2.this, "Failed to ready model due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void sourceLanguageChoose(){

        //init PopUpMenu param 1 is context, param 2 is the UI view around which to show the popup menu, to choose source lang from list
        PopupMenu popupMenu = new PopupMenu(this, sourceLangChooseBtn);

        //from languageArrayList we will display lang titles
        for (int i = 0; i<languageArrayList.size(); i++){

            //keep adding titles in popup menu item: param 1 is groupId, param 2 is itemId, param 3 is order, param 4 is title
            popupMenu.getMenu().add(Menu.NONE, i, i, languageArrayList.get(i).languageTitle);
        }

        //show popup menu
        popupMenu.show();

        //handle popup menu item click
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //get clicked item id which is position / index from the list
                int position = item.getItemId();

                //get code and title of the lang selected
                sourceLanguageCode = languageArrayList.get(position).languageCode;
                sourceLanguageTitle = languageArrayList.get(position).languageTitle;
                //set the selected lang to sourceLangChooseBtn as the text and editText as hint
                sourceLangChooseBtn.setText(sourceLanguageTitle);
                editText.setHint("Enter"+sourceLanguageTitle);

                //show in logs
                Log.d(TAG, "onMenuItemClick: sourceLanguageCode: "+ sourceLanguageCode);
                Log.d(TAG, "onMenuItemClick: sourceLanguageTitle: "+ sourceLanguageTitle);

                return false;
            }
        });
    }

    private void finalLangChoose(){
        //initiate PopupMenu param 1 is context, param 2 is thw ui view around which we want to show the popup menu, to choose source lang from list
        PopupMenu popupMenu = new PopupMenu(this, finalLangChooseBtn);

        // from languageArrayList we will display lang titles
        for (int i = 0; i<languageArrayList.size(); i++){

            // keep adding titles in popup menu item: param 1 is groupId, param 2 is itemId, param 3is order, param 4 is title
            popupMenu.getMenu().add(Menu.NONE, i, i, languageArrayList.get(i).getLanguageTitle());
        }

        //show popup menu
        popupMenu.show();

        //handle popup menu item click
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //get clicked item id which is position/index from the list
                int position = item.getItemId();

                //get code and title of the lang selected
                finalLanguageCode = languageArrayList.get(position).languageCode;
                finalLanguageTitle = languageArrayList.get(position).languageTitle;

                //set the selected lang to finalLanguageChooseBtn as text
                finalLangChooseBtn.setText(finalLanguageTitle);

                // show in logs
                Log.d(TAG, "onMenuItemClick: finalLanguageCode: "+finalLanguageCode);
                Log.d(TAG, "onMenuItemClick: finalLanguageTitle: "+finalLanguageTitle);

                return false;
            }
        });
    }

    private void loadAvailableLanguages() {

        //initiate lang array list b4 starting adding data into it
        languageArrayList = new ArrayList<>();

        //get list of all language codes eg. en, ur, ar
        List<String> languageCodeList = TranslateLanguage.getAllLanguages();
        // to make list containing both the lang code eg. en and lang title eg. English
        for(String languageCode: languageCodeList){
            //get lang title from lang code eg en -> English
            String languageTitle = new Locale(languageCode).getDisplayLanguage(); //e.g. en -> English
            //print lang code anf title logs
            Log.d(TAG, "loadAvailableLanguages: languageCode: "+languageCode);
            Log.d(TAG, "loadAvailableLanguages: languageTitle: "+languageTitle);

            //prepare lang model and add in list
            ModelLanguage modelLanguage = new ModelLanguage(languageCode, languageTitle);
            languageArrayList.add(modelLanguage);
        }
    }
}