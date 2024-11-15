package com.escdodev.lojinha.dados

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val titulo: String,
    val descricao: String,
    val concluido: Boolean = false,
    val preco: Double = 0.0,
    val imagemUri: String? = null // Novo campo adicionado
) {
    constructor() : this(null, "", "", false, 0.0, null)
}
