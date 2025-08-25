package com.example.finalproject.note

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentThreeBinding
import com.example.finalproject.login.MyApplication
import com.example.finalproject.note.AddActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.jvm.java

class ThreeFragment : Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeBinding.inflate(inflater, container, false)

        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                // AddActivity를 호출
                val intent = Intent(requireContext(),AddActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(),"인증을 먼저 해주세요.",Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        MyApplication.db.collection("feeling")
            .get()
            .addOnSuccessListener { result ->
                var itemList = mutableListOf<ItemData>()
                for(document in result){
                    val item= document.toObject(ItemData::class.java) // email, date, content
                    item.docId = document.id
                    itemList.add(item)
                }
                Log.d("25android", "불러온 데이터 수: ${itemList.size}")

                binding.threeRecyclerView.layoutManager = LinearLayoutManager(context)
                binding.threeRecyclerView.adapter = MyAdapter(itemList)

            }
            .addOnFailureListener {
                Log.e("25android", "데이터 불러오기 실패: ${it.message}")
                Toast.makeText(context, "Firestore로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        //loadDataFromFirebase() // Firestore에서 데이터 받아와 RecyclerView에 연결
    }

}
