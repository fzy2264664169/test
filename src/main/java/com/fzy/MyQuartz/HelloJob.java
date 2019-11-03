package com.fzy.MyQuartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//为便于观察打印时间日期
		Date startDate = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Current Exec Time Is：" + sf.format(startDate));
		// 编写具体的业务逻辑
		System.out.println("Hello World");
	}

}
