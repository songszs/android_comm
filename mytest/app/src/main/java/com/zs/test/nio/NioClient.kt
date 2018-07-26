package com.zs.test.nio

import android.util.Log
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-23 上午11:34
 * @description: 打开连接后 发送给服务端一个请求
 */
class NioClient {
    private var selector: Selector? = null

    fun initClient(ip: String, port: Int) {
        val socketChannel = SocketChannel.open()
        socketChannel.configureBlocking(false)
        selector = Selector.open()

        socketChannel.connect(InetSocketAddress(ip, port))
        socketChannel.register(selector, SelectionKey.OP_CONNECT)
    }

    fun listen() {
        while (true) {
            selector?.select()

            val iterator = selector!!.selectedKeys().iterator()
            while (iterator.hasNext()) {
                val key = iterator.next()
                iterator.remove()
                when {
                    key.isConnectable -> {
                        val channel = key.channel() as SocketChannel
                        if (channel.isConnectionPending) {
                            channel.finishConnect()
                        }
                        channel.configureBlocking(false)
                        //连接之后立即写一个数据
                        //如果缓冲区未满 可以不用注册写事件 若注册写事件 在缓冲区未满的情况下 会一直触发写事件导致线程循环处理写事件
                        //合理的做法 先注册写事件 然后写完数据后 反注册
                        channel.write(ByteBuffer.wrap("hello server,i am client".toByteArray()))

                        channel.register(selector, SelectionKey.OP_READ)
                    }
                    key.isReadable -> read(key)
                }
            }
        }
    }

    private fun read(key: SelectionKey) {
        val channel: SocketChannel = key.channel() as SocketChannel
        val buffer = ByteBuffer.allocate(32)
        channel.read(buffer)
        val data = buffer.array()
        val content = String(data).trim()
        Log.e("NioClient read content:", content)
    }


}