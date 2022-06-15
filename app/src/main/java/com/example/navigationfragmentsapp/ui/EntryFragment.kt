package com.example.navigationfragmentsapp.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationfragmentsapp.R
import com.example.navigationfragmentsapp.databinding.FragmentEntryBinding
import com.example.navigationfragmentsapp.model.Event
import java.text.SimpleDateFormat

class EntryFragment : Fragment() {



    private val binding by lazy {
        FragmentEntryBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val SimpleF=SimpleDateFormat("MM/dd/yyyy")
        var date:String

        binding.calendarEvent.setOnDateChangeListener{view,year,month,dayOfMonth->
            date="${month+1}/$dayOfMonth/$year"

            Toast.makeText(this.context,date,Toast.LENGTH_SHORT).show()


        }.let{date=SimpleF.format(binding.calendarEvent.date)}

        binding.eventNameEntry.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.saveEventBtn.isEnabled = p0?.isNotEmpty() ?: false
            }

            override fun afterTextChanged(p0: Editable?) {
                // no-op
            }

        })

        binding.saveEventBtn.setOnClickListener {
            val name = binding.eventNameEntry.text.toString()
            val category = binding.eventCategoryEntry.text.toString()

        }

        binding.saveEventBtn.setOnClickListener {
            val name = binding.eventNameEntry.text.toString()
            val category = binding.eventCategoryEntry.text.toString()

          val d = SimpleF.parse(date)
          val milliseconds: Long = d.getTime()

            Event(name, category, date, milliseconds).also {//
                findNavController().navigate(
                    R.id.action_EntryFragment_to_MainFragment, bundleOf(
                        Pair(EVENT_DATA, it)
                    ))
            }
        }

        return binding.root
    }

    companion object {
        const val EVENT_DATA = "EVENT_DATA"
    }
}