package com.example.appsku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // Deklarasi variabel
    EditText title_input, author_input, pages_input;
    Button add_button, back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Inisialisasi elemen UI
        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        // Menambahkan onClickListener ke tombol "Add"
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat instance dari MyDatabaseHelper
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);

                // Mengambil nilai dari input pengguna dan menambahkannya ke database
                myDB.addBook(
                        title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim())
                );

                // Setelah data ditambahkan, Anda dapat menambahkan logika tambahan di sini,
                // seperti kembali ke MainActivity atau memberikan pesan sukses.
                // Contohnya:
                Toast.makeText(AddActivity.this, "Data Has Successfully Added.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
