package com.kenowa.approom.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenowa.approom.AppROOM
import com.kenowa.approom.R
import com.kenowa.approom.model.Deudor
import com.kenowa.approom.model.DeudorDAO

class ListFragment : Fragment() {

    private var allDeudores: List<Deudor> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val rvDeudores = root.findViewById<RecyclerView>(R.id.rv_deudores)

        rvDeudores.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        rvDeudores.setHasFixedSize(true)

        val deudorDAO : DeudorDAO = AppROOM.database.DeudorDAO()
        allDeudores=deudorDAO.getDeudores()

        val deudoresRVAdapter = DeudoresRVAdapter(
            requireActivity().applicationContext,
            allDeudores as ArrayList<Deudor>
        )
        rvDeudores.adapter = deudoresRVAdapter

        deudoresRVAdapter.notifyDataSetChanged()

        return root
    }
}