# VPBanner

This is a library for creating banners with ViewPager2.

## Working Principle

When we set a list to the `BaseInfiniteLoopAdapter`, the adapter will create a list that first item
is
the last item of the original list, the last item is the first item of the original list, and the
middle items are the original list. Let me show you a examples.

Now, we have a list with 3 items like this:

| 1 | 2 | 3 |
|-|-|-|

And we set this list to the adapter, the adapter will create a list like this:

| 3 | 1 | 2 | 3 | 1 |
|-|-|-|-|-|

So, when we scroll to the first item, we can see the last item of the original list. When we scroll
to the last item, we can see the first item of the original list. Of course, we need to make the
ViewPager2 start with the original first item, in another word, first item index is 1 instead of 0.

Then use the `InfinitePageChangeCallback`, it will change the current item to 1 when we scroll to
the last item, and change the current item to the last original item when we scroll to the first
item.

Finally, we create a timer to make the ViewPager2 scroll to the next item automatically.

## Usage

import the library:

```groovy

```

Add this to your module's `build.gradle`:

```groovy
android {
    //...
    viewBinding {
        enabled = true
    }
}
```

Then create a layout file `item_banner` like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent">

    <ImageView android:id="@+id/imageView" android:layout_width="match_parent"
        android:layout_height="match_parent" android:scaleType="centerCrop" />

    <TextView android:id="@+id/textView" android:layout_width="match_parent"
        android:layout_height="match_parent" android:gravity="center" android:textSize="40sp"
        android:textStyle="bold" />

</RelativeLayout>
```

Android Studio will generate a binding class `ItemBannerBinding` for you.

Now, you can create a banner like this:

```kotlin
binding.viewPager.asBanner(
    arrayListOf(
        BannerItem(
            "https://img2.baidu.com/it/u=3883116267,4060519068&fm=253&fmt=auto&app=138&f=GIF?w=500&h=273",
            "Eminem 1"
        ),// you can use any object, a String or a custom class.
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
    ),// a generics data list
    ItemBannerBinding::class.java,// binding class
    4000// delay time
) {
    // bind data, in this lambda, 'this' is the ViewHolder with binding class, 'it' is a item of the data list.
    Glide.with(itemView).load(it.url).into(bind.imageView)
    bind.textView.text = it.title
}
```

## Multi Banners

Sometimes we need to create multiple banners in the same activity, and the banners need to be
synchronized
with automatic scrolling, we can use another function `setInfiniteAdapter`, this function will not
scroll automatic. We can use a timer to control the banners. Let me show you some code:

```kotlin
val pagerSet = LinkedHashSet<ViewPager2>()

val timer = InfiniteTimer(3000) {
    pagerSet.forEach {
        it.next()
    }
}

val images = arrayListOf(
    "https://img2.baidu.com/it/u=129968130,1812456545&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=497",
    "https://img1.baidu.com/it/u=2726621973,105225233&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
    "https://img1.baidu.com/it/u=1820926892,157673252&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=574"
)

binding.viewPager.setInfiniteAdapter(images, ItemBannerBinding::class.java) {
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
pagerSet.add(binding.viewPager)
pagerSet.add(binding.viewPager2)
pagerSet.add(binding.viewPager3)
timer.start()
```

## Indicator

You can create a indicator from `BaseIndicator`, and draw the indicator in the `onDraw` function.
Then you can use the `setIndicator` function to set the indicator to the banner.

I written a `CircleIndicator` for you, you can use it, or you can write your own indicator.

## License

No license, you can use it in any way you want.