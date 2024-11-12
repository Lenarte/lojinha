package com.escdodev.lojinha.dados

import androidx.room.Entity
import androidx.room.PrimaryKey


//INFRAESTRUTURA DE BANCO DE DADOS
//https://developer.android.com/training/data-storage/room

//Entidades ou tabelas do banco de dados
@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val titulo: String,
    val descricao: String,
    val concluido: Boolean = false
){
    constructor() : this(null, "", "", false)
}
