package tech.orionn.prototipo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tech.orionn.prototipo.helpers.InternalFirebase;
import tech.orionn.prototipo.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForUser();
        findViewById(R.id.fazer_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference reference = InternalFirebase.getDatabaseReferenceToUser();
                try {
                    final String cnpj = ((TextInputEditText)findViewById(R.id.cnpj)).getText().toString();
                    final String senha = ((TextInputEditText)findViewById(R.id.senha)).getText().toString();

                    Query query = reference.orderByChild("email");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChild(cnpj)) {
                                showToast("Usuário não existe.");
                            } else {
                                User user = dataSnapshot.child(cnpj).getValue(User.class);
                                if (!user.getSenha().equals(senha)) {
                                    showToast("Senha incorreta.");
                                } else {
                                    SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("user", cnpj);
                                    editor.putString("nome", user.getNome());
                                    editor.apply();
                                    startActivity(new Intent(MainActivity.this, InitialActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                    showToast("Preencha todos os campos!");
                }
            }
        });
        findViewById(R.id.cadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });
    }

    private void checkForUser() {
        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String user = preferences.getString("user", "");
        if (user != null && !user.isEmpty()) {
            startActivity(new Intent(MainActivity.this, InitialActivity.class));
        }
    }

    private void showToast(final String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
