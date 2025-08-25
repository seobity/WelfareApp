package com.example.finalproject.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalproject.databinding.FragmentGraphBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        drawBarChart()
        return binding.root
    }

    private fun drawBarChart() {
        val entries = listOf(
            BarEntry(0f, 5f),  // 예: 영상 검색 5건
            BarEntry(1f, 8f),  // 복지정보 8건
            BarEntry(2f, 3f)   // 메모 3건
        )

        val dataSet = BarDataSet(entries, "기능별 사용량")
        dataSet.setColors(Color.BLUE, Color.GREEN, Color.MAGENTA)
        val barData = BarData(dataSet)

        val labels = listOf("영상", "복지", "메모")
        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.labelCount = labels.size
        xAxis.setDrawGridLines(false)

        binding.barChart.data = barData
        binding.barChart.invalidate()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
