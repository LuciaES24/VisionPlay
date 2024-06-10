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

class GetMovieGenresUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var getMovieGenresUseCase: GetMovieGenresUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getMovieGenresUseCase = GetMovieGenresUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.getMovieGenres() } returns emptyMap<String, String>() as MutableMap
        //When
        getMovieGenresUseCase()
        //Then
        coVerify(exactly = 1) { appRepository.getMovieGenres() }
    }
}