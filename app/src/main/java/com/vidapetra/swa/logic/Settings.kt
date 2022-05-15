package com.vidapetra.swa.logic

data class Settings(
    var celsius: Boolean,
    var auto: Boolean,
    var dark: Boolean,
    var language: String,
    var notification: Boolean,
    var locale: Boolean,
    var position: String
)