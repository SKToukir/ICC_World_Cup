package com.walton.waltonicc.models.example

import com.walton.waltonicc.models.example.Detail

data class Result(
    val details: List<Detail>,
    val id: Int,
    val title: String
)