package com.kenowa.approom.ui.read

import android.annotation.SuppressLint
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
import com.kenowa.approom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_read.*

@Suppress("SENSELESS_COMPARISON")
class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.bt_buscarRead).setOnClickListener {
            val nombre = et_nombreRead.text.toString()

            if(nombre.isEmpty()){
                Toast.makeText(context, "Indique un nombre", Toast.LENGTH_SHORT).show()
            } else {
                val deudorDAO: DeudorDAO = AppROOM.database.DeudorDAO()
                val deudor = deudorDAO.buscarDeudor(nombre)

                et_nombreRead.setText("")

                if(deudor.isEmpty()){
                    tv_resultado.text = ""
                    Toast.makeText(context, "No hay coincidencias", Toast.LENGTH_SHORT).show()
                } else {
                    context?.let { it1 -> hideKeyboardFrom(it1, view) }
                    if(deudor.size == 1){
                        Toast.makeText(context, "Hay 1 resultado", Toast.LENGTH_SHORT).show()
                        tv_resultado.text = "Nombre: ${deudor[0].nombre}\n" +
                                            "Teléfono: ${deudor[0].telefono}\n" +
                                            "Cantidad: ${deudor[0].cantidad}"
                    } else {
                        val personas = deudor.size
                        var flag = false
                        Toast.makeText(context, "Hay $personas resultados", Toast.LENGTH_SHORT).show()
                        for (i in deudor) {
                            if (flag) {
                                tv_resultado.append("\n\nNombre: ${i.nombre}\n" +
                                        "Teléfono: ${i.telefono}\n" +
                                        "Cantidad: ${i.cantidad}")
                            } else {
                                flag = !flag
                                tv_resultado.text = "Nombre: ${i.nombre}\n" +
                                        "Teléfono: ${i.telefono}\n" +
                                        "Cantidad: ${i.cantidad}"
                            }
                        }
                    }
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