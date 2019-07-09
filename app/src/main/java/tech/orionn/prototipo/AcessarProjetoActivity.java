package tech.orionn.prototipo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tech.orionn.prototipo.helpers.InternalFirebase;
import tech.orionn.prototipo.model.Project;

public class AcessarProjetoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acessar_projeto);
        Bundle bundle = getIntent().getExtras();
        try {
            SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
            String user = preferences.getString("user", "");

            final String project = bundle.getString("project", "");
            DatabaseReference reference = InternalFirebase.getDatabaseReferenceToProject().child(user).child(project);
            Query query = reference.orderByChild("nome");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Project proj = dataSnapshot.getValue(Project.class);
                    getSupportActionBar().setTitle(proj.getNome());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.acessar_uc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcessarProjetoActivity.this, AcessarUCActivity.class));
            }
        });
        findViewById(R.id.acessar_requisito).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcessarProjetoActivity.this, VisualizarRequisitoActivity.class));
            }
        });
    }
}
