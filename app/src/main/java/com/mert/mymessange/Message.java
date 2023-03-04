package com.mert.mymessange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mert.mymessange.databinding.ActivityMessageBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Message extends AppCompatActivity {
    ArrayList<Mesaj> mesajArrayList;
    SharedPreferences sharedPreferences;
    FirebaseFirestore firebaseFirestore;
    private ActivityMessageBinding binding;
    FirebaseAuth firebaseAuth;
    MesajAdepter mesajAdepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sharedPreferences = this.getSharedPreferences("com.mert.mymessange", Context.MODE_PRIVATE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mesajArrayList = new ArrayList<>();
        Veri();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mesajAdepter = new MesajAdepter(mesajArrayList);
        binding.recyclerView.setAdapter(mesajAdepter);




    }
    public void gonder(View view){
        String isim = sharedPreferences.getString("isim","");
        firebaseFirestore = FirebaseFirestore.getInstance();
        String mesaj = binding.editTextTextPersonName2.getText().toString();
        HashMap<String , Object> postData = new HashMap<>();
        postData.put("mesaj",isim+":"+mesaj);
        postData.put("taih", FieldValue.serverTimestamp());
        firebaseFirestore.collection("Mesajlar").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                binding.editTextTextPersonName2.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Message.this,"Mesaj Gönderme Başarısız",Toast.LENGTH_LONG).show();
            }
        });




    }
    private void Veri(){
        firebaseFirestore.collection("Mesajlar").orderBy("taih", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(Message.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        String mesajcii = (String) data.get("mesaj");
                        Mesaj mesaj1 = new Mesaj(mesajcii);
                        mesajArrayList.add(mesaj1);
                    }
                    mesajAdepter.notifyDataSetChanged();
                }
            }
        });

    }
}