package com.dafa.challeng4synrgy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dafa.challeng4synrgy.data.AppDatabase
import com.dafa.challeng4synrgy.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName : EditText
    private lateinit var email :EditText
    private lateinit var phone :EditText
    private lateinit var btnSave :Button
    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnSave = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if(intent!=null){
            val id = intent.getInt("id", 0)
            var user = database.userDao().get(id)

            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)
        }

        btnSave.setOnClickListener{
            if (fullName.text.isNotEmpty() &&
                email.text.isNotEmpty() &&
                phone.text.isNotEmpty()){
                if(intent!=null){
                    //edit data
                    database.userDao().update(
                        User(
                            intent.getInt("id",0),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                } else {//tambah data
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }
                finish()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Silahkan isi data dengan data yang valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}