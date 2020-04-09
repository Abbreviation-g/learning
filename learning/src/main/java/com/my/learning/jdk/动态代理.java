package com.my.learning.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class 动态代理 {
	/**
	 * 通过接口定义行为 只有需要被监控的行为才有资格 在这里声明
	 */
	public static interface BaseService {
		void eat();

		void wc();
	}

	public static class Person implements BaseService {

		@Override
		public void eat() {// 主要业务，代理模式要求开发人员只关心主要业务
			System.out.println("使用筷子吃饭....");
		}

		@Override
		public void wc() {
			System.out.println("测试地球重力是否存在");
		}
	}

	public static class Invocation implements InvocationHandler {

		private BaseService obj;// 具体被监控对象

		public Invocation(BaseService param) {
			this.obj = param;
		}

		/*
		 * 
		 * invoke方法：在被监控行为将要执行时，会被JVM拦截 被监控行为和行为实现方会被作为参数输送invoke ****
		 * 通知JVM,这个被拦截方法是如何与当前次要业务方法绑定实现 invoke方法三个参数
		 * 
		 * int v= 小明.eat();//JVM拦截 eat方法封装为Mehtod类型对象 eat方法运行时接受所有的实参封装到Object[]
		 * 将负责监控小明的代理对象作为invoke方法第一个参数
		 * 
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
			// 0.局部变量，接受主要业务方法执行完毕后返回值
			Object value;
			// 1.确认当前被拦截行为
			String methodName = method.getName();
			// 2.根据被拦截行为不同，决定主要业务和次要业务如何绑定执行
			if ("eat".equals(methodName)) {// 饭前要洗手
				wash(); // 洗手
				value = method.invoke(this.obj, params); // 吃饭
			} else {// 便后要洗手
				value = method.invoke(this.obj, params);
				wash();
			}
			return value; // 返回被拦截方法，需要调用地方
		}

		// 次要业务
		public void wash() {
			System.out.println("-----洗手----");
		}
	}

	public static class ProxyFactory {
		/*
		 *
		 * JDK动态代理模式下，代理对象的数据类型 应该由监控行为来描述 参数： Class文件，监控类
		 */
		public static BaseService Builder(Class<? extends BaseService> classFile) throws Exception {

			// 1.创建被监控实例对象
			BaseService obj = (BaseService) classFile.newInstance();
			// 2.创建一个通知对象 用接口来描述
			InvocationHandler adviser = new Invocation(obj);
			// 3.向JVM申请负责监控obj对象指定行为的监控对象（代理对象）
			/*
			 * loader:被监控对象隶属的类文件在内存中真实地址 interfaces:被监控对象隶属的类文件实现接口
			 * h：监控对象发现小明要执行被监控行为，应该有哪一个通知对象进行辅助
			 */
			BaseService $proxy = (BaseService) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
					obj.getClass().getInterfaces(), adviser);
			return $proxy;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new SimpleDateFormat("EEE MMM d HH:mm:ss 'CST' yyyy",Locale.US).format(new Date()));
		System.out.println(new SimpleDateFormat("LLL",Locale.US).format(new Date()));
		BaseService baseService = ProxyFactory.Builder(Person.class);
		baseService.eat();
		baseService.wc();
	}
}
