package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import com.lespsan543.visionplay.app.ui.states.ProviderState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DiscoverMovieProviderUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var discoverMovieProviderUseCase: DiscoverMovieProviderUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        discoverMovieProviderUseCase = DiscoverMovieProviderUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.getMovieProvider(0) } returns ProviderState()
        //When
        discoverMovieProviderUseCase(0)
        //Then
        coVerify(exactly = 1) { appRepository.getMovieProvider(0) }
    }
}