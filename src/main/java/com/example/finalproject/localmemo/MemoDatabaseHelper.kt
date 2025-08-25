package com.example.finalproject.localmemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MemoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "memo_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE memo (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                content TEXT,
                date TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS memo")
        onCreate(db)
    }

    fun insertMemo(title: String, content: String, date: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("content", content)
            put("date", date)
        }
        db.insert("memo", null, values)
        db.close()
    }

    fun getAllMemos(): List<Memo> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM memo ORDER BY id DESC", null)
        val memos = mutableListOf<Memo>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
            val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
            memos.add(Memo(id, title, content, date))
        }
        cursor.close()
        db.close()
        return memos
    }
}