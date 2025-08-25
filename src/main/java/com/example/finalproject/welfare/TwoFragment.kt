//TwoFragment 기본!!!

package com.example.finalproject.welfare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    private val stageCodeMap = mapOf(
        "전체" to "",
        "영유아" to "001",
        "아동" to "002",
        "청소년" to "003",
        "청년" to "004",
        "중장년" to "005",
        "노년" to "006",
        "임신 · 출산" to "007"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)

        // 버튼 클릭 시 XmlFragment를 생성하고 교체
        binding.btnLoad.setOnClickListener {
            val selectedStage = binding.spinnerLifeStage.selectedItem.toString()
            val lifeCode = stageCodeMap[selectedStage] ?: ""
            loadXmlFragment(lifeCode)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // 앱 시작 시 기본 값으로 "임신 · 출산" 정보 보여주기
        loadXmlFragment("007")
    }

    private fun loadXmlFragment(lifeArray: String) {
        val fragment = XmlFragment.newInstance(lifeArray)
        childFragmentManager.beginTransaction()
            .replace(R.id.activity_content, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
