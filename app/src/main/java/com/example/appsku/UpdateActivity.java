package com.example.appsku;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button update_button, delete_button;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Inisialisasi elemen UI
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // Pertama, kita panggil metode ini untuk mengambil data dari intent
        getAndSetIntentData();

        // Set judul action bar setelah metode getAndSetIntentData dipanggil
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        // Menambahkan onClickListener untuk tombol "Update"
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dan hanya setelah itu kita panggil metode ini
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                pages = pages_input.getText().toString().trim();
                if (myDB.updateData(id, title, author, pages)) {
                    // Data berhasil diupdate, tampilkan pesan sukses dan redirect ke MainActivity
                    // Toast.makeText(UpdateActivity.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                    redirectToMainActivity();
                } else {
                    // Data gagal diupdate, tampilkan pesan gagal
                    Toast.makeText(UpdateActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Menambahkan onClickListener untuk tombol "Delete"
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    // Metode untuk mengambil dan menetapkan data dari intent
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            // Mendapatkan data dari Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            // Menetapkan data dari Intent ke elemen UI
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
            Log.d("thianos", title+" "+author+" "+pages);
        }else{
            Toast.makeText(this, "No Data Entry", Toast.LENGTH_SHORT).show();
        }
    }

    // Metode untuk menampilkan dialog konfirmasi sebelum menghapus data
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                redirectToMainActivity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Tidak melakukan apa-apa jika tombol "No" ditekan
            }
        });
        builder.create().show();
    }

    // Metode untuk mengarahkan pengguna ke MainActivity
    void redirectToMainActivity() {
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Tutup Activity UpdateActivity saat mengarahkan ke MainActivity
    }
}
