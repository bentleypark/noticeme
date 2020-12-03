/*
 * COPYRIGHT © SOVORO 2020 ALL RIGHTS RESERVED
 */

package com.project.noticeme.ui.guide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.project.noticeme.R
import kotlinx.android.synthetic.main.onboarding_slide_item_container.view.*

class OnBoardingAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindImage(image: Int) {
            with(itemView) {
//                iv_center_img.setImageResource(image)
                iv_center_img.load(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return OnBoardingViewHolder(
            layoutInflater.inflate(
                R.layout.onboarding_slide_item_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        val item = imageList[position]
        holder.bindImage(item)
    }
}
