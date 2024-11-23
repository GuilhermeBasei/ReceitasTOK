package com.example.receitastok

data class Receita(
    val titulo: String = "",
    val imagemUrl: String = "",
    val ingredientes: List<String> = listOf(),
    val instrucoes: List<String> = listOf()
)
