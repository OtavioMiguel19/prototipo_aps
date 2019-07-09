package tech.orionn.prototipo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tech.orionn.prototipo.helpers.InternalFirebase;
import tech.orionn.prototipo.model.Project;

public class CriarProjetoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_projeto);

        findViewById(R.id.novo_projeto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                String user = preferences.getString("user", "");
                assert user != null;
                final DatabaseReference reference = InternalFirebase.getDatabaseReferenceToProject().child(user);
                Query query = reference.orderByChild("nome");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String name = ((TextInputEditText) findViewById(R.id.nome)).getText().toString();
                        Project project = new Project();
                        project.setNome(name);
                        DatabaseReference newReference = reference.push();
                        newReference.setValue(project);
                        Intent intent = (new Intent(CriarProjetoActivity.this, AcessarProjetoActivity.class));
                        Bundle bundle = new Bundle();
                        bundle.putString("project", newReference.getKey());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
