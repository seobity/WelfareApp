package com.example.finalproject.welfare

import WelfareAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentXmlBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class XmlFragment : Fragment() {

    private lateinit var lifeArray: String
    private lateinit var adapter: WelfareAdapter
    private var _binding: FragmentXmlBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(lifeArray: String): XmlFragment {
            val fragment = XmlFragment()
            val args = Bundle()
            args.putString("lifeArray", lifeArray)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeArray = arguments?.getString("lifeArray") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WelfareAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        loadWelfareData()
    }
    private fun showDummyData() {
        val dummyList = listOf(
            WelfareItem("WLF00000061", "의료급여 임신.출산진료비지원", "임신 또는 출산한 의료급여 수급자에게 의료비 지원", "보건복지부", "영유아,임신 · 출산", "현금지급"),
            WelfareItem("WLF00001088", "고위험 임산부 의료비 지원", "고위험 임신 진료비 지원", "보건복지부", "임신 · 출산", "현금지급"),
            WelfareItem("WLF00003213", "인플루엔자 국가예방접종 지원사업", "어르신, 임신부, 어린이 인플루엔자 예방접종 지원", "질병관리청", "전 연령", "기타"),
            WelfareItem("WLF00003278", "여성장애인 출산비용지원", "여성장애인 출산비용 지원", "보건복지부", "임신 · 출산", "현금지급")
        )
        adapter.submitList(dummyList)
    }

    private fun loadWelfareData() {
        val serviceKey = "mrl0VZdVcapf7YIOh4FbXa6CpQD72OGocCDMdbSmhZCEvShglTeEvyG%2B5E%2BH7ZZRhUbSdxzqQ6WVKvvYkH5b3A%3D%3D"

        RetrofitConnection.xmlNetworkService.getWelfareData(
            serviceKey = serviceKey,
            lifeArray = lifeArray,
            callTp = "L",
            pageNo = 1,
            numOfRows = 10,
            srchKeyCode = "001",
            trgter = "",
            interest = "",
            age = 0,
            onlineApply = "",
            orderBy = "popular"
        ).enqueue(object : Callback<WelfareResponse> {
            override fun onResponse(call: Call<WelfareResponse>, response: Response<WelfareResponse>) {
                if (response.isSuccessful) {
                    val list = response.body()?.serviceList ?: emptyList()
                    if (list.isEmpty()) {
                        Log.d("XML", "실제 API 데이터 없음 → 더미 사용")
                        showDummyData()
                    } else {
                        adapter.submitList(list)
                        Log.d("XML", "API로부터 ${list.size}개 수신됨")
                    }
                } else {
                    Log.e("XML", "API 실패 → 더미 사용")
                    showDummyData()
                }
            }

            override fun onFailure(call: Call<WelfareResponse>, t: Throwable) {
                Log.e("XML", "네트워크 오류 → 더미 사용: ${t.message}")
                showDummyData()
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
