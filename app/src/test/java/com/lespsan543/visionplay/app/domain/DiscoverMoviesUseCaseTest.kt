package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.MovieOrSerieResponseState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DiscoverMoviesUseCaseTest{

    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var discoverMoviesUseCase: DiscoverMoviesUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        discoverMoviesUseCase = DiscoverMoviesUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.discoverMovies(0) } returns MovieOrSerieResponseState()
        //When
        discoverMoviesUseCase(0)
        //Then
        coVerify(exactly = 1) { appRepository.discoverMovies(0) }
    }
}