package com.appcarestudio.autolaucher.feature.items.ui.list

/*

class NewsAdapter(context: Context) :
    SimpleBaseRecyclerViewAdapter<Movie, NewsAdapter.ViewHolder>(context) {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val binding = DataBindingUtil.bind<ItemMoviesBinding>(itemView)
           fun bind(item: Movie) {
               binding?.image?.let { Glide.with(it).load(item.image).into(it) }
               binding?.title?.text = item.title
               binding?.descr?.text = item.description
 */
/*              item.subTitleColor?.let { binding?.subTitle?.setBackgroundColor(it) }
               item.subTitleFGColor?.let {binding?.subTitle?.setTextColor(it)   }
               item.titleColor?.let {binding?.title?.setTextColor(it)   }*//*

           }


    }



    override fun getViewHolder(
        parent: ViewGroup?,
        layoutRes: Int,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(getInflatedView(layoutRes, parent, false))
    }


    override val layoutRes: Int = R.layout.item_movies

    override fun onBind(holder: ViewHolder, position: Int) {
        val item = getItem(position)
          holder?.bind(item!!)
          holder?.itemView?.setOnClickListener {
              onAdapterItemClick?.onItemClick(item!!)
          }
    }
}*/
