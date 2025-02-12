package com.bignerdranch.android.weatherapp.presentation.state

/**
 * @author e.petrashko
 */
sealed interface News {
    object ShowError : News
    object NavigateForward: News
}