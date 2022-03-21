package io.jonpruessmeier.crm_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editName: EditText = findViewById(R.id.editCustomerName);
        val editEmail: EditText = findViewById(R.id.editCustomerEmail);
        val editTel: EditText = findViewById(R.id.editCustomerTel);

        val btn: Button = findViewById(R.id.submitCustomer);

        updateList();

        btn.setOnClickListener {
            val name = editName.text.toString();
            val email = editEmail.text.toString();
            val tel = editTel.text.toString();

            val db = DBHelper(this, null);
            db.addCustomer(name, email, tel);

            updateList();
        }

    }

    fun updateList(): Unit{
        try {
            val customerList: LinearLayout = findViewById(R.id.customerList);
            customerList.removeAllViews();

            val db = DBHelper(this, null);
            val cursor = db.getAllCustomers();
            cursor!!.moveToFirst()
            do {
                val customerView = TextView(this);

                var name =  cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL));
                var email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_COL));
                var tel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TEL_COL));

                var text = "Name: $name \n" +
                        "Email: $email \n" +
                        "Tel: $tel"

                customerView.text = text;

                customerList.addView(customerView);

            } while(cursor.moveToNext());



        } catch (e: Exception){
            println(e);
        }




    }
}