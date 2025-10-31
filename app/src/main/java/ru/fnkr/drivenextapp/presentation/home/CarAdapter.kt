package ru.fnkr.drivenextapp.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.databinding.CardItemBinding
import ru.fnkr.drivenextapp.domain.model.Car

import coil.load

class CarAdapter: RecyclerView.Adapter<CarAdapter.CarHolder>() {
    var carList = ArrayList<Car>()
    class CarHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = CardItemBinding.bind(item)
        fun bind(car: Car) {
            binding.ivPreview.load(car.imgUrl) {
                placeholder(R.drawable.mers)
                error(R.drawable.mers)
                crossfade(true)
            }
            binding.tvCardBrand.text = car.brand
            binding.tvCardModel.text = car.model
            binding.tvPrice.text = car.price.toString()
            binding.tvGearbox.text = car.gearbox
            binding.tvFuel.text = car.fuel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CarHolder(view)
    }

    override fun onBindViewHolder(
        holder: CarHolder,
        position: Int
    ) {
        holder.bind(carList[position])
    }

    override fun getItemCount(): Int {
        return carList.size
    }

}