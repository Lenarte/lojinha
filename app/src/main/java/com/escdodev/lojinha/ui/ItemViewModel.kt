package com.escdodev.lojinha.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.escdodev.lojinha.dados.SyncRepository
import com.escdodev.lojinha.dados.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemViewModel(
    private val repository: SyncRepository // Alterado para SyncRepository
) : ViewModel() {

    private val _itens = MutableStateFlow<List<Item>>(emptyList())
    val itens: StateFlow<List<Item>> get() = _itens

    init {
        sincronizarDados() // Sincroniza dados ao iniciar
        carregarItens() // Carrega itens locais para exibir
    }

    private fun carregarItens() {
        viewModelScope.launch {
            repository.listarItens().collectLatest { listaDeItens ->
                _itens.value = listaDeItens
            }
        }
    }

    fun sincronizarDados() {
        viewModelScope.launch {
            try {
                repository.sincronizarDados() // Sincroniza local e remoto
            } catch (e: Exception) {
                // Log de erro ou mensagem ao usuário
                e.printStackTrace()
            }
        }
    }

    fun gravar(item: Item) {
        viewModelScope.launch {
            repository.gravarItem(item) // Salva no local e tenta sincronizar
        }
    }

    fun excluir(item: Item) {
        viewModelScope.launch {
            repository.excluirItem(item) // Exclui do local e tenta sincronizar
        }
    }

    suspend fun buscarPorId(itemId: Int): Item? {
        return withContext(Dispatchers.IO) {
            repository.localRepository.buscarItemPorId(itemId) // Busca localmente
        }
    }
}
