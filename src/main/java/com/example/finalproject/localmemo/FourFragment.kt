package com.example.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentFourBinding
import com.example.finalproject.localmemo.Memo
import com.example.finalproject.localmemo.MemoAdapter
import com.example.finalproject.localmemo.MemoDatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class FourFragment : Fragment() {

    private var _binding: FragmentFourBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: MemoDatabaseHelper
    private lateinit var adapter: MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourBinding.inflate(inflater, container, false)
        dbHelper = MemoDatabaseHelper(requireContext())

        adapter = MemoAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        loadMemos()

        binding.btnSave.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val content = binding.editContent.text.toString()
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            if (title.isNotEmpty() && content.isNotEmpty()) {
                dbHelper.insertMemo(title, content, date)
                loadMemos()
                binding.editTitle.text.clear()
                binding.editContent.text.clear()
                Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "내용을 모두 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loadMemos() {
        val list = dbHelper.getAllMemos()
        adapter.updateData(list)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
