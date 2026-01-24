package com.example.subtrack

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhelper (context: Context) :
    SQLiteOpenHelper(context, "database_subTrack.db", null, 1 ){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE utilizador(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL,
            password TEXT NOT NULL
            )
        """)

        db.execSQL("""
           INSERT INTO utilizador(username, password)
            VALUES ('user', 'pass')
        """)

        db.execSQL("""
           CREATE TABLE assinaturas(
           id INTEGER PRIMARY KEY AUTOINCREMENT,
           nome TEXT NOT NULL,
           valor REAL NOT NULL,
           usuario_id INTEGER,
           FOREIGN KEY (usuario_id) REFERENCES utilizador(id)
           ) 
        """)

    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS assinaturas")
        db.execSQL("DROP TABLE IF EXISTS utilizador")
        onCreate(db)
    }
    fun validarLogin(username: String, pass: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM utilizador WHERE username=? AND password=?",
            arrayOf(username, pass)
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }
    fun listarAssinaturas(usuarioId: Int): List<Assinatura> {
        val lista = ArrayList<Assinatura>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM assinaturas WHERE usuario_id = ?", arrayOf(usuarioId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id"))
                lista.add(Assinatura(id, nome, valor, userId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
    fun inserirAssinatura(assinatura: Assinatura): Long {
        val db = this.writableDatabase
        val contentValues = android.content.ContentValues()
        contentValues.put("nome", assinatura.nome)
        contentValues.put("valor", assinatura.valor)
        contentValues.put("usuario_id", assinatura.usuarioId)

        val id = db.insert("assinaturas", null, contentValues)
        db.close()
        return id
    }
}