package com.josework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理用户提交的请求，springmvc中是使用方法来处理的
 * 方法是自定义的，可以有多种返回值，多种参数，方法名称自定义
 *准备使用doSome方法处理some.do请求。
 * @RequestMapping：请求映射，作用是把一个请求地址和一个方法绑定在一起
 *                  一个请求指定一个方法处理。
 *
 *          属性:1.value是一个String，表示一个请求的uri地址
 *
 *          位置：1.在方法的上面，常用的。
 *              2.在类的上面，所有请求地址的公共部分，叫做模块名称
 *           说明：使用RequestMapping修饰的方法叫做处理器方法或者控制器方法。
 *           使用@RequestMapping修饰的方法类似于Servlet中的doGet()和doPost()
 *
 *
 *  返回值 ModelAndView 表示本次请求的处理结果
 *
 *
 *
 *  springmvc处请求处理的过程
 *  1）发起some.do请求
 *  2) tomcat(web.xml--> url-pattern -->知道 *.do的请求给DispatcherServlet)
 *  3) DispatcherServlet （根据spring-mvc.xml配置知道some.do-->doSome()）
 *  4) DispatcherServlet 把some.do转发给MyController.doSome()方法。
 *  5） 框架执行 doSome() 把得到ModelAndView进行处理，转发到show.jsp
 *
 *
 * springmvc执行过程源码分析
 * 1.tomcat启动，创建容器的过程
 *      通过load-on-start标签指定的1，创建DispatcherServlet对象
 *      DispatcherServlet的父类是继承自HttpServlet，它是一个servler，在被创建时，会执行init()方法。
 *
 *      在init方法中：
 *      //创建容器，读取配置文件
 *      WebApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mvc.xml")
 *      //把容器对象放入ServletContext中
 *      getServletContext.setAttribute(key,ctx)
 *
 *     上面创建容器的作用：创建@controller注解所在的类的对象，创建MyController对象，
 *     这个对象放入到springmvc的容器中，容器是map，类似map.put("MyController",MyController对象)
 *
 * 2.请求的处理过程
 *
 *
 *
 *
 *
 */
@Controller
public class MyController {

    /**
     *逐个接收请求参数：
     * 要求：处理器（控制器）方法的形参名和请求中参数名一致。
     *       同名的请求参数赋值给同名的形参
     * 框架接收请求参数
     *      1. 使用request对象接收请求参数
     *          String  strName= request.getParameter("name");
     *          String strAge = request.getParameter("age");
     *      2.springmvc框架通过DispatcherServerlet 调用MyController的doSome()方法
     *          调用方法时，按照名称对应，把接收的参数赋值给形参
     *          doSome(strName,Integer.valueOf(strAge))
     *          框架会提供类型转换的功能，能把String转为int， long，float，double等类型
     *          
     *
     */
    @RequestMapping( value = "/some.do", method= RequestMethod.GET)
    public ModelAndView doSome(
            HttpServletRequest request,
            HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        //添加数据，框架在请求的最后把数据放入到request作用域
        //request.setAttribute("msg","welcome to springmvc web apps!")
        mv.addObject("msg","welcome to springmvc web apps!"+request.getParameter("name"));
        mv.addObject("fun","execute doSome function");


        //指定视图的完整路径
        //框架对视图执行forward操作， request.getRequestDispatcher("/show.jsp").forward(..)
        mv.setViewName("/show.jsp");

        return mv;

    }
}
