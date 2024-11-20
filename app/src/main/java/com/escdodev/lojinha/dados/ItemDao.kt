package com.escdodev.lojinha.dados

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//INFRAESTRUTURA DE BANCO DE DADOS
//https://developer.android.com/training/data-storage/room

// Objetos de manipulação do banco de dados
@Dao
interface ItemDao {

    //Listar
    @Query("select * from item")
    fun listaritens(): Flow<List<Item>>
    //suspend fun listarItens(): List<Item>

    //Buscar por Id
    @Query("select * from item where id = :idx")
    suspend fun buscarItemPorId(idx: Int): Item

    //Gravar @Update @Insert
    @Upsert
    suspend fun gravarItem(item: Item)

    //Excluir
    @Delete
    suspend fun excluirItem(item: Item)

    @Query("SELECT * FROM item")
    suspend fun listaritensDiretamente(): List<Item>


}
