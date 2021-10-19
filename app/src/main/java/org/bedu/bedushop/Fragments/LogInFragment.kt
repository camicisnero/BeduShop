package org.bedu.bedushop.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.google.android.material.textfield.TextInputLayout
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import org.bedu.bedushop.R
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern


class LogInFragment : Fragment() {

    private lateinit var metEmail : TextInputLayout
    private lateinit var metPassword : TextInputLayout
    private lateinit var btnLogin : Button
    private lateinit var tvRegister : TextView

    private val args: LogInFragmentArgs by navArgs()

    private val url = "https://reqres.in/api/login"

    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        metEmail = view.findViewById(R.id.metEmail)
        metPassword = view.findViewById(R.id.metPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        tvRegister = view.findViewById(R.id.tvRegister)

        val email = args.email
        val password = args.password
        metEmail.editText?.setText(email)
        metPassword.editText?.setText(password)

        btnLogin.setOnClickListener {
            if (!metEmail.editText?.text.toString().isNotBlank() || !metPassword.editText?.text.toString().isNotBlank() ){
                Snackbar.make(view, "Te falta llenar algun campo", Snackbar.LENGTH_INDEFINITE)
                    .setAction("ENTENDIDO") {}
                    .show()
            } else if (!isValidEmail(metEmail.editText?.text.toString())){
                metEmail.error = getString(R.string.errorEmail)
            } else if (!isValidPassword(metPassword.editText?.text.toString())){
                metPassword.error = getString(R.string.errorPassword)
            } else {
                loginRequest(metEmail.editText?.text.toString(), metPassword.editText?.text.toString(), view)
            }
        }

        metEmail.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(metEmail.error?.isNotBlank() == true) metEmail.error = ""
            }
        })

        metPassword.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(metPassword.error?.isNotBlank() == true) metPassword.error = ""
            }
        })

        tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment, null, options)
        }

        return view
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_-])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun loginRequest(email: String, password: String, view: View) {
        val okHttpClient = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        val clientBuilder = okHttpClient.newBuilder()
        clientBuilder.build()
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(context, "ERROR REQUEST", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    try {
                        val json = JSONObject(body)
                        if(response.isSuccessful) {
                            findNavController().navigate(R.id.action_logInFragment_to_homeActivity)
                        } else {
                            Snackbar.make(view, R.string.userNotFound, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.snackbarAction) {}
                                .show()
                        }

                    } catch (e: Exception){
                        e.printStackTrace()
                    }

                }

            })
    }

}