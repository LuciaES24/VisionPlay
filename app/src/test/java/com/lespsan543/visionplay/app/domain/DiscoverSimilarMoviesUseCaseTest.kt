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

class DiscoverSimilarMoviesUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var discoverSimilarMoviesUseCase: DiscoverSimilarMoviesUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        discoverSimilarMoviesUseCase = DiscoverSimilarMoviesUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.discoverSimilarMovies(0) } returns MovieOrSerieResponseState()
        //When
        discoverSimilarMoviesUseCase(0)
        //Then
        coVerify(exactly = 1) { appRepository.discoverSimilarMovies(0) }
    }
}