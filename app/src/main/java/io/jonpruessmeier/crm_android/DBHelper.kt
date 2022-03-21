package io.jonpruessmeier.crm_android

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT, " +
                EMAIL_COL + " TEXT, " +
                TEL_COL + " TEXT);");


        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // this method is to check if table already exists
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            onCreate(db)
        }
    }

    fun addCustomer(name: String, email: String?, telephone: String?): Unit{
        val values = ContentValues();
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(TEL_COL, telephone);

        val db = this.writableDatabase;

        db.insert(TABLE_NAME, null, values);

        db.close();

    }

    fun getAllCustomers(): Cursor? {
        val statement = "SELECT * FROM $TABLE_NAME";
        val db = this.writableDatabase;
        val cursor = db.rawQuery(statement, null);
        return cursor;
    }

    companion object{
        // here we have defined variables for our database
        // below is variable for database name
        private val DATABASE_NAME = "CRM"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "customers"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COL = "company_name"

        // below is the variable for the email column
        val EMAIL_COL = "email"

        // below is the variable for the telephone column
        val TEL_COL = "telephone"

    }
}