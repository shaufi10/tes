package transport.com.grinasia.travel_transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by coder on 20-Jan-17.
 */

public class Detail_Order_Aktif extends ActionBarActivity {

    Button Arrived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_next);

        Arrived = (Button) findViewById(R.id.accept);

        Arrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Detail_Order_Aktif.this, MainActivity.class);
                startActivity(a);
            }
        });
    }
}
