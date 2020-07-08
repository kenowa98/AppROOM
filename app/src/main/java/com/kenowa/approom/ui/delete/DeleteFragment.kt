package com.kenowa.approom.ui.delete

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kenowa.approom.AppROOM
import com.kenowa.approom.R
import com.kenowa.approom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_delete.*

class DeleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp_deudores.visibility = View.GONE
        bt_eliminar.visibility = View.GONE

        val deudorDAO: DeudorDAO = AppROOM.database.DeudorDAO()

        view.findViewById<Button>(R.id.bt_buscarDelete).setOnClickListener {
            val nombre = et_nombreDelete.text.toString()

            if (nombre.isEmpty()) {
                Toast.makeText(context, "Indique un nombre", Toast.LENGTH_SHORT).show()
            } else {
                val deudor = deudorDAO.buscarDeudor(nombre)

                if (deudor.isEmpty()) {
                    et_nombreDelete.setText("")
                    Toast.makeText(context, "No hay coincidencias", Toast.LENGTH_SHORT).show()
                } else {
                    context?.let { it1 -> hideKeyboardFrom(it1, view) }

                    sp_deudores.visibility = View.VISIBLE
                    bt_buscarDelete.visibility = View.GONE
                    bt_eliminar.visibility = View.VISIBLE

                    val opciones: MutableList<String> = ArrayList()
                    for ((contador, i) in deudor.withIndex()) {
                        opciones.add(contador, i.nombre)
                    }
                    opciones.add(0, "Elija el deudor")

                    val spinner: Spinner = view.findViewById(R.id.sp_deudores)
                    context?.let {
                        ArrayAdapter(
                            it,
                            R.layout.spinner_item,
                            opciones
                        ).also { adapter ->
                            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                            spinner.adapter = adapter
                        }
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.bt_eliminar).setOnClickListener {
            val opcion = sp_deudores.selectedItem.toString()

            if (opcion == "Elija el deudor") {
                Toast.makeText(context, "No ha elegido un deudor", Toast.LENGTH_SHORT).show()
            } else {
                val deudor = deudorDAO.buscarDeudorEspecifico(opcion)

                sp_deudores.visibility = View.GONE
                bt_buscarDelete.visibility = View.VISIBLE
                bt_eliminar.visibility = View.GONE

                et_nombreDelete.setText("")

                deudorDAO.borrarDeudor(deudor)

                Toast.makeText(context, "Deudor eliminado", Toast.LENGTH_SHORT).show()
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