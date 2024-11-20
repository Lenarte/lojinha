package com.escdodev.lojinha.dados



import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class RemoteRepository() : IRepository {

    var db = FirebaseFirestore.getInstance()
    var itemCollection = db.collection("itens")

    // Função reativa
    override fun listarItens(): Flow<List<Item>> = callbackFlow {
        val listener = itemCollection.addSnapshotListener { dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null) {
                val itens = dados.documents.mapNotNull { dado ->
                    dado.toObject(Item::class.java)
                }
                trySend(itens).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    //novo
    suspend fun getId(): Int {
        val snapshot = itemCollection.get().await()
        val maxId = snapshot.documents.mapNotNull { it.getLong("id")?.toInt() }.maxOrNull() ?: 0
        return maxId + 1
    }


//    suspend fun getId(): Int {
//        val dados = itemCollection.get().await()
//        // Recupera o maior id do Firestore no formato inteiro
//        val maxId = dados.documents.mapNotNull {
//            it.getLong("id")?.toInt()
//        }.maxOrNull() ?: 0
//        return maxId + 1
//    }

    override suspend fun gravarItem(item: Item) {
        val document: DocumentReference
        if (item.id == null) { // Inclusão
            item.id = getId()
            document = itemCollection.document(item.id.toString())
        } else { // Alteração
            document = itemCollection.document(item.id.toString())
        }
        document.set(item).await()  // Agora o item inclui imagemUri
    }

    override suspend fun buscarItemPorId(idx: Int): Item? {
        val dados = itemCollection.document(idx.toString()).get().await()
        return dados.toObject(Item::class.java)
    }

    override suspend fun excluiritem(item: Item) {
        itemCollection.document(item.id.toString()).delete().await()
    }

    suspend fun listarItensDiretamente(): List<Item> {
        val snapshot = itemCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(Item::class.java) }
    }

}

