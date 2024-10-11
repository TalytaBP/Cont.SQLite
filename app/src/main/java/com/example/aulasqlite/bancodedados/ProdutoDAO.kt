package com.example.aulasqlite.bancodedados

import android.content.Context
import android.util.Log
import com.example.aulasqlite.model.Produto
import java.lang.Exception
class ProdutoDAO(context:Context):IProdutoDAO {
    val escrita = DatabaseHelper(context).writableDatabase
    val leitura = DatabaseHelper(context).readableDatabase
    override fun salvar(produto: Produto): Boolean {
        val titulo = produto.titulo

        try{
            val sql = "INSERT INTO ${DatabaseHelper.TABELA} VALUES(null,'$titulo','512gb')"
            escrita.execSQL(sql)
            Log.i("db_info","Produto salvo")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("db_info","Erro ao salvar produto")
            return false
        }
        return true
    }
    override fun atualizar(produto: Produto): Boolean {

        val nomeProduto = produto.titulo
        val sql = "UPDATE ${DatabaseHelper.TABELA} SET ${DatabaseHelper.TITULO} = '$nomeProduto' WHERE ${DatabaseHelper.ID_PRODUTO}=2;"

        try{
            escrita.execSQL(sql)
            Log.i("db_info","Registro atualizado com suceso.")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("db_info","Error ao atualizar..")
            return false
        }
        return true
    }
    override fun remover(idProduto: Int): Boolean {
        val sql = "DELETE FROM ${DatabaseHelper.TABELA} WHERE ${DatabaseHelper.ID_PRODUTO}=$idProduto"
        try {
            escrita.execSQL(sql)
            Log.i("db_info","Produto removido com sucesso..")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("db_info","Error ao excluir..")
            return false
        }
        return true
    }
    override fun listar(): List<Produto> {
        val listaProduto = mutableListOf<Produto>()

        val sql = "SELECT * FROM ${DatabaseHelper.TABELA}"
        val cursor = leitura.rawQuery(sql, null)

        while (cursor.moveToNext()) {//retorn true or false
            val idProduto = cursor.getInt(0)
            val titulo = cursor.getString(1)
            val descricao = cursor.getString(2)

            val produto = Produto(idProduto, titulo, descricao)

            listaProduto.add(produto)

            //Log.i("db_info", "PRODUTOS: $idProduto - $titulo - $descricao")
        }
        return listaProduto
    }
}