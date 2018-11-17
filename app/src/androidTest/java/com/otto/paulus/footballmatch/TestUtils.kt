package com.otto.paulus.footballmatch

fun loadJSON(clazz: Class<Any>, path: String): String {
    val jsonFile = clazz.classLoader!!.getResourceAsStream(path)
    return String(jsonFile.readBytes())
}