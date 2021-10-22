package org.bedu.bedushop.fragments

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before


import org.junit.Test

class LogInFragmentTest {

    private lateinit var fragment: LogInFragment

    @Before
    fun setUp(){
        fragment = LogInFragment()
    }
    //Al ser estática la propiedad que usa para validar el email, devuelve null en pruebas unitarias
    //por ello probamos este método como prueba integrada
    @Test
    fun correctEmailReturnsTrue() {
        val result = fragment.isValidEmail("user@example.com")
        assertThat(result).isTrue()
    }

    @Test
    fun invalidEmailReturnsFalse(){
        val result = fragment.isValidEmail("uuuuuseeer")
        assertThat(result).isFalse()
    }

}