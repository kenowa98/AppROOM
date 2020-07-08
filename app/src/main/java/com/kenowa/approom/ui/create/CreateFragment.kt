package com.kenowa.approom.ui.create

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kenowa.approom.AppROOM
import com.kenowa.approom.R
import com.kenowa.approom.model.Deudor
import com.kenowa.approom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_create.*
import java.sql.Types.NULL


class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.bt_guardar).setOnClickListener {
            val nombre = et_nombreCreate.text.toString()
            val telefono = et_telefonoCreate.text.toString()
            val cantidad = et_cantidadCreate.text.toString()

            if(nombre.isEmpty() || telefono.isEmpty() || cantidad.isEmpty()){
                Toast.makeText(context, "Faltan Datos", Toast.LENGTH_SHORT).show()
            } else {
                if (telefono.length == 10) {
                    val valor = cantidad.toLong()

                    val deudor = Deudor(NULL, nombre, telefono, valor)

                    val deudorDAO :DeudorDAO = AppROOM.database.DeudorDAO()

                    deudorDAO.crearDeudor(deudor)

                    et_nombreCreate.setText("")
                    et_telefonoCreate.setText("")
                    et_cantidadCreate.setText("")

                    context?.let { it1 -> hideKeyboardFrom(it1, view) }

                    Toast.makeText(context, "Deudor Guardado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "El número de celular debe tener 10 dígitos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun hideKeyboardFrom(
        context: Context,
        view: View
    ) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}