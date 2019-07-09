package tech.orionn.prototipo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);


        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String name = preferences.getString("nome", "");
        if (name != null && !name.isEmpty()) {
            ((TextView) findViewById(R.id.oi)).setText("Oi, ".concat(name).concat("!"));
        }


        findViewById(R.id.novo_projeto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InitialActivity.this, CriarProjetoActivity.class));
            }
        });
        findViewById(R.id.ver_projetos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InitialActivity.this, AcessarProjetoActivity.class));
            }
        });
    }
}
