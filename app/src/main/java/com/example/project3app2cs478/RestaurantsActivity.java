package com.example.project3app2cs478;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class RestaurantsActivity extends AppCompatActivity {
    RestaurantsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurants);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(RestaurantsViewModel.class);

        //loads list fragment
        //puts ListFragment into listContainer
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listContainer, new RestaurantsListFragment())
                .commit();

        // Restore WebFragment if URL exists in ViewModel
        if (viewModel.currentUrl != null) {
            showWebFragment(viewModel.currentUrl, viewModel.selectedPosition);
        }

        //handles back press actions
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                //check if theres something on the backstack
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    //if there is that means that a web fragment was added so remove it from the backstack
                    getSupportFragmentManager().popBackStack();

                    // Reset layout to full screen list
                    findViewById(R.id.listContainer).setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT
                            )
                    );
                    //webview disappears with width 0
                    findViewById(R.id.webContainer).setLayoutParams(
                            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
                    );

                } else {
                    //if nothing is in the backstack close the activity
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; // show menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_restaurants) {
            // Already in AttractionsActivity
            Toast.makeText(this, "Already viewing Restaurants", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_attractions) {
            // Launch RestaurantsActivity
            Intent intent = new Intent(this, AttractionActivity.class);
            startActivity(intent);
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //runs when user clicks an item list
    public void showWebFragment(String url, int position) {
        viewModel.selectedPosition = position;
        viewModel.currentUrl = url;

        // Resize layout → 1/3 list, 2/3 web
        LinearLayout.LayoutParams listParams =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);

        LinearLayout.LayoutParams webParams =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2);

        findViewById(R.id.listContainer).setLayoutParams(listParams);
        findViewById(R.id.webContainer).setLayoutParams(webParams);

        // Load WebFragment that shows the website
        WebFragment webFragment = new WebFragment();

        //show the webfragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.webContainer, webFragment)
                .addToBackStack(null) //add web fragment to backstack
                .commit();
        //Forces fragment to load so that its ready before loading the url
        getSupportFragmentManager().executePendingTransactions();
        //displays the selected website
        webFragment.loadUrl(url);
    }

}
