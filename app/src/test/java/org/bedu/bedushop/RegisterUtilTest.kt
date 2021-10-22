package org.bedu.bedushop

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegisterUtilTest{

    @Test
    fun `empty password returns false`(){
        val result = RegisterUtil.isValidPassword(" ")
        assertThat(result).isFalse()
    }

    @Test
    fun `password length less than 8 characters returns false`(){
        val result = RegisterUtil.isValidPassword("qawDFed")
        assertThat(result).isFalse()
    }

    @Test
    fun `password contains blank spaces returns false`(){
        val result = RegisterUtil.isValidPassword("as df as")
        assertThat(result).isFalse()
    }

    @Test
    fun `correct password returns true`(){
        val result = RegisterUtil.isValidPassword("asdf-AaaA")
        assertThat(result).isTrue()
    }

    @Test
    fun `phone length less than 10 characters returns false`(){
        val result = RegisterUtil.isValidPhone("5438745")
        assertThat(result).isFalse()
    }

    @Test
    fun `phone length more than 13 characters returns false`(){
        val result = RegisterUtil.isValidPhone("5438745999999999")
        assertThat(result).isFalse()
    }

    @Test
    fun `phone with letters returns false`(){
        val result = RegisterUtil.isValidPhone("54384a74543")
        assertThat(result).isFalse()
    }

    @Test
    fun `correct phone returns true`(){
        val result = RegisterUtil.isValidPhone("543874555666")
        assertThat(result).isTrue()
    }

}