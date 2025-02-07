package com.example.beatbox

class Sound(val assetPath: String) {
    val name = assetPath.split("/").last().removeSuffix(".wav")
}