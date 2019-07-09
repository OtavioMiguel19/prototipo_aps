package tech.orionn.prototipo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tech.orionn.prototipo.helpers.InternalFirebase;
import tech.orionn.prototipo.model.User;

public class CadastroActivity extends AppCompatActivity {

    DatabaseReference reference = InternalFirebase.getDatabaseReferenceToUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        findViewById(R.id.cadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String name = ((TextInputEditText) findViewById(R.id.nome)).getText().toString();
                    final String cnpj = ((TextInputEditText) findViewById(R.id.cnpj)).getText().toString();
                    final String email = ((TextInputEditText) findViewById(R.id.email)).getText().toString();
                    final String telefone = ((TextInputEditText) findViewById(R.id.telefone)).getText().toString();
                    final String senha = ((TextInputEditText) findViewById(R.id.senha)).getText().toString();
                    final String confirmar = ((TextInputEditText) findViewById(R.id.confirmar)).getText().toString();

                    if (((CheckBox) findViewById(R.id.aceitar)).isChecked()) {

                        if (senha.equals(confirmar)) {
                            Query query = reference.orderByChild("email");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.hasChild(cnpj)) {
                                        User user = new User();
                                        user.setCnpj(cnpj);
                                        user.setEmail(email);
                                        user.setNome(name);
                                        user.setSenha(senha);
                                        user.setTelefone(telefone);
                                        reference.child(cnpj).setValue(user);

                                        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("user", cnpj);
                                        editor.putString("nome", name);
                                        editor.apply();

                                        startActivity(new Intent(CadastroActivity.this, InitialActivity.class));
                                    } else {
                                        ((TextInputEditText) findViewById(R.id.cnpj)).setError("CNPJ já cadastrado.");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Senhas não conferem", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Aceite os termos!", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Erro: preencha todos os campos!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
