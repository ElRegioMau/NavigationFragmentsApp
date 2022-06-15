package com.example.navigationfragmentsapp.adapter


import android.service.autofill.UserData
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.example.navigationfragmentsapp.databinding.TodoItemBinding
import com.example.navigationfragmentsapp.model.Event
import com.example.navigationfragmentsapp.ui.MainFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*


class EventAdapter(

    private val onEventClickHandler: MainFragment,
    private val eventsList: MutableList<Event> = SINGLETON.list,
   // private val sorting: List<Event> =SINGLETON.sortedList,//check
    private val onClickEventHighOrderFunction: (Event) -> Unit

) : RecyclerView.Adapter<EventViewHolder>() {



    fun updateEventsList(event: Event) {//I have to add something to edit data
        eventsList.add(event)
        //readFireStoreData()

       val ss= eventsList.sortedBy { it.date }
            SINGLETON.list.clear()
            ss.forEach{
                SINGLETON.list.add(it)
            }

            notifyDataSetChanged()
        notifyItemInserted(eventsList.indexOf(event))
        saveFireData(event.category,event.date,event.name)

    
    }
//
    private fun saveFireData(category:String,date:String,name:String){

        val db = FirebaseFirestore.getInstance()
        val events:MutableMap<String,Any> = HashMap()
            events["name"] = name
            events["date"] = date
            events["category"] = category


        db.collection("events")
            .add(events)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }



    }
   /** fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("events")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()
                eventsList.clear()

                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        val name= document.data.getValue("name").toString()
                        val category = document.data.getValue("category").toString()
                        val date = document.data.getValue("date").toString()
                        val mili  = document.data.getValue("mili").toString().toLong()
                        eventsList.add(Event(name,category,date,mili))
                    }

                }
            }

    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
        holder.bind(eventsList[position], onEventClickHandler, onClickEventHighOrderFunction)

    override fun getItemCount(): Int {

        return eventsList.size}
}

class EventViewHolder(
    private val binding: TodoItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: Event, onEventClickHandler: ClickHandler, onClickEventHighOrderFunction: (Event) -> Unit) {
        binding.eventName.text = event.name
        binding.eventCategory.text = event.category
        binding.eventDate.text = event.date


binding.editSettings.setOnClickListener {

    binding.eventName.text = event.name
    binding.eventCategory.text = event.category
    binding.eventDate.text = event.date
    //saveData()



            onClickEventHighOrderFunction(event)//this calls line 19
        }
    }
}