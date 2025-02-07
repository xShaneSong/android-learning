package com.example.beatbox

import android.content.res.AssetManager
import android.media.SoundPool

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

class BeatBox(private val asserts: AssetManager) {

    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

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
//            sounds.add(sound)
            try {
                load(sound)
                sounds.add(sound)
            } catch (e: Exception) {
                // log error
            }
        }
        return sounds
    }
    private fun load(sound: Sound) {
        val afd = asserts.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }
    fun release() {
        soundPool.release()
    }
}