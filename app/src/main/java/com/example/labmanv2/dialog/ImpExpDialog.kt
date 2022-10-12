package com.example.labmanv2.dialog

import MyViewModelFactory
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.labmanv2.R
import roomdb.StudentGroupViewModel

class ImpExpDialog(): DialogFragment()  {

    private lateinit var listener: ImpExpListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.imp_exp_dialog, container, false)
        var impbutt: Button? = view.findViewById(R.id.imp_button)
        var expjbutt: Button? = view.findViewById(R.id.expj_butt)
        var expebutt: Button? = view.findViewById(R.id.expe_butt)

        var vm: StudentGroupViewModel =  ViewModelProvider(this, MyViewModelFactory(this.activity?.application as Application))[StudentGroupViewModel::class.java]

        impbutt?.setOnClickListener {l->
            listener.onImp()
            this.dismiss()
        }

        expjbutt?.setOnClickListener {l->
            listener.OnExpJ()
            this.dismiss()
        }

        expebutt?.setOnClickListener {l->
            listener.OnExpE()
            this.dismiss()
        }
        return view
    }


    fun setListener(listener: ImpExpListener) {
        this.listener = listener
    }

    interface ImpExpListener {
        fun onImp()
        fun OnExpJ()
        fun OnExpE()
    }
}