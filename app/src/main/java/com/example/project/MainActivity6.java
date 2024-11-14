//package com.example.project;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import android.Manifest;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//public class MainActivity6 extends AppCompatActivity {
//
//    // Permission codes
//    private static final int CAMERA_PERMISSION_CODE = 223;
//    private static final int READ_STORAGE_PERMISSION_CODE = 144;
//    private static final int WRITE_STORAGE_PERMISSION_CODE = 145;
//
//    Button btScanBarcode;
//    ImageView ivQrCode;
//    TextView tvResult;
//
//    // ActivityResultLauncher for Camera and Gallery
//    private final ActivityResultLauncher<Intent> cameraLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    // Handle the camera image result
//                    Uri imageUri = result.getData().getData();
//                    processImageQRCode(imageUri);
//                }
//            });
//
//    private final ActivityResultLauncher<Intent> galleryLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    // Handle the gallery image result
//                    Uri selectedImage = result.getData().getData();
//                    processImageQRCode(selectedImage);
//                }
//            });
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btScanBarcode = findViewById(R.id.btnScanBarcode);
//        ivQrCode = findViewById(R.id.ivQrCode);
//        tvResult = findViewById(R.id.tvResult);
//
//        btScanBarcode.setOnClickListener(v -> showCameraOrGalleryDialog());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();  // Calls the parent class' onResume()
//
//        // Check camera and storage permissions after the super.onResume() call
//        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
//        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_PERMISSION_CODE);
//        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_STORAGE_PERMISSION_CODE);
//    }
//
//    // Generic method to check and request a permission
//    public void checkPermission(String permission, int requestCode) {
//        if (ContextCompat.checkSelfPermission(MainActivity6.this, permission) == PackageManager.PERMISSION_DENIED) {
//            // Request the permission
//            ActivityCompat.requestPermissions(MainActivity6.this, new String[]{permission}, requestCode);
//        } else {
//            // Permission already granted
//            Toast.makeText(MainActivity6.this, permission + " already granted", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                Toast.makeText(MainActivity6.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
//            } else {
//                // Proceed with camera logic here
//                Toast.makeText(MainActivity6.this, "Camera permission granted", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == READ_STORAGE_PERMISSION_CODE) {
//            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                Toast.makeText(MainActivity6.this, "Read storage permission denied", Toast.LENGTH_SHORT).show();
//            } else {
//                // Handle read storage logic here
//                Toast.makeText(MainActivity6.this, "Read storage permission granted", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == WRITE_STORAGE_PERMISSION_CODE) {
//            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                Toast.makeText(MainActivity6.this, "Write storage permission denied", Toast.LENGTH_SHORT).show();
//            } else {
//                // Handle write storage logic here
//                Toast.makeText(MainActivity6.this, "Write storage permission granted", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Show dialog to choose between Camera or Gallery
//    private void showCameraOrGalleryDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Option")
//                .setMessage("Would you like to scan a barcode with the Camera or choose from the Gallery?")
//                .setPositiveButton("Camera", (dialog, which) -> openCamera())
//                .setNegativeButton("Gallery", (dialog, which) -> openGallery())
//                .show();
//    }
//
//    // Open camera to scan barcode
//    private void openCamera() {
//        // Check if Camera permission is granted
//        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
//
//        // Launch camera intent (this is just a simple example to open the camera)
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraLauncher.launch(intent);
//    }
//
//    // Open gallery to choose an image
//    private void openGallery() {
//        // Check if Storage permissions are granted
//        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_PERMISSION_CODE);
//        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_STORAGE_PERMISSION_CODE);
//
//        // Launch gallery intent
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        galleryLauncher.launch(intent);
//    }
//
//    // Process the image for QR code and barcode detection using Firebase ML Kit
//    private void processImageQRCode(Uri imageUri) {
//        try {
//            if (imageUri != null) {
//                // Convert URI to Bitmap
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
//
//                // Initialize barcode detector with options to detect both QR codes and barcodes
//                FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
//                        .setBarcodeFormats(
//                                FirebaseVisionBarcode.FORMAT_QR_CODE |
//                                        FirebaseVisionBarcode.FORMAT_UPC_A |
//                                        FirebaseVisionBarcode.FORMAT_UPC_E |
//                                        FirebaseVisionBarcode.FORMAT_EAN_13 |
//                                        FirebaseVisionBarcode.FORMAT_EAN_8 |
//                                        FirebaseVisionBarcode.FORMAT_CODE_128 |
//                                        FirebaseVisionBarcode.FORMAT_CODE_39 |
//                                        FirebaseVisionBarcode.FORMAT_CODE_93 |
//                                        FirebaseVisionBarcode.FORMAT_ITF
//                        )
//                        .build();
//
//                FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
//
//                // Detect barcode in the image
//                detector.processImage(firebaseVisionImage)
//                        .addOnSuccessListener(barcodes -> {
//                            if (!barcodes.isEmpty()) {
//                                StringBuilder barcodeResult = new StringBuilder();
//                                for (FirebaseVisionBarcode barcode : barcodes) {
//                                    String value = barcode.getDisplayValue();
//                                    int valueType = barcode.getValueType();
//                                    switch (valueType) {
//                                        case FirebaseVisionBarcode.TYPE_TEXT:
//                                            barcodeResult.append("Text: ").append(value).append("\n");
//                                            break;
//                                        case FirebaseVisionBarcode.TYPE_URL:
//                                            barcodeResult.append("URL: ").append(value).append("\n");
//                                            break;
//                                        case FirebaseVisionBarcode.TYPE_PRODUCT:
//                                            barcodeResult.append("Product: ").append(value).append("\n");
//                                            break;
//                                        case FirebaseVisionBarcode.TYPE_CONTACT_INFO:
//                                            barcodeResult.append("Contact Info: ").append(value).append("\n");
//                                            break;
//                                        // Add more types if needed
//                                        default:
//                                            barcodeResult.append("Unknown: ").append(value).append("\n");
//                                    }
//                                }
//                                tvResult.setText(barcodeResult.toString());
//                                Toast.makeText(MainActivity.this, "Barcode detected", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(MainActivity.this, "No Barcode or QR Code found", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(e -> {
//                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "Error detecting Barcode or QR Code", Toast.LENGTH_SHORT).show();
//                        });
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "Error reading image", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
