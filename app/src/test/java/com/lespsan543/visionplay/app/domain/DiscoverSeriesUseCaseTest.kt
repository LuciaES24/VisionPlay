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

class DiscoverSeriesUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var discoverSeriesUseCase: DiscoverSeriesUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        discoverSeriesUseCase = DiscoverSeriesUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.discoverSeries(0) } returns MovieOrSerieResponseState()
        //When
        discoverSeriesUseCase(0)
        //Then
        coVerify(exactly = 1) { appRepository.discoverSeries(0) }
    }
}