package com.escdodev.lojinha.dados

import kotlinx.coroutines.flow.Flow

class SyncRepository(
    val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    // Retorna os itens do Room (dados locais)
    fun listarItens(): Flow<List<Item>> = localRepository.listarItens()

    // Sincroniza dados entre Room e Firestore
    suspend fun sincronizarDados() {
        // 1. Baixar dados do Firestore e salvar no Room
        val itensRemotos = remoteRepository.listarItensDiretamente()
        itensRemotos.forEach { item ->
            localRepository.gravarItem(item)
        }

        // 2. Subir dados do Room para o Firestore
        val itensLocais = localRepository.listarItensDiretamente()
        itensLocais.forEach { item ->
            remoteRepository.gravarItem(item)
        }
    }

    // Salvar um item tanto no Room quanto no Firestore novo
    suspend fun gravarItem(item: Item) {
        if (item.id == null) {
            // Caso o ID seja null, gere um novo ID local ou remoto
            val novoId = remoteRepository.getId()
            item.id = novoId
        }

        // Salva no banco local
        localRepository.gravarItem(item)

        // Tenta salvar no Firestore
        try {
            remoteRepository.gravarItem(item)
        } catch (e: Exception) {
            // Log ou persistir que a sincronização falhou
        }
    }

//    suspend fun gravarItem(item: Item) {
//        localRepository.gravarItem(item) // Salva localmente no Room
//        try {
//            remoteRepository.gravarItem(item) // Tenta salvar no Firestore
//        } catch (e: Exception) {
//            // Log ou persistência de falha de sincronização
//        }
//    }

    // Excluir um item do Room e do Firestore
    suspend fun excluirItem(item: Item) {
        localRepository.excluiritem(item) // Remove localmente
        try {
            remoteRepository.excluiritem(item) // Tenta excluir do Firestore
        } catch (e: Exception) {
            // Log ou persistência de falha de sincronização
        }
    }


}
