package com.zs.test.nio

import android.util.Log
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author:      ZangSong
 * @email:       gnoszsong@gmail.com
 * @date:        18-7-23 上午10:30
 * @description:
 *
 * 三个关键
 * channel
 * selector
 * buffer
 */
class NioServer {
    private var selector: Selector? = null

    private var executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun initServer(port: Int) {
        val serverChannel = ServerSocketChannel.open()
        serverChannel.configureBlocking(false)
        serverChannel.socket().bind(InetSocketAddress(port))
        //打开selector
        selector = Selector.open()

        //注册客户端连接的接受事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)
    }

    /**
     * 一直论询所有注册到selector中的channel的事件
     * 因为是论询处理，这里各个事件触发的处理尽量只是读取其中的数据，具体的业务逻辑可以放到线程池中进行，避免造成后续事件的等待
     */
    fun listen() {
        while (true) {
            //阻塞论询
            selector?.select()
            //有事件发生
            val iterator = selector!!.selectedKeys().iterator()
            //逐个取出处理
            while (iterator.hasNext()) {
                val key: SelectionKey = iterator.next()
                //处理完后移除 避免在处理
                iterator.remove()
                when {
                //表示接受连接的事件已经准备好 可以处理了
                    key.isAcceptable -> {
                        //获取发生该事件的管道 此处即服务端监听的管道
                        val channel = key.channel() as ServerSocketChannel
                        //接受连接 返回一个新的channel 即建立的新连接
                        val singleChannel = channel.accept()
                        singleChannel?.configureBlocking(false)
                        //为这个新的连接注册读事件的
                        singleChannel?.register(selector, SelectionKey.OP_READ)
                    }
                //表示读数据的事件已经准备好 可以读数据了
                    key.isReadable -> {

                        handClientRequest(key)
                        //读完之后写一个测试数据
                        writeData("hello client", key)
                    }
                //表示写数据的事件已经准备好 可以写数据了 写数据时
                    key.isWritable -> write(key)
                }
            }
        }
    }

    /**
     * 处理客户端发来的请求
     */
    private fun handClientRequest(selectionKey: SelectionKey) {
        //在线程池中处理请求
//        executor.execute(Runnable {
            read(selectionKey)
//        })
    }


    /**
     * 读数据
     */
    private fun read(selectionKey: SelectionKey) {
        val channel: SocketChannel = selectionKey.channel() as SocketChannel
        //要想往channel中读写数据，必须通过buffer，buffer本质上就是字节数组
        val buffer = ByteBuffer.allocate(32)
        channel.read(buffer)
        val data = buffer.array()
        val content = String(data).trim()
        Log.e("NioServer read", content)

    }

    /**
     * 写数据被触发时的处理
     * 从附加对象中拿到数据并写入
     */
    private fun write(selectionKey: SelectionKey) {
        val channel: SocketChannel = selectionKey.channel() as SocketChannel
        //从key中拿到 附加对象
        val buffer = selectionKey.attachment() as? ByteBuffer
        channel.write(buffer)
        Log.e("NioServer write", "write")

        //反注册写事件  否则该事件在select中会一直被触发  详见 https://blog.csdn.net/iter_zc/article/details/39291129
        val operate = selectionKey.interestOps()
        selectionKey.interestOps(operate xor SelectionKey.OP_WRITE)
    }


    private fun writeData(content: String, selectionKey: SelectionKey) {
        val outBuffer = ByteBuffer.wrap(content.toByteArray())
        //attach到key中 读事件发生时 在读出来
        selectionKey.attach(outBuffer)

        //注册写事件
        val operate = selectionKey.interestOps()
        selectionKey.interestOps(operate or SelectionKey.OP_WRITE)
    }


}