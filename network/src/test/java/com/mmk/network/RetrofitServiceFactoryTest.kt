package com.mmk.network

import com.google.common.truth.Truth.assertThat
import com.mmk.network.RetrofitServiceFactory.createApiService
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class RetrofitServiceFactoryTest {

    @DisplayName("Given the same base url")
    @Nested
    inner class SameBaseUrl {

        @DisplayName("When Getting instance in Single Thread")
        @Nested
        inner class SameThread {
            private val dummyBaseUrl = "http://testsamethread.com"

            private val retrofit1 = RetrofitServiceFactory.getInstance(dummyBaseUrl)
            private val retrofit2 = RetrofitServiceFactory.getInstance(dummyBaseUrl)

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
            private val dummyBaseUrl = "http://testmultithread.com"

            @Test
            @DisplayName("Then same instance is returned")
            fun verifyAreTheSameInstances() {
                var retrofit1: Retrofit? = null
                var retrofit2: Retrofit? = null
                callFunctionInMultiThread(
                    doOnFirstThread = {
                        retrofit1 = RetrofitServiceFactory.getInstance(dummyBaseUrl)
                    }, doOnEachThread = {
                        retrofit2 = RetrofitServiceFactory.getInstance(dummyBaseUrl)
                    }
                )
                assertThat(retrofit1.hashCode()).isEqualTo(retrofit2.hashCode())
                assertThat(retrofit1).isEqualTo(retrofit2)
                assertThat(retrofit1 === retrofit2).isTrue()
            }

            // Creates 1000 thread to attempt race condition in function
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
        private val baseUrl = "wrongFormattedUrl"

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
        private val baseUrl1 = "https://testdifferentbase1.com"
        private val baseUrl2 = "https://testdifferentbase2.com"

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
        private val dummyBaseUrl = "http://testcreatedretrofit.com"

        val retrofit = RetrofitServiceFactory.getInstance(dummyBaseUrl)

        @DisplayName("When create new API service, then it is not null")
        @Test
        fun verifyApiServiceIsCreatedAndNotNull() {
            val newApiService = retrofit.createApiService<TestInterface>()
            assertThat(newApiService).isNotNull()
            assertThat(newApiService).isInstanceOf(TestInterface::class.java)
        }
    }


    @DisplayName("Given logging parameter")
    @Nested
    inner class GivenLoggingParameter {
        private var okHttpBuilder: OkHttpClient.Builder


        init {
            mockkConstructor(OkHttpClient.Builder::class)
            okHttpBuilder = OkHttpClient.Builder()
        }

        @DisplayName("When logging is enabled")
        @Nested
        inner class LoggingEnabled {
            private val dummyBaseUrl = "http://testloggingenabled.com"
            private val isLoggingEnabled = true

            val retrofit = RetrofitServiceFactory.getInstance(
                dummyBaseUrl, isLoggingEnabled = isLoggingEnabled
            )

            @DisplayName("Then LoggingInterceptor is added to Retrofit")
            @Test
            fun verifyLoggingInterceptorAdded() {
                verify {
                    okHttpBuilder.addInterceptor(any() as HttpLoggingInterceptor)
                }
            }

        }

        @DisplayName("When logging is disabled")
        @Nested
        inner class LoggingDisabled {
            private val dummyBaseUrl = "http://testloggingdisabled.com"
            private val isLoggingEnabled = false
            val retrofit = RetrofitServiceFactory.getInstance(
                dummyBaseUrl, isLoggingEnabled = isLoggingEnabled
            )

            @DisplayName("Then LoggingInterceptor is added to Retrofit")
            @Test
            fun verifyLoggingInterceptorIsNotAdded() {
                verify(exactly = 0) {
                    okHttpBuilder.addInterceptor(any() as HttpLoggingInterceptor)
                }
            }

        }


    }

    internal interface TestInterface {
        fun testMethod()
    }
}
