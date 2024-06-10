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

class GetTrailerUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var getTrailerUseCase: GetTrailerUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getTrailerUseCase = GetTrailerUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.getYoutubeTrailer("") } returns ""
        //When
        getTrailerUseCase("")
        //Then
        coVerify(exactly = 1) { appRepository.getYoutubeTrailer("") }
    }
}