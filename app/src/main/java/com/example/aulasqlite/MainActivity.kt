package com.example.aulasqlite

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulasqlite.bancodedados.DatabaseHelper
import com.example.aulasqlite.bancodedados.ProdutoDAO
import com.example.aulasqlite.model.Produto
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val bancoDados by lazy {
        DatabaseHelper(this)
    }

    private lateinit var editNomeProduto: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnListar: Button
    private lateinit var btnAtualizar: Button
    private lateinit var btnDeletar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editNomeProduto = findViewById(R.id.editNomeProduto)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnListar = findViewById(R.id.btnListar)
        btnAtualizar = findViewById(R.id.btnAtualizar)
        btnDeletar = findViewById(R.id.btnDeletar)


        btnSalvar.setOnClickListener {
            salvar()
        }

        btnListar.setOnClickListener {
            listar()
        }

        btnAtualizar.setOnClickListener {
            atualizar()
        }

        btnDeletar.setOnClickListener {
            remover()
        }


    }

    private fun salvar() {
        val nomeProduto = editNomeProduto.text.toString()

        val produtoDAO = ProdutoDAO(this)

        val produto = Produto(
            -1, nomeProduto, "descrição..."
        )

        produtoDAO.salvar(produto)


    }

    private fun listar() {
        val produtoDAO = ProdutoDAO(this)
        val listaProduto = produtoDAO.listar()

        if(listaProduto.isNotEmpty()){
            listaProduto.forEach{produto ->
                Log.i("db_info", "${produto.titulo} - ${produto.descricao}")
            }
        }
    }

    private fun atualizar() {
        val nomeProduto = editNomeProduto.text.toString()

        val produtoDAO = ProdutoDAO(this)

        val produto = Produto(
            -1, nomeProduto, "descrição..."
        )

        produtoDAO.atualizar(produto)
    }

    private fun remover() {

        val produtoDAO= ProdutoDAO(this)
        produtoDAO.remover(2)

    }
}