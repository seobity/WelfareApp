import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemWelfareBinding
import com.example.finalproject.welfare.WelfareItem

class WelfareAdapter : RecyclerView.Adapter<WelfareAdapter.WelfareViewHolder>() {

    private var welfareList: List<WelfareItem> = emptyList()

    inner class WelfareViewHolder(private val binding: ItemWelfareBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WelfareItem) {
            Log.d("ADAPTER", "item: ${item.servNm}")
            binding.txtServNm.text = item.servNm ?: "정보 없음"
            binding.txtServDgst.text = item.servDgst ?: "설명 없음"
            binding.txtJurMnofNm.text = item.jurMnofNm ?: "-"
            binding.txtLifeArray.text = item.lifeArray ?: "-"
            binding.txtSrvPvsnNm.text = item.srvPvsnNm ?: "-"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelfareViewHolder {
        val binding = ItemWelfareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WelfareViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WelfareViewHolder, position: Int) {
        holder.bind(welfareList[position])
    }

    override fun getItemCount(): Int = welfareList.size

    fun submitList(list: List<WelfareItem>) {
        Log.d("ADAPTER", "SubmitList 실행 전 첫 번째 항목: ${list.firstOrNull()?.servNm}")
        welfareList = list
        notifyDataSetChanged()
        Log.d("ADAPTER", "SubmitList 실행됨: ${list.size}개")
    }
}
