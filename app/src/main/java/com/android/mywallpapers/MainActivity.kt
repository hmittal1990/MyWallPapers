package com.android.mywallpapers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.android.adapter.WallPapersAdapter
import com.android.modal.PhotosItem
import com.android.network.NetworkController
import com.android.utils.Constants
import com.android.utils.PaginationScrollListener
import com.android.utils.Utility
import kotlinx.android.synthetic.main.activity_main.*
import com.android.utils.SpacesItemDecoration

class MainActivity : AppCompatActivity() {

    lateinit var networkController: NetworkController
    var pageNumber = 1
    var adapter: WallPapersAdapter? = null
    lateinit var wallpapers: ArrayList<PhotosItem>
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wallpapers = ArrayList()
        networkController = NetworkController(Constants.API_TOKEN)

        if (Utility.isInternetConnectionAvailable(this)) {
            getWallPapers()
        } else {
            Toast.makeText(applicationContext, getString(R.string.check_internet), Toast.LENGTH_LONG).show()
        }

        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        adapter = WallPapersAdapter(this@MainActivity, wallpapers) {
            val intent = Intent(this, FullScreenImageActivity::class.java)
            intent.putExtra(Constants.IMAGE, it.src?.portrait)
            startActivity(intent)
        }
        list_recycler_view.layoutManager = layoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        list_recycler_view.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        list_recycler_view.adapter = adapter

        list_recycler_view.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                pageNumber++
                getWallPapers()
            }
        })
    }

    private fun getWallPapers() {
        networkController.getWallpapers(
            Constants.PER_PAGE, pageNumber = pageNumber
        ) { response, error ->
            if (response?.photos != null) {
                isLoading = false
                checkIfLastItem(response.nextPage)
                adapter?.setData(response.photos)
            } else {
                Toast.makeText(applicationContext, error.plus(""), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkIfLastItem(nextPage:String?){
        nextPage?.let {
            isLastPage = false
        } ?: kotlin.run {
            isLastPage = true
        }
    }
}