package com.escdodev.lojinha.dados



import kotlinx.coroutines.flow.Flow

class LocalRepository(
    private val dao : ItemDao
) : IRepository {

    override fun listarItens(): Flow<List<Item>> {
        return dao.listaritens()
    }

    override suspend fun buscarItemPorId(idx: Int): Item {
        return dao.buscarItemPorId(idx)
    }

    override suspend fun gravarItem(item: Item) {
        dao.gravarItem(item)
    }

    override suspend fun excluiritem(item: Item) {
        dao.excluirItem(item)
    }
    // Recuperar lista completa do Room
    suspend fun listarItensDiretamente(): List<Item> = dao.listaritensDiretamente()


}
