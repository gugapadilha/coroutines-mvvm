package com.example.filmescoroutines.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: MainRepository) : ViewModel() { //injecao de dependencia
    val filmesLiveData = MutableLiveData<List<Filme>>()

    //FORMA MAIS OU MENOS
    fun getFilmes() {
        repository.getFilmes { filmes ->
            filmesLiveData.postValue(filmes) //se usar o value ele não reconhece, pois está em outra thread
        }
    }

    //USANDO AGORA COROUTINES
    fun getFilmesCoroutines() { //ser criado na classe main
        CoroutineScope(Dispatchers.Main).launch {
            //tudo aqui dentro so aceita funcao suspensa (scope coroutine)

            val filmes = withContext(Dispatchers.Default) {
                repository.getFilmesCoroutines()
            }

            filmesLiveData.value = filmes
        }
    }

    //FAZEDO A INJECAO DE DEPENDENCIA NO COMEÇO DA CLASSE MAINVIEWMODEL
    class MainViewModelFactory(
        private val repository: MainRepository
        ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}