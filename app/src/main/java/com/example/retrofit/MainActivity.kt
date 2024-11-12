package com.example.retrofit

import PokeApiService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.retrofit.Pokemon

class MainActivity : ComponentActivity() {

    private val pokeApiService: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            fetchPokemon("pikachu")
        }
    }

    private suspend fun fetchPokemon(pokemonName: String) {
        try {
            val  pokemon = pokeApiService.getPokemon(pokemonName) as Pokemon
            println("Name: ${pokemon.name}, Height: ${pokemon.height}, Weight: ${pokemon.weight}")
        } catch (e: Exception) {
            println("Failed to fetch Pokemon data: ${e.message}")
        }
    }
}