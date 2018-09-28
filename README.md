# RecyclerViewPager
像RecyclerView一样支持ViewType的ViewPager  
这个东西是使用kotlin编写的，所以你的项目必须引入kotlin，不然是没法正常使用的。推荐你也使用kotlin，因为java调用这个框架的话，方法名参数名等可能会有些奇怪  
### 特点
- 像RecyclerView一样支持控件重用和ViewType
- 数据适配器的方法和使用方式跟RecyclerView一模一样
- 带自动滚动帮助类，可使RecyclerViewPager自动滚动
- 框架轻量，接入简单，安全可靠
### 接入方式
- 下载源码，将名为recyclerviewpager的module加入到你的工程，或者将module打包成aar格式
- maven方式接入(推荐使用上面的方法，我服务器配置垃圾)
    编辑Project的build.gradle文件
    ```
    allprojects {
        repositories {
            google()
            jcenter()
            mavenCentral()
            maven { url "http://101.132.235.215/repor" } //加入这一行
        }
    }
    ```
    然后编辑你app module的build.gradle文件，在dependencies节点下加入
    ```
    implementation "com.jiandanlangman:recyclerviewpager:1.0.1@aar"
    ```
### API说明
- 主类  
    主类的路径如下：
    ```
    com.jiandanlangman.recyclerviewpager.widget.RecyclerViewPager
    ```
    主要方法：
    ```
    //设置数据适配器
    fun setAdapter(adapter: com.jiandanlangman.recyclerviewpager.widget.RecyclerViewPager.Adapter<*>?)

    //获取当前显示的ItemView
    fun getCurrentItemView(): View?

    //这个是ViewPager自带的setAdapter方法，已经废弃，使用上面的setAdapter方法替代
    fun setAdapter(adapter: android.support.v4.view.PagerAdapter?)
    ```
    其它方法和ViewPager无异
- ViewHolder  
    这个ViewHolder的存在意义参照RecyclerView的ViewHolder  
    默认的ViewHolder声明路径如下：
    ```
    com.jiandanlangman.recyclerviewpager.widget.RecyclerViewPager.ViewHolder
    ```
    声明内容如下：
    ```
    open class ViewHolder(val itemView: View)
    ```
- Adapter  
    Adapter的公开方法和RecyclerView.Adapter一样，使用方式也和RecyclerView.Adapter一样  
    Adapter的声明路径如下：
    ```
    com.jiandanlangman.recyclerviewpager.widget.RecyclerViewPager.Adapter
    ```
    声明内容如下(只列出了部分重要方法，具体实现参考源码)：
    ```
    abstract class Adapter<VH : ViewHolder> : PagerAdapter() {

        abstract fun getItemCount(): Int

        abstract fun onCreateViewHolder(container: ViewGroup, viewType: Int): VH

        abstract fun onBindViewHolder(viewHolder: VH, position: Int)

        final override fun getCount(): Int

        open fun getItemViewType(position: Int): Int
    }
    ```
- 自动滚动帮助类  
    自动滚动帮助类的声明路径如下：
    ```
    com.jiandanlangman.recyclerviewpager.util.ViewPagerAutoScrollHelper
    ```
    使用方法如下：
    ```
    //hold方法的第一个参数为需要自动滚动的viewPager，第二个参数true表示当触摸ViewPager时暂停自动滚动计时，松开时继续计时
    val helper = ViewPagerAutoScrollHelper.hold(viewPager, true)

    //开始滚动，参数为自动滚动的时间间隔，单位毫秒
    helper.startAutoScroll(1000)

    //停止滚动，调用此方法后如需再次自动滚动，需要重新调用startAutoScroll方法
    helper.stopAutoScroll()

    //暂停or继续滚动，一般是用来配合Activity/Fragment声明周期使用
    helper.onPause()
    helper.onResume()
    ```
