////package com.example.project;
////
////import android.annotation.SuppressLint;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.TextView;
////
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.google.android.gms.tasks.OnFailureListener;
////import com.google.android.gms.tasks.OnSuccessListener;
////import com.google.mlkit.nl.smartreply.SmartReply;
////import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
////import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
////import com.google.mlkit.nl.smartreply.TextMessage;
////
////import java.util.ArrayList;
////import java.util.List;
////
////public class MainActivity7 extends AppCompatActivity {
////    EditText etSendMessage;
////    Button btnSendMessage;
////    TextView tvReplyMessage;
////
////    List<TextMessage> conversation;
////    String userUID = "123456"; // On production application, this comes from user UID
////    SmartReply smartReplyGenerator;
////
////    @SuppressLint("MissingInflatedId")
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////
////        // Initialize UI elements
////        etSendMessage = findViewById(R.id.etSendMessage);
////        btnSendMessage = findViewById(R.id.btnSendMessage);
////        tvReplyMessage = findViewById(R.id.tvReplyMessage);
////
////        // Initialize conversation list
////        conversation = new ArrayList<>();
////        smartReplyGenerator = SmartReply.getClient();
////
////        // Set button click listener
////        btnSendMessage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String message = etSendMessage.getText().toString().trim();
////                conversation.add(TextMessage.createForRemoteUser(message, System.currentTimeMillis(), userUID));
////
////                smartReplyGenerator.suggestReplies(conversation)
////                        .addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
////                            @Override
////                            public void onSuccess(SmartReplySuggestionResult smartReplySuggestionResult) {
////                                if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
////                                    // The conversation's language isn't supported, so
////                                    // the result doesn't contain any suggestions.
////                                    tvReplyMessage.setText("No Reply");
////                                } else if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
////                                    String reply = "";
////                                    for (SmartReplySuggestion suggestion : smartReplySuggestionResult.getSuggestions()) {
////                                        reply = reply + suggestion.getText() + "\n";
////                                    }
////                                    tvReplyMessage.setText(reply);
////                                }
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                tvReplyMessage.setText("Error: " + e.getMessage());
////                            }
////                        });
////            }
////        });
////    }
////}
//
//package com.example.project;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.mlkit.nl.smartreply.SmartReply;
//import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
//import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
//import com.google.mlkit.nl.smartreply.TextMessage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity7 extends AppCompatActivity {
//    EditText etSendMessage;
//    Button btnSendMessage;
//    TextView tvReplyMessage;
//
//    List<TextMessage> conversation;
//    String userUID = "123456"; // In a production application, this would come from the user UID
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize UI elements
//        etSendMessage = findViewById(R.id.etSendMessage);
//        btnSendMessage = findViewById(R.id.btnSendMessage);
//        tvReplyMessage = findViewById(R.id.tvReplyMessage);
//
//        // Initialize conversation list
//        conversation = new ArrayList<>();
//
//        // Set button click listener
//        btnSendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = etSendMessage.getText().toString().trim();
//                if (!message.isEmpty()) {
//                    conversation.add(TextMessage.createForRemoteUser(message, System.currentTimeMillis(), userUID));
//
//                    SmartReply.getClient().suggestReplies(conversation)
//                            .addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
//                                @Override
//                                public void onSuccess(SmartReplySuggestionResult smartReplySuggestionResult) {
//                                    if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
//                                        tvReplyMessage.setText("No Reply - Unsupported Language");
//                                    } else if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
//                                        StringBuilder replyBuilder = new StringBuilder();
//                                        for (SmartReplySuggestion suggestion : smartReplySuggestionResult.getSuggestions()) {
//                                            replyBuilder.append(suggestion.getText()).append("\n");
//                                        }
//                                        tvReplyMessage.setText(replyBuilder.toString().trim());
//                                    }
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    tvReplyMessage.setText("Error: " + e.getMessage());
//                                }
//                            });
//                }
//            }
//        });
//    }
//}

package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity7 extends AppCompatActivity {
    private static final String TAG = "My Tag";

    EditText etSendMessage;
    Button btnSendMessage;
    TextView tvReplyMessage;

    List<TextMessage> conversation;
    String userUID = "123456"; // Simulated UID for testing

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Initialize UI elements
            etSendMessage = findViewById(R.id.etSendMessage);
            btnSendMessage = findViewById(R.id.btnSendMessage);
            tvReplyMessage = findViewById(R.id.tvReplyMessage);

            // Ensure UI elements are not null
            if (etSendMessage == null || btnSendMessage == null || tvReplyMessage == null) {
                throw new NullPointerException("One or more UI elements are not initialized properly.");
            }

            // Initialize conversation list
            conversation = new ArrayList<>();

            // Set button click listener
            btnSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = etSendMessage.getText().toString().trim();
                    if (!message.isEmpty()) {
                        conversation.add(TextMessage.createForRemoteUser(message, System.currentTimeMillis(), userUID));

                        SmartReply.getClient().suggestReplies(conversation)
                                .addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
                                    @Override
                                    public void onSuccess(SmartReplySuggestionResult smartReplySuggestionResult) {
                                        if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                                            tvReplyMessage.setText("No Reply - Unsupported Language");
                                        } else if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                            StringBuilder replyBuilder = new StringBuilder();
                                            for (SmartReplySuggestion suggestion : smartReplySuggestionResult.getSuggestions()) {
                                                replyBuilder.append(suggestion.getText()).append("\n");
                                            }
                                            tvReplyMessage.setText(replyBuilder.toString().trim());
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Smart Reply suggestion failed", e);
                                        tvReplyMessage.setText("Error: " + e.getMessage());
                                    }
                                });
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Initialization Error", e);
        }
    }
}
