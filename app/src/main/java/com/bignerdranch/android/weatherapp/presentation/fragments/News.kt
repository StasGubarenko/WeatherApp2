package com.bignerdranch.android.weatherapp.presentation.fragments

/**
 * @author e.petrashko
 */
sealed interface News {
    object ShowError : News
    object NavigateForward: News
}