package transport.com.grinasia.travel_transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by coder on 06-Jan-17.
 */

public class LoginActivity extends AppCompatActivity {

    TextView txtSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtSignUp = (TextView) findViewById(R.id.txtSignUp);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(LoginActivity.this, Register.class);
                startActivity(a);
            }
        });
    }
}
