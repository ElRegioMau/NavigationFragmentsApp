package com.example.navigationfragmentsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationfragmentsapp.R
import com.example.navigationfragmentsapp.adapter.ClickHandler
import com.example.navigationfragmentsapp.adapter.EventAdapter
import com.example.navigationfragmentsapp.databinding.FragmentMainBinding
import com.example.navigationfragmentsapp.model.Event
import com.google.firebase.database.*


class MainFragment : Fragment(), ClickHandler {

    /**private lateinit var userReciclerView: RecyclerView
    private lateinit var eventsList: MutableList<Event>
    private lateinit var db: DatabaseReference*/

    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val eventAdapter by lazy {
        EventAdapter(this) { event ->
            findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundleOf(Pair(EntryFragment.EVENT_DATA, event))
            )
        }
    }

    private var newEvent: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //loadData()

        arguments?.let {
            newEvent = it.getSerializable(EntryFragment.EVENT_DATA) as? Event
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.todoRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            adapter = eventAdapter
        }

        binding.createEvent.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_EntryFragment)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        newEvent?.let {
            eventAdapter.updateEventsList(it)
            newEvent = null
            arguments = null
        }
    }

    override fun onEventItemClick(event: Event) {
        findNavController().navigate(
            R.id.action_mainFragment_to_detailsFragment,
            bundleOf(Pair(EntryFragment.EVENT_DATA, event))
        )
    }
   /** private fun loadData(){

        db= FirebaseDatabase.getInstance().getReference("events")
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){

                    if(snapshot.exists()){
                        for(userSnapshot in snapshot.children){
                            val datos = userSnapshot.getValue(Event::class.java)
                            eventsList.add(datos!!)
                        }
                        userReciclerView.adapter=EventAdapter()


                    }


                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })




    }*/

}