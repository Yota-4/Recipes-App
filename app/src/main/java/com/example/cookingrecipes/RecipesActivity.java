package com.example.cookingrecipes;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class RecipesActivity extends AppCompatActivity {

    // UI Components
    private Button btnBreakfast, btnLunch, btnDinner, btnWebsite, btnVideo, btnBack;
    private ListView recipeListView;
    private TextView tvDetails, tvRecipeText;
    private ImageView imgRecipe;
    private ScrollView detailsContainer;

    // Data Management
    private Map<String, String[][]> recipeData;

    // State Variables (for Orientation Changes)
    private String currentCategory;
    private String currentRecipeName;
    private String currentRecipeUrl;
    private String currentVideoUrl;
    private boolean showingDetails= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // Initialize UI Elements
        btnBreakfast= findViewById(R.id.btn_breakfast);
        btnLunch= findViewById(R.id.btn_lunch);
        btnDinner= findViewById(R.id.btn_dinner);
        recipeListView= findViewById(R.id.recipe_list);
        tvDetails= findViewById(R.id.tv_details);
        tvRecipeText = findViewById(R.id.tv_recipe_text);
        imgRecipe = findViewById(R.id.img_recipe);
        detailsContainer = findViewById(R.id.details_container);
        btnWebsite= findViewById(R.id.btn_website);
        btnVideo= findViewById(R.id.btn_video);
        btnBack= findViewById(R.id.btn_back);

        // Retrieve User Name from Login/Register Intent
        String userName = getIntent().getStringExtra("USER_FIRST_NAME");
        if (userName == null || userName.isEmpty()) {
            userName = "Guest";
        }

        TextView tvWelcome = findViewById(R.id.tv_welcome);
        if (tvWelcome != null) {
            tvWelcome.setText("Welcome " + userName);
        }

        // Initialize Recipe Data (Mock Data Source)
        setupRecipeData();

        // Setup Category Buttons
        btnBreakfast.setOnClickListener(v -> showRecipes("Breakfast"));
        btnLunch.setOnClickListener(v -> showRecipes("Lunch"));
        btnDinner.setOnClickListener(v -> showRecipes("Dinner"));

        // Restore State on Screen Rotation (Landscape/Portrait)
        if (savedInstanceState != null) {
            showingDetails = savedInstanceState.getBoolean("showingDetails");
            currentCategory = savedInstanceState.getString("currentCategory");
            currentRecipeName = savedInstanceState.getString("currentRecipeName");
            currentRecipeUrl = savedInstanceState.getString("currentRecipeUrl");
            currentVideoUrl = savedInstanceState.getString("currentVideoUrl");

            int listVis = savedInstanceState.getInt("listVisibility", View.GONE);

            if (showingDetails) {
                tvDetails.setText(getString(R.string.recipe_info, currentCategory, currentRecipeName));                findViewById(R.id.details_container).setVisibility(View.VISIBLE);
                String[][] recipes = recipeData.get(currentCategory);
                if (recipes != null) {
                    for (String[] recipe : recipes) {
                        if (recipe[0].equals(currentRecipeName)) {
                            tvRecipeText.setText(recipe[3]);
                            break;
                        }
                    }
                }
                imgRecipe.setImageResource(getImageResource(currentRecipeName));

                detailsContainer.setVisibility(View.VISIBLE);
                tvDetails.setVisibility(View.VISIBLE);
                btnWebsite.setVisibility(View.VISIBLE);
                btnVideo.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);

                btnBreakfast.setVisibility(View.GONE);
                btnLunch.setVisibility(View.GONE);
                btnDinner.setVisibility(View.GONE);
                recipeListView.setVisibility(View.GONE);

            } else if (listVis == View.VISIBLE) {
                showRecipes(currentCategory);
            }
        }

        // Handle Recipe Selection
        recipeListView.setOnItemClickListener((parent, view, position, id) -> {
            String[][] recipes= recipeData.get(currentCategory);
            if(recipes == null || position>=recipes.length)
                return;
            String[] selectedRecipe= recipes[position];

            currentRecipeName= selectedRecipe[0];
            currentRecipeUrl= selectedRecipe[1];
            currentVideoUrl= selectedRecipe[2];
            String recipeText= selectedRecipe[3];

            tvRecipeText.setText(recipeText);
            imgRecipe.setImageResource(getImageResource(currentRecipeName));
            tvDetails.setText(getString(R.string.recipe_info, currentCategory, currentRecipeName));

            // UI Transitions
            detailsContainer.setVisibility(View.VISIBLE);
            detailsContainer.post(() -> detailsContainer.scrollTo(0, 0));
            tvDetails.setVisibility(View.VISIBLE);
            btnWebsite.setVisibility(View.VISIBLE);
            btnVideo.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
            recipeListView.setVisibility(View.GONE);
            showingDetails= true;
        });

        // Intent Actions for external links
        btnWebsite.setOnClickListener(v -> {
            if (currentRecipeUrl!=null && !currentRecipeUrl.isEmpty())
                openUrl(currentRecipeUrl);
        });
        btnVideo.setOnClickListener(v -> {
            if (currentVideoUrl!=null && !currentVideoUrl.isEmpty())
                openUrl(currentVideoUrl);
        });
        btnBack.setOnClickListener(v -> showCategories());

        // Handle Device Back Button explicitly
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(showingDetails) {
                    showCategories();   // Go back to list if viewing details
                } else {
                    finish();           // Exit activity if on main categories
                }
            }
        });
    }

    private void setupRecipeData() {
        recipeData= new HashMap<>();
        recipeData.put("Breakfast", new String[][]{
                {"Red Velvet Croissants", "https://akispetretzikis.com/recipe/8836/krouasan-red-velvet", "https://youtu.be/aDjJ1o7b_v8?feature=shared", getString(R.string.croissants_text)},
                {"Breakfast Burrito", "https://akispetretzikis.com/recipe/8820/breakfast-burrito", "https://youtu.be/tRyIrzJpt18?feature=shared", getString(R.string.burrito_text)},
                {"Waffles", "https://akispetretzikis.com/recipe/7920/vafles", "https://youtu.be/NItGS3_Iql0?feature=shared", getString(R.string.waffles_text)},
        });

        recipeData.put("Lunch", new String[][]{
                {"Chicken Kontosouvli", "https://akispetretzikis.com/recipe/9060/kontosouvli-kotopoulo-sto-air-fryer", "https://youtu.be/1kUIf6U15ug", getString(R.string.kontosouvli_text)},
                {"Light Mousakas", "https://akispetretzikis.com/recipe/8775/light-mousakas-me-mpesamel-giaourtiou", "https://youtu.be/1x0swsY6A2k", getString(R.string.mousakas_text)},
                {"Smashed Burgers", "https://akispetretzikis.com/recipe/8389/smashed-burgers", "https://youtu.be/cJN8GeDwq6M", getString(R.string.smashed_burgers_text)},
        });

        recipeData.put("Dinner", new String[][]{
                {"Light Wrap with Chicken Salad", "https://akispetretzikis.com/recipe/8035/light-wrap-me-kotosalata", "https://youtu.be/sGA5XmBF2as", getString(R.string.light_wrap_text)},
                {"Mac and Cheese Corn Dogs", "https://akispetretzikis.com/recipe/7910/mac-n-cheese-corn-dogs", "https://youtu.be/HUZYOI92eJs", getString(R.string.corn_dogs_text)},
                {"Porridge", "https://akispetretzikis.com/recipe/5156/porridge", "https://youtu.be/03gYrJsBLig", getString(R.string.porridge_text)},
        });
    }

    private int getImageResource(String recipeName) {
        switch (recipeName) {
            case "Red Velvet Croissants":
                return R.drawable.croissants;
            case "Breakfast Burrito":
                return R.drawable.burrito;
            case "Waffles":
                return R.drawable.waffles;
            case "Chicken Kontosouvli":
                return R.drawable.kontosouvli;
            case "Light Mousakas":
                return R.drawable.mousakas;
            case "Smashed Burgers":
                return R.drawable.burger;
            case "Light Wrap with Chicken Salad":
                return R.drawable.wrap;
            case "Mac and Cheese Corn Dogs":
                return R.drawable.corndogs;
            case "Porridge":
                return R.drawable.porridge;
            default:
                return R.drawable.ic_launcher_background;
        }
    }
    private void showRecipes(String category) {
        currentCategory= category;
        String[][] recipes= recipeData.get(category);
        if (recipes == null)
            return;     //safety net in case there are no recipes in a category
        String[] recipeNames= new String[recipes.length];
        for (int i=0; i<recipes.length; i++){
            recipeNames[i]= recipes[i][0];
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
        recipeListView.setAdapter(adapter);

        detailsContainer.setVisibility(View.GONE);
        recipeListView.setVisibility(View.VISIBLE);
        btnBreakfast.setVisibility(View.GONE);
        btnLunch.setVisibility(View.GONE);
        btnDinner.setVisibility(View.GONE);
        tvDetails.setVisibility(View.GONE);
        btnWebsite.setVisibility(View.GONE);
        btnVideo.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
        showingDetails= false;
    }

    private void openUrl(String url) {
        if (url!=null && !url.isEmpty()) {
            Intent intent= new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    private void showCategories() {
        recipeListView.setVisibility(View.GONE);
        tvDetails.setVisibility(View.GONE);
        btnWebsite.setVisibility(View.GONE);
        btnVideo.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        detailsContainer.setVisibility(View.GONE);
        btnBreakfast.setVisibility(View.VISIBLE);
        btnLunch.setVisibility(View.VISIBLE);
        btnDinner.setVisibility(View.VISIBLE);
        showingDetails= false;
    }

//    Retain state for landscape mode
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("showingDetails", showingDetails);
        outState.putString("currentCategory", currentCategory);
        outState.putString("currentRecipeName", currentRecipeName);
        outState.putString("currentRecipeUrl", currentRecipeUrl);
        outState.putString("currentVideoUrl", currentVideoUrl);
        outState.putInt("listVisibility", recipeListView.getVisibility());
    }
}
