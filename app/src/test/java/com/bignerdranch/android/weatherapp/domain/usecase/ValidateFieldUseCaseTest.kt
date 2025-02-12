package com.bignerdranch.android.weatherapp.domain.usecase

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ValidateFieldUseCaseTest{

    @Test
    fun test_input_lowercase_latin_letter(){
        general("l",true)
    }

    @Test
    fun test_input_capital_latin_letter(){
        general("L",true)
    }

    @Test
    fun test_input_space(){
        general(" ",false)
    }

    @Test
    fun test_input_lowercase_сyrillic_letter(){
        general("п",false)
    }

    @Test
    fun test_input_capital_сyrillic_letter(){
        general("П", false)
    }

    @Test
    fun test_input_capital_and_lowercase_сyrillic_letters(){
        general("Пп",false)
    }

    @Test
    fun test_input_lowercase_and_capital_сyrillic_letters(){
        general("пП",false)
    }

    @Test
    fun test_input_lowercase_and_capital_latin_letters(){
        general("lL",true)
    }

    @Test
    fun test_input_capital_and_lowercase_latin_letters(){
        general("Ll",true)
    }

    @Test
    fun test_input_capital_and_lowercase_latin_letters_by_a_space(){
        general("L l",false)
    }

    @Test
    fun test_input_lowercase_and_capital_latin_letters_by_a_space(){
        general("l L",false)
    }

    @Test
    fun test_input_two_lowercase_latin_letters_by_a_space(){
        general("l l",false)
    }

    @Test
    fun test_input_two_capital_latin_letters_by_a_space(){
        general("L L",false)
    }

    @Test
    fun test_input_a_symbol(){
        general("+",false)
    }

    @Test
    fun test_input_a_number(){
        general("1",false)
    }

    @Test
    fun test_input_a_latin_and_a_number(){
        general("r1",false)
    }

    @Test
    fun test_input_a_latin_and_a_symbol(){
        general("r+",false)
    }

    private fun general(input: String, isValid : Boolean){
        val validateFieldUseCase = ValidateFieldUseCase()
        val actual = validateFieldUseCase.validate(input)
        if (isValid){
            assertTrue(actual)
        }else{
            assertFalse(actual)
        }
    }
}


