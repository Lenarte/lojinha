package com.escdodev.lojinha.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.escdodev.lojinha.dados.IRepository
import com.escdodev.lojinha.dados.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemViewModel(
    private val repository : IRepository
) : ViewModel(){

    private val _itens = MutableStateFlow<List<Item>>(emptyList())
    val itens: StateFlow<List<Item>> get() = _itens

    init {
        viewModelScope.launch {
            repository.listarItens().collectLatest { listaDeItens ->
                _itens.value = listaDeItens
            } //.collectLastest
        }
    }

    fun excluir(item: Item) {
        viewModelScope.launch {
            repository.excluiritem(item)
        }
    }

    suspend fun buscarPorId(itemId: Int): Item? {
        return withContext(Dispatchers.IO){
            repository.buscarItemPorId(itemId)
        }
    }

    fun gravar(item: Item) {
        viewModelScope.launch {
            repository.gravarItem(item)
        }
    }

}
