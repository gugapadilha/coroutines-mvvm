package com.example.filmescoroutines.ui.main

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainRepository {

    //FORMA MAIS OU MENOS
    fun getFilmes(callback: (filmes: List<Filme>) -> Unit) {
        //return -> Como estamos executando esse codigo em outra thead, nao podemos usar o return
        //por isso usamos o callback -seria a mesma coisa caso estivesse trabalhando com retofrit-

        Thread(Runnable { //thread criada simulando delay de 3 segundos
            Thread.sleep(3000)
            callback.invoke(
                listOf(
                    Filme(1, "Titulo 01"),
                    Filme(2, "Titulo 02")
                )
            )

        }).start()
    }

    //USANDO AGORA COROUTINES - FORMA CERTA
    suspend fun getFilmesCoroutines(): List<Filme> {
        //quando iniciar um contexto de coroutines, ele deve estar dentro de uma função suspensa
        //aqui sim eu posso usar o return
        //esse codigo nao precisa de um calllback
        return withContext(Dispatchers.Default){
            delay(3000)
            listOf(
                Filme(1, "Titulo 01"),
                Filme(2, "Titulo 02")
            )
        }
    }
}