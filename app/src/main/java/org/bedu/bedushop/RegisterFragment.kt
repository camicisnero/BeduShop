package org.bedu.bedushop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.textfield.TextInputLayout
/*Fragment register*/
class RegisterFragment : Fragment() {

    private lateinit var ti_fullname : TextInputLayout
    private lateinit var ti_email : TextInputLayout
    private lateinit var ti_phone : TextInputLayout
    private lateinit var ti_password : TextInputLayout
    private lateinit var btn_register : Button

    val options = navOptions {
        anim {
            enter = R.anim.slide_in_left
            exit = R.anim.slide_out_right
            popEnter = R.anim.slide_in_right
            popExit = R.anim.slide_out_left
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        ti_fullname = view.findViewById(R.id.et_regName)
        ti_email = view.findViewById(R.id.et_regEmail)
        ti_phone = view.findViewById(R.id.et_regPhone)
        ti_password = view.findViewById(R.id.et_regPassword)
        btn_register = view.findViewById(R.id.btn_register)

        btn_register.setOnClickListener {
            if (ti_fullname.editText?.text.toString().isBlank()){
                ti_fullname.error = getString(R.string.validInput)
            } else if (ti_email.editText?.text.toString().isBlank()){
                ti_email.error = getString(R.string.validInput)
            } else if (ti_phone.editText?.text.toString().isBlank()){
                ti_phone.error = getString(R.string.errorEmail)
            } else if (ti_password.editText?.text.toString().isBlank()){
                ti_password.error = getString(R.string.errorEmail)
            } else {
                val action = RegisterFragmentDirections.actionRegisterFragmentToLogInFragment(ti_email.editText?.text.toString())
                findNavController().navigate(action, options)
            }
        }


        return view
    }
}