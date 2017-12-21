package ui;

/**
 * Created by domenico on 24/05/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dijkstra.Navigation;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);
        finish();
    }
}
