package id.ac.ukdw.pertemuan13b_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference dbChat;
    private List<Map> listChat;

    private EditText txtChat;
    private Button btnChat;
    private TextView txtHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup database
        db = FirebaseDatabase.getInstance();
        dbChat = db.getReference("chatting");
        dbChat.addValueEventListener(new ValueEventListener() {
            //ketika data pada server berubah (bertambah/berkurang/diedit)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChat = (List<Map>)dataSnapshot.getValue();
                if(listChat != null){
                    String tmpStr = "";
                    for(int i = 0; i < listChat.size(); i++){
                        tmpStr += listChat.get(i).get("user")+": "+
                                listChat.get(i).get("message")+"\n";
                    }
                    txtHasil.setText(tmpStr);
                }else{
                    listChat = new ArrayList<Map>();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        txtChat = (EditText)findViewById(R.id.txtChat);
        btnChat = (Button)findViewById(R.id.btnChat);
        txtHasil = (TextView)findViewById(R.id.txtHasil);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map chat = new HashMap();
                chat.put("user","adi");
                chat.put("message", txtChat.getText().toString());
                listChat.add(chat);
                dbChat.setValue(listChat);
                txtChat.setText("");
            }
        });
    }
}
