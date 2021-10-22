package org.bedu.bedushop

import android.util.Log
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

object RegisterUtil {

    /**
     * El teléfono no es válido si:
     * - tiene menos de 10 caracteres
     * - tiene letras
     */

    fun isValidPhone(target: CharSequence): Boolean{
        val pattern: Pattern
        val PHONE_PATTERN = "^[+]?[0-9]{10,13}$"
        pattern = Pattern.compile(PHONE_PATTERN)
        val matcher: Matcher = pattern.matcher(target)
        return target.isNotBlank() && matcher.matches()
    }

    /**
     * La contraseña no es válida si:
     * -la longitud de la contraseña es menor que 8
     * -la contraseña no tiene una mayúscula, una minúscula y un caracter especial
     * -la contraseña tiene espacios
     */
    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_-])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

}