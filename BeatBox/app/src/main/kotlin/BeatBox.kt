package com.example.beatbox

import android.content.res.AssetManager

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

class BeatBox(private val asserts: AssetManager) {

    val sounds: List<Sound>

    init {
        sounds = loadSounds()
    }

    fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = asserts.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            sounds.add(sound)
        }
        return sounds
    }
}