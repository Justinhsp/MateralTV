package com.king.tv.ioc.module;

import android.content.Context;

import com.king.tv.http.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Inject:
 *      在需要依赖的地方使用这个注解。用它告诉Dagger这个类或者字段需要依赖注入，注意这个类或者字段不能用private修饰，
 *      因为 @inject 采用 new 的方式注入对象的，若创建该对象需要参数，它会到所有 @provides 中去寻找匹配的数据来完成对象的创建。
 * @Module:
 *       Modules类里面的方法专门生产和整合依赖(新对象)，并通过@Provides告诉Dagger，所以我们定义一个类，用@Module注解,
 *       这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的依赖。modules的一个重要特征是它们设计为分区并组合在一起
 *       (比如说，在我们的app中可以有多个组成在一起的modules).总之：@Module 是很多对象的集中营，目的是为通过@Provides 提供各种对象；
 * @Provides:
 *            在modules中,我们定义的方法是用这个注解,以此来告诉Dagger我们想要构造对象并提供这些依赖
 *           方法中的返回参数和入参必须是继承关系才可以，否则它查不到子类，就会进入无限循环错误;
 * @Component:
 *    Components从根本上来说就是一个注入器,也可以说是@Inject和@Module的桥梁,它的主要作用就是连接这两个部分。
 *    Components可以提供所有Modules中定义了的类型的(非void)”实例”，比如：我们必须用@Component注解一个接口,
 *    并绑定需要的 @Modules 组成该组件,如果缺失了任何一块都会在编译的时候报错。所有的组件都可以通过它的modules知道依赖的范围。
 *    总之：@Component 用于获取各种对象，若接口/抽象类中方法有返回值，则还可以进行对象功能操作等；并且子、父Component的作用域不能相同。
 * @Scope: 用于自定义注解限定注解作用域。这是一个非常强大的特点，没必要让每个对象都去了解如何管理他们的实例。
 */
@Module
public class AppModule {
    /**
     * 默认超时事件/s
     */
    private static  final int DEFAULT_TIME_OUT=10;

    private Context context;

    private String baseUrl;

    public AppModule(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public  Context provideContext(){
        return context;
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(provideOkHttpCilent())
                .build();
    }


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpCilent() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }


    @Provides
    @Singleton
    public ApiService provideApiService(){
        return provideRetrofit().create(ApiService.class);
    }


}
