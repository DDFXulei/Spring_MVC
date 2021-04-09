package com.josework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
 *              2.在类的上面
 *           说明：使用RequestMapping修饰的方法叫做处理器方法或者控制器方法。
 *           使用@RequestMapping修饰的方法类似于Servlet中的doGet()和doPost()
 *
 *
 *  返回值 ModelAndView 表示本次请求的处理结果
 */
@Controller
public class MyController {


    @RequestMapping( value = "/some.do")
    public ModelAndView doSome(){
        ModelAndView mv = new ModelAndView();

        //添加数据，框架在请求的最后把数据放入到request作用域
        //request.setAttribute("msg","welcome to springmvc web apps!")
        mv.addObject("msg","welcome to springmvc web apps!");
        mv.addObject("fun","execute doSome function");


        //指定视图的完整路径
        //框架对视图执行forward操作， request.getRequestDispatcher("/show.jsp").forward(..)
        mv.setViewName("/show.jsp");

        return mv;

    }
}
