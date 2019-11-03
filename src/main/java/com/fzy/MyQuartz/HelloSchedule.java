package com.fzy.MyQuartz;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HelloSchedule {

	public static void main(String[] args) throws SchedulerException {
		// 创建一个JobDetail实例，将该实例与HelloJob Class绑定
		// JobDetail用来绑定Job的，且在Job执行的时候携带一些执行的信息在里面。
		// JobDetail是通过Builder模式来创建的，，有一个JobBuilder，有个newJob的方法，newJob方法里需要传入这个HelloJob的类定义。
		// 之后需要给这个JobDetail在Schedule中找到而创建一个唯一标识,withIdentity以哪个名字来入伙，并属于哪个组，默认是default
		// 之后调用build方法将JobDetail创建出来
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myjob", "group1").build();

		// 创建一个Trigger实例，定义该Job立即执行，且每隔两秒钟重复执行一次，直到永远
		// 用来触发这个Job去执行的，触发会定义它什么时候去执行，第一次执行，且会定义它会不会一直重复的执行下去或执行几次会终止。
		// 通过buider模式创建，triggerBuilder则newTrigger，定义一个标识符且定义了它的组，定义了现在开始执行；并定义了它的执行频度，再调用builder的build的方法将Trigger创建出来
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("mytrigger", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(2000).repeatForever())
				.build();
		// 创建Schedule的实例
		// 区别于Trigger和JobDetail，是通过Factory模式来创建的
		/*
		 * 注意：StdScheduleFactory需要手动输出并引入相关资源，代码提示中没有
		 * 所有的Scheduler实例应该由SchedulerFactory来创建，SchedulerFactory有两种：
		 * SchedulerFactory sFactory=new StdSchedulerFactory();Scheduler scheduler=sFactory.getScheduler();
		 * DirectSchedulerFactory factory=DirectSchedulerFactory.getInstance();Scheduler scheduler=factory.getScheduler();
		 * 由于DirectSchedulerFactory的配置是由代码编写配置，而StdSchedulerFactory的配置是由配置文件配置，所有大多使用StdSchedulerFactory创建Scheduler。

	StdSchedulerFactory详解：
	
		使用一组参数（Java.util.Properties）来创建和初始化Quartz调度器
		配置参数一般存储在quartz.properties中
		调用getScheduler方法就能创建和初始化调度器对象
	Scheduler的主要函数
		Date schedulerJob(JobDetail jobDetail,Trigger trigger) 该方法的返回值是：指定的Scheduler近期就要触发的时间
		void strat() 启动Scheduler
		void standby() 将该Scheduler暂停
		void shutdown() 将该Scheduler关闭，并且不能被重启。该方法可以有两个参数，如果为 shutdown(true) ,表示等待所有正在执行的Job执行完毕之后，再关闭scheduler；如果为 shutdown(false) ，默认不填也是这样，表示直接关闭scheduler
		 */
		SchedulerFactory sFactory = new StdSchedulerFactory();
		/*
		 * SchedulerFactory sFactory = new SchedulerFactory() {
		 * 
		 * public Scheduler getScheduler(String arg0) throws SchedulerException { //
		 * TODO Auto-generated method stub return null; } //无参创建 public Scheduler
		 * getScheduler() throws SchedulerException { // TODO Auto-generated method stub
		 * return null ; }
		 * 
		 * public Collection<Scheduler> getAllSchedulers() throws SchedulerException {
		 * // TODO Auto-generated method stub return null; } };
		 */
		// 获取schedule的实例，调用ScheduleFactory来获取Schedule的实例
		Scheduler scheduler = sFactory.getScheduler();

		// 空执行是不可以的，需要将JobDetail和Trigger传入，并将其绑定在一起传入到schedule里面去
		scheduler.scheduleJob(jobDetail, trigger);

		// 获取到实例之后开始执行
		scheduler.start();
		// 为方便调试、或让执行的步骤可视化，加入当前的时间信息。
		Date startDate = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Current Time Is：" + sf.format(startDate));

	}
}
