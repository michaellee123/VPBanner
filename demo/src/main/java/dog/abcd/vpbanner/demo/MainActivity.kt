package dog.abcd.vpbanner.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dog.abcd.vpbanner.*
import dog.abcd.vpbanner.demo.databinding.ActivityMainBinding
import dog.abcd.vpbanner.demo.databinding.ItemBanner2Binding
import dog.abcd.vpbanner.demo.databinding.ItemBannerBinding

class MainActivity : AppCompatActivity() {

    val timer = InfiniteTimer(3000) {
        pagerSet.forEachIndexed { index, viewPager2 ->
            if (index == 0) {
                viewPager2.previous()
            } else {
                viewPager2.next()
            }
        }
    }

    val pagerSet = LinkedHashSet<ViewPager2>()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val images = arrayListOf(
            "https://img2.baidu.com/it/u=129968130,1812456545&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=497",
            "https://img1.baidu.com/it/u=2726621973,105225233&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
            "https://img1.baidu.com/it/u=1820926892,157673252&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=574"
        )
        binding.viewPager.setInfiniteAdapter(images.reversed(), ItemBannerBinding::class.java) {
            Glide.with(itemView).load(it).into(bind.imageView)
            bind.textView.text = layoutPosition.toString()
        }
        binding.viewPager2.setInfiniteAdapter(images, ItemBannerBinding::class.java) {
            Glide.with(itemView).load(it).into(bind.imageView)
            bind.textView.text = layoutPosition.toString()
        }
        binding.viewPager3.setInfiniteAdapter(images, ItemBannerBinding::class.java) {
            Glide.with(itemView).load(it).into(bind.imageView)
            bind.textView.text = layoutPosition.toString()
        }
        binding.viewPager.setCurrentItem(binding.viewPager.itemCount - 2, false)
        binding.viewPager.ignoreAllEvent = false
        pagerSet.add(binding.viewPager)
        pagerSet.add(binding.viewPager2)
        pagerSet.add(binding.viewPager3)
        timer.start()

        binding.viewPager4.asBanner(
            arrayListOf(
                BannerItem(
                    "https://img2.baidu.com/it/u=3883116267,4060519068&fm=253&fmt=auto&app=138&f=GIF?w=500&h=273",
                    "Eminem 1"
                ),
                BannerItem(
                    "https://img2.baidu.com/it/u=2330297901,3395040902&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500",
                    "Eminem 2"
                ),
                BannerItem(
                    "https://img2.baidu.com/it/u=3057947913,1359852592&fm=253&fmt=auto&app=138&f=JPEG?w=865&h=500",
                    "Eminem 3"
                ),
                BannerItem(
                    "https://img0.baidu.com/it/u=3398376240,764405049&fm=253&fmt=auto&app=120&f=JPEG?w=799&h=500",
                    "Eminem 4"
                )
            ),
            ItemBanner2Binding::class.java,
            4000
        ) {
            Glide.with(itemView).load(it.url).into(bind.imageView)
            bind.textView.text = it.title
        }

        binding.viewPager4.setIndicator(binding.indicator)

    }

    data class BannerItem(val url: String, val title: String)

}