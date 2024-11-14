package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity4 extends AppCompatActivity {
    String[] appNames = {"Detect Language", "Translate Text", "Extract Text from Image", "Image/Object Detection", "Smart Reply" };
    int[] appImages = {R.drawable.baseline_content_paste_search_24,R.drawable.baseline_translate_24,R.drawable.baseline_image_search_24,R.drawable.img, R.drawable.baseline_qr_code_scanner_24};

    ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        listView = findViewById(R.id.listView);

        appAdapter appAdapter = new appAdapter(getApplicationContext(),appNames,appImages);
        listView.setAdapter(appAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity4.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity4.this, MainActivity2.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity4.this, MainActivity3.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity4.this, MainActivity5.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity4.this, MainActivity7.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}