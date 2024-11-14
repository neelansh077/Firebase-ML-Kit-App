//package com.example.project;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.text.Text;
//import com.google.mlkit.vision.text.TextRecognition;
//import com.google.mlkit.vision.text.TextRecognizer;
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
//
//
//public class MainActivity3 extends AppCompatActivity {
//
//    private static final String TAG = "My Tag";
//    TextView recognisedTxt;
//    Button choosePic;
//
//     private static final int STORAGE_PERMISSION_CODE = 113;
//
//     ActivityResultLauncher<Intent> intentActivityResultLauncher;
//
//     InputImage inputImage;
//     TextRecognizer textRecognizer;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//         recognisedTxt = findViewById(R.id.recognisedTxt);
//         choosePic = findViewById(R.id.choosePic);
//
//         textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
//
//        intentActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        Intent data = result.getData();
//                        Uri imageUri = data.getData();
//
//                        convertImageToText(imageUri);
//                    }
//
//                }
//        );
//
//         choosePic.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent intent = new Intent();
//                 intent.setType("image/*");
//                 intent.setAction(Intent.ACTION_GET_CONTENT);
//                 startActivityForResult();
//                 intentActivityResultLauncher.launch(intent);
//             }
//         });
//    }
//
//    private void startActivityForResult() {
//    }
//
//    private void convertImageToText(Uri imageUri) {
//
//        try {
//            inputImage = InputImage.fromFilePath(getApplicationContext(),imageUri);
//
//            // get text from input image
//            Task<Text> result = textRecognizer.process(inputImage)
//                    .addOnSuccessListener(new OnSuccessListener<Text>() {
//                        @Override
//                        public void onSuccess(Text text) {
//                            recognisedTxt.setText(text.getText());
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            recognisedTxt.setText("Error: "+e.getMessage());
//                            Log.d(TAG, "Error: "+e.getMessage());
//                        }
//                    });
//        }catch (Exception e){
//            Log.d(TAG, "convertImageToText: Error : " +e.getMessage());
//        }
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        checkPermission(Manifest.permission.MANAGE_DEVICE_POLICY_RUNTIME_PERMISSIONS, STORAGE_PERMISSION_CODE);
//    }
//
//    public void checkPermission(String permission, int requestCode){
//        if(ContextCompat.checkSelfPermission(MainActivity3.this, permission) == PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(MainActivity3.this, new String[]{permission}, requestCode);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if(requestCode == STORAGE_PERMISSION_CODE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}

package com.example.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = "My Tag";
    TextView recognisedTxt;
    Button choosePic;

    private static final int STORAGE_PERMISSION_CODE = 113;

    ActivityResultLauncher<Intent> intentActivityResultLauncher;

    InputImage inputImage;
    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recognisedTxt = findViewById(R.id.recognisedTxt);
        choosePic = findViewById(R.id.choosePic);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        // Check storage permission once when the activity is created
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            if (imageUri != null) {
                                convertImageToText(imageUri);
                            } else {
                                Toast.makeText(MainActivity3.this, "Error: Image not selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intentActivityResultLauncher.launch(intent);
            }
        });
    }

    private void convertImageToText(Uri imageUri) {
        try {
            inputImage = InputImage.fromFilePath(getApplicationContext(), imageUri);

            // Get text from input image
            Task<Text> result = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            recognisedTxt.setText(text.getText());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            recognisedTxt.setText("Error: " + e.getMessage());
                            Log.d(TAG, "Error: " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "convertImageToText: Error : " + e.getMessage());
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity3.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Only request permission if not permanently denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "Storage permission is required for text recognition", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(MainActivity3.this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
