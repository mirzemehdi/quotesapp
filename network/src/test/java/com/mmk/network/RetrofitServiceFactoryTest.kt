package com.mmk.network

import com.google.common.truth.Truth.assertThat
import com.mmk.network.RetrofitServiceFactory.createApiService
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class RetrofitServiceFactoryTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @DisplayName("Given the same base url")
    @Nested
    inner class SameBaseUrl {
        val baseUrl = "https://test.com"

        @DisplayName("When Getting instance in Single Thread")
        @Nested
        inner class SameThread {
            private val retrofit1 = RetrofitServiceFactory.getInstance(baseUrl)
            private val retrofit2 = RetrofitServiceFactory.getInstance(baseUrl)

            @Test
            @DisplayName("Then same instance is returned")
            fun verifyAreTheSameInstances() {
                assertThat(retrofit1.hashCode()).isEqualTo(retrofit2.hashCode())
                assertThat(retrofit1).isEqualTo(retrofit2)
                assertThat(retrofit1 === retrofit2).isTrue()
            }
        }

        @DisplayName("When Getting instance in Multi Thread")
        @Nested
        inner class MultiThread {
            @Test
            @DisplayName("Then same instance is returned")
            fun verifyAreTheSameInstances() {
                var retrofit1: Retrofit? = null
                var retrofit2: Retrofit? = null
                callFunctionInMultiThread(
                    doOnFirstThread = {
                        retrofit1 = RetrofitServiceFactory.getInstance(baseUrl)
                    }, doOnEachThread = {
                        retrofit2 = RetrofitServiceFactory.getInstance(baseUrl)
                    })
                assertThat(retrofit1.hashCode()).isEqualTo(retrofit2.hashCode())
                assertThat(retrofit1).isEqualTo(retrofit2)
                assertThat(retrofit1 === retrofit2).isTrue()
            }


            //Creates 1000 thread to attempt race condition in function
            private fun callFunctionInMultiThread(
                doOnFirstThread: () -> Unit,
                doOnEachThread: () -> Unit
            ) {
                val nbThreads = 1000
                val executorService: ExecutorService = Executors.newFixedThreadPool(nbThreads)
                val countDownLatch = CountDownLatch(nbThreads)
                for (i in 0 until nbThreads) {
                    executorService.execute {
                        if (i == 0) doOnFirstThread()
                        doOnEachThread()
                        countDownLatch.countDown()
                    }
                }
                countDownLatch.await()

            }

        }

    }

    @DisplayName("Given wrong formatted URL")
    @Nested
    inner class WrongFormattedUrl {
        private val baseUrl = "testUrl"

        @DisplayName("When getting instance, Then throws IllegalArgument exception")
        @Test
        fun verifyThrowsException() {
            assertThrows(java.lang.IllegalArgumentException::class.java) {
                RetrofitServiceFactory.getInstance(baseUrl)
            }
        }


    }

    @DisplayName("Given different base url")
    @Nested
    inner class DifferentBaseUrl {
        private val baseUrl1 = "https://test1.com"
        private val baseUrl2 = "https://test2.com"


        private val retrofit1 = RetrofitServiceFactory.getInstance(baseUrl1)
        private val retrofit2 = RetrofitServiceFactory.getInstance(baseUrl2)

        @Test
        @DisplayName("When Getting instance, Then difference instance is returned")
        fun verifyDifferentInstances() {
            assertThat(retrofit1).isNotEqualTo(retrofit2)
            assertThat(retrofit1.hashCode()).isNotEqualTo(retrofit2.hashCode())
            assertThat(retrofit1 === retrofit2).isFalse()
        }


    }

    @DisplayName("Given retrofit object")
    @Nested
    inner class CreatedRetrofitObject {
        val retrofit = RetrofitServiceFactory.getInstance("https://test.com")


        @DisplayName("When create new API service, then it is not null")
        @Test
        fun verifyApiServiceIsCreatedAndNotNull() {
            val newApiService = retrofit.createApiService<TestInterface>()
            assertThat(newApiService).isNotNull()
            assertThat(newApiService).isInstanceOf(TestInterface::class.java)
        }


    }

    internal interface TestInterface {
        fun testMethod()
    }
}