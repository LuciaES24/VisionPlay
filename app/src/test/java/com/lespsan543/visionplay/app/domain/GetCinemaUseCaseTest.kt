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

class GetCinemaUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var getCinemaUseCase: GetCinemaUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCinemaUseCase = GetCinemaUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.getCineMovies(0) } returns MovieOrSerieResponseState()
        //When
        getCinemaUseCase(0)
        //Then
        coVerify(exactly = 1) { appRepository.getCineMovies(0) }
    }
}