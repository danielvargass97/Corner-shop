package com.dvargas.adaschool.cornershop

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvargas.adaschool.cornershop.dataLayer.dto.Item
import com.dvargas.adaschool.cornershop.dataLayer.dto.ItemAdapter
import com.dvargas.adaschool.cornershop.dataLayer.dto.digimon.api.Digimon
import com.dvargas.adaschool.cornershop.dataLayer.dto.rickandmorty.api.characters.Character
import com.dvargas.adaschool.cornershop.databinding.ActivityMainBinding
import com.dvargas.adaschool.cornershop.domain.services.digimon.DigimonService
import com.dvargas.adaschool.cornershop.domain.services.rickandmorty.RickAndMortyService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @Named("rickAndMorty")
    lateinit var rickAndMortyService: RickAndMortyService

    @Inject
    @Named("digimon")
    lateinit var digimonService: DigimonService

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateBanner()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        val rickAndMortyButton = binding.rickAndMortyButton
        rickAndMortyButton.setOnClickListener {
            getRickAndMortyCharacters()
        }
        val digimonButton = binding.digimonButton
        digimonButton.setOnClickListener {
            getDigimonCharacters()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getDigimonCharacters() {
        GlobalScope.launch {
            val response = digimonService.getAllDigimon()
            if (response.isSuccessful) {
                response.body()?.let { recyclerViewWithDigimons(it) }
            }
        }
    }

    private fun recyclerViewWithDigimons(digimon: List<Digimon>) {
        val list = mutableListOf<Item>()
        for (character in digimon) {
            val name = character.name
            val level = character.level
            val item = Item("Name: $name", "Level: $level", character.img)
            list.add(item)
        }
        runOnUiThread {
            binding.recyclerView.adapter = ItemAdapter(list)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getRickAndMortyCharacters() {
        GlobalScope.launch {
            val response = rickAndMortyService.getAllCharacters()
            if (response.isSuccessful) {
                response.body()?.let { recyclerViewWithRickAndMorty(it.results) }
            }
        }
    }

    private fun recyclerViewWithRickAndMorty(characters: List<Character>) {
        val list = mutableListOf<Item>()
        for (character in characters) {
            val name = character.name
            val gender = character.gender
            val item = Item("Name: $name", "Gender: $gender", character.image)
            list.add(item)
        }
        runOnUiThread {
            binding.recyclerView.adapter = ItemAdapter(list)
        }
    }

    private fun updateBanner() {
        val sharedPreferences =
            getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        binding.mainWelcome.text =
            getString(R.string.welcome, sharedPreferences.getString("email", "Unknown"))
        binding.mainLastUpdate.text =
            getString(R.string.last_update, sharedPreferences.getString("date", "Unknown"))
    }
}