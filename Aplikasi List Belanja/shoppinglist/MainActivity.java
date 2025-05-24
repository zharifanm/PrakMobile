package com.example.shoppinglist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ShoppingListAdapter adapter;
    private ArrayList<ShoppingItem> shoppingItems;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListView();
        setupFab();
        addSampleData();
    }

    private void initViews() {
        listView = findViewById(R.id.listView);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void setupListView() {
        shoppingItems = new ArrayList<>();
        adapter = new ShoppingListAdapter(this, shoppingItems);
        listView.setAdapter(adapter);

        // Set item click listener untuk toggle status
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ShoppingItem item = shoppingItems.get(position);
            item.setPurchased(!item.isPurchased());
            adapter.notifyDataSetChanged();
        });

        // Set long click listener untuk hapus item
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
            return true;
        });
    }

    private void setupFab() {
        fabAdd.setOnClickListener(v -> showAddItemDialog());
    }

    private void addSampleData() {
        shoppingItems.add(new ShoppingItem("Beras 5kg", "Makanan Pokok", false));
        shoppingItems.add(new ShoppingItem("Minyak Goreng", "Makanan Pokok", false));
        shoppingItems.add(new ShoppingItem("Gula Pasir", "Makanan Pokok", true));
        shoppingItems.add(new ShoppingItem("Sabun Mandi", "Kebersihan", false));
        shoppingItems.add(new ShoppingItem("Pasta Gigi", "Kebersihan", false));
        adapter.notifyDataSetChanged();
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        EditText etItemName = dialogView.findViewById(R.id.etItemName);
        EditText etCategory = dialogView.findViewById(R.id.etCategory);

        builder.setView(dialogView)
                .setTitle("Tambah Item Baru")
                .setPositiveButton("Tambah", (dialog, which) -> {
                    String itemName = etItemName.getText().toString().trim();
                    String category = etCategory.getText().toString().trim();

                    if (!itemName.isEmpty()) {
                        if (category.isEmpty()) {
                            category = "Lainnya";
                        }
                        shoppingItems.add(new ShoppingItem(itemName, category, false));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Item berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Nama item tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus Item")
                .setMessage("Apakah Anda yakin ingin menghapus item ini?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    shoppingItems.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Item berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}