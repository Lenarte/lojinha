package com.escdodev.lojinha.dados

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun listarItens(): Flow<List<Item>>
    suspend fun buscarItemPorId(idx: Int): Item?
    suspend fun gravarItem(item: Item)
    suspend fun excluiritem(item: Item)
}
