package ru.tomindapps.spidertest

import org.junit.Test

import org.junit.Before
import ru.tomindapps.spidertest.network.ApiLoader

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var nu:ApiLoader
    @Before
    fun prepare(){
        nu = ApiLoader()

    }
    @Test
    fun addition_isCorrect() {
        val s = nu.makeGalerySearch()
        println(s)
        val a = nu.parseGaleryJson(s!!)
        println(a[0].link)
    }
}
