package com.king.tv.help;


import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类
 */
public class ThreadPoolManager {

    private static  ThreadPoolProxy mInstance;

    /**
     * 返回唯一实例
     * 如果唯一实例为空
     * 代理类创建并且上锁
     */
    public static  ThreadPoolProxy getInstance(){
        if (mInstance==null){
            synchronized (ThreadPoolManager.class){
                if (mInstance==null){
                    mInstance=new ThreadPoolProxy(2,5);
                }
            }
        }
        return mInstance;
    }


    /**
     * 线程池代理
     */
    public static class ThreadPoolProxy{
        private ThreadPoolExecutor mThreadPoolExecutor;

        public  ThreadPoolProxy(int corePoolSize,int maximumPoolSize){
            initThreadPoolExecuter(corePoolSize,maximumPoolSize);
        }

        /**
         * 初始化工作
         * @param corePoolSize
         * @param maximumPoolSize
         */
        private void initThreadPoolExecuter(int corePoolSize, int maximumPoolSize) {
            if (mThreadPoolExecutor==null){
                //阻塞缓存队列
                BlockingDeque<Runnable> workQueue=new LinkedBlockingDeque<>();
                //线程工厂
                ThreadFactory threadFactory= Executors.defaultThreadFactory();
                //拒绝任务处理策略(抛弃旧的任务)
                RejectedExecutionHandler handler=new ThreadPoolExecutor.DiscardPolicy();
                mThreadPoolExecutor=new ThreadPoolExecutor
                        (corePoolSize,maximumPoolSize,0, TimeUnit.MICROSECONDS,workQueue,threadFactory,handler);
            }
        }

        /**
         * 提交任务
         * @param task
         */
        public void submit(Runnable task){
            if (mThreadPoolExecutor!=null)
                mThreadPoolExecutor.submit(task);
        }


        /**
         * 执行任务
         * @param task
         */
        public void execute(Runnable task){
            if (mThreadPoolExecutor!=null)
                mThreadPoolExecutor.execute(task);
        }


        /**
         * 移除任务
         * @param task
         */
        public void remove(Runnable task){
            if (mThreadPoolExecutor!=null)
                mThreadPoolExecutor.remove(task);
        }

    }
}
