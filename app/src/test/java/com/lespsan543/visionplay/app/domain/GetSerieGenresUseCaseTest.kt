package com.lespsan543.visionplay.app.domain

import com.lespsan543.visionplay.app.data.AppRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetSerieGenresUseCaseTest{
    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var getSerieGenresUseCase: GetSerieGenresUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getSerieGenresUseCase = GetSerieGenresUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.getSerieGenres() } returns emptyMap<String, String>() as MutableMap
        //When
        getSerieGenresUseCase()
        //Then
        coVerify(exactly = 1) { appRepository.getSerieGenres() }
    }
}