package com.example.retrofit

import PokeApiService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.await

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

        setContent {

            var pokemon by remember { mutableStateOf<Pokemon?>(null) }
            var secondPokemon by remember { mutableStateOf<Pokemon?>(null) }
            var thirdPokemon by remember { mutableStateOf<Pokemon?>(null) }
            var fourthPokemon by remember { mutableStateOf<Pokemon?>(null) }
            var fifthPokemon by remember { mutableStateOf<Pokemon?>(null) }

            LaunchedEffect(Unit) {
                pokemon = fetchPokemon("ditto")
                secondPokemon = fetchPokemon("pikachu")
                thirdPokemon = fetchPokemon("squirtle")
                fourthPokemon = fetchPokemon("charmander")
                fifthPokemon = fetchPokemon("bulbasaur")
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column {
                    PokemonData(pokemon = pokemon)
                    Spacer(modifier = Modifier.height(30.dp))
                    PokemonData(pokemon = secondPokemon)
                    Spacer(modifier = Modifier.height(30.dp))
                    PokemonData(pokemon = thirdPokemon)
                    Spacer(modifier = Modifier.height(30.dp))
                    PokemonData(pokemon = fourthPokemon)
                    Spacer(modifier = Modifier.height(30.dp))
                    PokemonData(pokemon = fifthPokemon)
                }
            }
        }
    }

    private suspend fun fetchPokemon(pokemonName: String): Pokemon? {
        try {
            val  pokemon = pokeApiService.getPokemon(pokemonName)
            return pokemon.await()
        } catch (e: Exception) {
            println("Failed to fetch Pokemon data: ${e.message}")
        }
        return null
    }

    @Composable
    fun PokemonData(pokemon: Pokemon?) {
        Column {
            Text(text = "Pokemon:", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Name: " + pokemon?.name.toString())
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "Height: " + pokemon?.height.toString())
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Weight: " + pokemon?.weight.toString())
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "Base Experience: " + pokemon?.base_experience.toString())
            }
        }
    }
}