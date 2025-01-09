package com.bignerdranch.android.weatherapp.domain.usecase

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ValidationFieldUseCaseTest{

    @Test
    fun test_input_lowercase_latin_letter(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "l"
        val actual = validationFieldUseCase.validate(input)
        assertTrue(actual)
    }

    @Test
    fun test_input_capital_latin_letter(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "L"
        val actual = validationFieldUseCase.validate(input)
        assertTrue(actual)
    }

    @Test
    fun test_input_space(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = " "
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_lowercase_сyrillic_letter(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "п"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_capital_сyrillic_letter(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "П"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_capital_and_lowercase_сyrillic_letters(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "Пп"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_lowercase_and_capital_сyrillic_letters(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "пП"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_lowercase_and_capital_latin_letters(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "lL"
        val actual = validationFieldUseCase.validate(input)
        assertTrue(actual)
    }

    @Test
    fun test_input_capital_and_lowercase_latin_letters(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "Ll"
        val actual = validationFieldUseCase.validate(input)
        assertTrue(actual)
    }

    @Test
    fun test_input_capital_and_lowercase_latin_letters_by_a_space(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "L l"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_lowercase_and_capital_latin_letters_by_a_space(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "l L"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_two_lowercase_latin_letters_by_a_space(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "l l"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_two_capital_latin_letters_by_a_space(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "L L"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_a_symbol(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "+"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_a_number(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "1"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_a_latin_and_a_number(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "r1"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }

    @Test
    fun test_input_a_latin_and_a_symbol(){
        val validationFieldUseCase = ValidationFieldUseCase()
        val input = "r+"
        val actual = validationFieldUseCase.validate(input)
        assertFalse(actual)
    }
}


