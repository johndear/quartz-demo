//package com.ls.quartz.web;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.impl.StdSchedulerFactory;
//
//public class QuartzStartupListener implements ServletContextListener{
//
//	public void contextInitialized(ServletContextEvent arg0) {
//		System.out.println("启动quartz监听...");
//		try {
//			// Initiate a Schedule Factory
//			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//			Scheduler scheduler = schedulerFactory.getScheduler();
//			// start the scheduler
//			scheduler.start();
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void contextDestroyed(ServletContextEvent arg0) {
//		System.out.println("关闭quartz监听...");
//		try {
//			// Initiate a Schedule Factory
//			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//			Scheduler scheduler = schedulerFactory.getScheduler();
//			// close the scheduler
//			scheduler.shutdown(true);
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	
//
//	
//}
