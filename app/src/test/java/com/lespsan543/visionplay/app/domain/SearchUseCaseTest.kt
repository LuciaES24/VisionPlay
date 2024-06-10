package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var searchUseCase: SearchUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        searchUseCase = SearchUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.searchMovieOrSerie("") } returns MovieOrSerieResponseState()
        //When
        searchUseCase("")
        //Then
        coVerify(exactly = 1) { appRepository.searchMovieOrSerie("") }
    }
}