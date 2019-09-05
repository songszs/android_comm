package com.zs.test.kotlin

import com.zs.R
import com.zs.base.view.BaseFragment

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-11 下午3:13
 * @description: mytest
 */
class KotlinTestFragment : BaseFragment() {

    lateinit var testList: List<String>

    override fun createViewId(): Int {
        return R.layout.main
    }

    fun test() {
        //单例
        SingletonTest.getTestValue()
        SingletonTest.setValue("hello world")

        StaticMehtod.calc(1, 1)
    }

    fun commonTest() {
//        testList = ArrayList<String>()
//        testList.add()
//        for ()

//        var names:Array<String> = Arr

        //数组使用
        val nums = IntArray(5)
        nums[0] = 1
        nums[1] = 2
        nums[2] = 3
        nums[3] = 4
        nums[4] = 5

        val name = "your name is ${nums[0]}"
        println(name)

        // a ? b : c
        val real = true
        if (real) println("real") else println("not")

        //if else if
        val code = 1
        when (code) {
            1 -> println("1")
            2 -> println("2")
            3 -> println("3")
            else -> println("hello world")
        }

        //循环两种形式
        for (num in nums) {
            println("$num")
        }
        for (index in nums.indices) {
            println(nums[index])
        }
        for ((index, value) in nums.withIndex()) {
            println("index is $index")
            println("value is $value")
        }

        val phone = Phone("hello world")

        println(phone.num)
        println(phone.getNameWithNum())
    }


    open class Phone(name: String) {

        //变量可以自定义set和get
        private var name: String = name
            get() {
                return "phone$name"
            }
            set(value) {
                field = "setTag$value"
            }

        var num: Int = 0

        protected lateinit var factoryName: String

        //如果有一个自定义的主构造函数 则需要继承主构造函数
        constructor(name: String, num: Int) : this(name) {
            this.num = num
        }

        fun getNameWithNum(): String {
            return name + 123
        }

        open fun callNum() {
            println("call ${num}")
        }

    }

    class Sumsung {
        private var pnum = 0
        private lateinit var name: String

        //继承默认构造函数
        constructor(num: Int) {
            pnum = num
        }

        constructor(name: String) {
            this.name = name
        }

        init {
            //这里只能使用主构造函数传入的参数，次构造函数的参数无法使用
        }
    }

    class Apple : Phone {

        var name: String? = null
            set(value) {
                field = "hello$value"
            }
            get() {
                return if (name == null) "" else name
            }

        //需要先调用父构造函数
        constructor() : super("1") {

        }

        //覆盖需要 override关键字 并且父方法需要 open
        override fun callNum() {
            super.callNum()
            factoryName = callNum().toString()
        }

        companion object {
            const val TAG = "APPLE"
        }
    }
}