package com.example.project;

import static com.example.project.R.id.imageView2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabelerOptionsBase;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;

public class MainActivity5 extends AppCompatActivity {

    private static final String TAG = "MyTag";
    ImageView imageView2;
    TextView textView4;
    Button button3;

    private  static final int CAMERA_PERMISSION_CODE = 223;
    private  static final int READ_STORAGE_PERMISSION_CODE = 144;
    private  static final int WRITE_STORAGE_PERMISSION_CODE = 144;

    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;

    InputImage inputImage;

    ImageLabeler labeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        imageView2 = findViewById(R.id.imageView2);
        button3 = findViewById(R.id.button3);
        textView4 = findViewById(R.id.textView4);

        labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        try {
                            Bitmap photo = (Bitmap) data.getExtras().get("data");
                            imageView2.setImageBitmap(photo);
                            inputImage = InputImage.fromBitmap(photo, 0);

                            processImage();
                        } catch (Exception e){
                            Log.d(TAG, "onActivityResult: " +e.getMessage());
                        }
                    }
                }
        );
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        try {
                            inputImage = InputImage.fromFilePath(MainActivity5.this, data.getData());
                            imageView2.setImageURI(data.getData());
                            processImage();
                        } catch (Exception e){
                            Log.d(TAG, "onActivityResult: " +e.getMessage());
                        }
                    }
                }
        );

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String [] options= {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity5.this);
                builder.setTitle("Pick an option");
                builder.setItems(options, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            cameraLauncher.launch(cameraIntent);

                        }else {
                            Intent storageIntent = new Intent();
                            storageIntent.setType("image/*");
                            storageIntent.setAction(Intent.ACTION_GET_CONTENT);
                            galleryLauncher.launch(storageIntent);
                        }
                    }
                });
                builder.show();
            }
        });

    }

    private void processImage() {
        labeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        String result = "";
                        for (ImageLabel label : imageLabels){
                            result=result+ "\n" + label.getText();
                        }
                        textView4.setText(result);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.getMessage());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);

    }

    public void checkPermission(String permission, int requestCode) {

        //Checking if permission is granted or not
        if(ContextCompat.checkSelfPermission(MainActivity5.this,permission) == PackageManager.PERMISSION_DENIED){
            // Take Permission
            ActivityCompat.requestPermissions(MainActivity5.this, new String[]{permission},requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERMISSION_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            } else {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_PERMISSION_CODE);
            } // READ_EXTERNAL_STORAGE Should be there instead of DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION
        } else if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            } else {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_STORAGE_PERMISSION_CODE);
            } // WRITE_EXTERNAL_STORAGE Should be there instead of DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION
        } else if (requestCode == WRITE_STORAGE_PERMISSION_CODE){
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


