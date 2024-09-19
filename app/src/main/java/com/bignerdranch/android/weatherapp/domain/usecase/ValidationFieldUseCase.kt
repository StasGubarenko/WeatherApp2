package com.bignerdranch.android.weatherapp.domain.usecase

import java.util.regex.Pattern

class ValidationFieldUseCase {
    fun validate(input: String): Boolean {
        //Запрещается вводить цифры и кириллицу
        val pattern = Pattern.compile("^[a-zA-Z]+$")

        return pattern.matcher(input).matches()
    }

}