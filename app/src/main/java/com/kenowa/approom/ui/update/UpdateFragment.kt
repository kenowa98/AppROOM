package com.kenowa.approom.ui.update

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
import com.kenowa.approom.model.Deudor
import com.kenowa.approom.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_update.*


@Suppress("SENSELESS_COMPARISON", "NAME_SHADOWING")
class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_telefonoUpdate.visibility = View.GONE
        et_cantidadUpdate.visibility = View.GONE
        tv_extension.visibility = View.GONE
        bt_actualizar.visibility = View.GONE
        sp_opciones.visibility = View.GONE
        bt_buscar2Update.visibility = View.GONE

        var idDeudor = 0
        val deudorDAO: DeudorDAO = AppROOM.database.DeudorDAO()

        view.findViewById<Button>(R.id.bt_buscarUpdate).setOnClickListener {
            val nombre = et_nombreUpdate.text.toString()

            if(nombre.isEmpty()){
                Toast.makeText(context, "Indique un nombre", Toast.LENGTH_SHORT).show()
            } else {
                val deudor = deudorDAO.buscarDeudor(nombre)

                if(deudor.isEmpty()){
                    et_nombreUpdate.setText("")
                    Toast.makeText(context, "No hay coincidencias", Toast.LENGTH_SHORT).show()
                } else {
                    context?.let { it1 -> hideKeyboardFrom(it1, view) }
                    if(deudor.size == 1){
                        Toast.makeText(context, "Hay 1 resultado", Toast.LENGTH_SHORT).show()

                        et_telefonoUpdate.visibility = View.VISIBLE
                        et_cantidadUpdate.visibility = View.VISIBLE
                        tv_extension.visibility = View.VISIBLE
                        bt_actualizar.visibility = View.VISIBLE
                        bt_buscarUpdate.visibility = View.GONE

                        idDeudor = deudor[0].id
                        et_nombreUpdate.setText(deudor[0].nombre)
                        et_telefonoUpdate.setText(deudor[0].telefono)
                        et_telefonoUpdate.hint = R.string.celular.toString()
                        et_cantidadUpdate.setText(deudor[0].cantidad.toString())
                        et_cantidadUpdate.hint = R.string.cantidad.toString()
                    } else {
                        sp_opciones.visibility = View.VISIBLE
                        bt_buscarUpdate.visibility = View.GONE
                        bt_buscar2Update.visibility = View.VISIBLE

                        val personas = deudor.size
                        Toast.makeText(context, "Hay $personas resultados", Toast.LENGTH_SHORT).show()

                        val opciones : MutableList<String> = ArrayList()
                        for ((contador, i) in deudor.withIndex()) {
                            opciones.add(contador, i.nombre)
                        }
                        opciones.add(0, "Elija el deudor")

                        val spinner: Spinner = view.findViewById(R.id.sp_opciones)
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
        }

        view.findViewById<Button>(R.id.bt_buscar2Update).setOnClickListener {
            val opcion = sp_opciones.selectedItem.toString()

            if (opcion == "Elija el deudor") {
                Toast.makeText(context, "No ha elegido un deudor", Toast.LENGTH_SHORT).show()
            } else {
                val deudor = deudorDAO.buscarDeudorEspecifico(opcion)

                et_telefonoUpdate.visibility = View.VISIBLE
                et_cantidadUpdate.visibility = View.VISIBLE
                tv_extension.visibility = View.VISIBLE
                bt_actualizar.visibility = View.VISIBLE
                sp_opciones.visibility = View.GONE
                bt_buscar2Update.visibility = View.GONE

                et_telefonoUpdate.hint = R.string.celular.toString()
                et_cantidadUpdate.hint = R.string.cantidad.toString()

                idDeudor = deudor.id
                et_nombreUpdate.setText(deudor.nombre)
                et_telefonoUpdate.setText(deudor.telefono)
                et_cantidadUpdate.setText(deudor.cantidad.toString())
            }
        }

        view.findViewById<Button>(R.id.bt_actualizar).setOnClickListener {
            val nombre = et_nombreUpdate.text.toString()
            val telefono = et_telefonoUpdate.text.toString()
            val cantidad = et_cantidadUpdate.text.toString()

            if(nombre.isEmpty() || telefono.isEmpty() || cantidad.isEmpty()){
                Toast.makeText(context, "Faltan Datos", Toast.LENGTH_SHORT).show()
            } else {
                context?.let { it1 -> hideKeyboardFrom(it1, view) }

                val deudor = Deudor(idDeudor, nombre, telefono, cantidad.toLong())

                deudorDAO.actualizarDeudor(deudor)

                et_telefonoUpdate.visibility = View.GONE
                et_cantidadUpdate.visibility = View.GONE
                tv_extension.visibility = View.GONE
                bt_actualizar.visibility = View.GONE
                bt_buscarUpdate.visibility = View.VISIBLE

                et_nombreUpdate.setText("")
                et_telefonoUpdate.hint = ""
                et_cantidadUpdate.hint = ""

                Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show()
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