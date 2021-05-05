package com.josework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josework.entities.Student;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
 * 3.处理器方法的返回值表示请求的处理结果：
 *  1. ModelAndView: 有数据和视图，对视图执行Forward。
 *  2. String: 表示视图，可以逻辑名称，也可以完整视图路径。
 *  3. void: 不能表示数据，也不能表示视图。
 *      在处理ajax的时候，可以使用void返回值。通过HttpServletResponse输出数据，响应ajax请求。
 *      ajax请求服务器端返回的就是数据，和视图无关。
 *  4. Object: 例如String,Integer, Map, List,Student等等对象，
 *      对象有属性，属性就是数据。所以返回Object表示数据，和视图无关。
 *      可以使用对象表示的数据，响应ajax请求。
 *
 *      现在ajax，主要使用json数据格式。实现步骤：
 *      1.加入处理json工具库依赖，springmvc默认使用的是jackson
 *
 *      2.在springmvc配置文件中加入<mvc:annotation-driven> 注解驱动。
 *          json = om.writeValueAsString(student);
 *
 *      3. 在处理器方法上加入@ResponseBody注解
 *         response.setContentType("application/json;charset=utf-8");
 *         PrintWriter pw = response.getWriter();
 *         pw.print(json);
 *
 *      springmvc处理器方法返回Object，可以转为json输出到浏览器，响应ajax的内部原理。
 *          1.<mvc:annotation-driven>注解驱动。
 *              注解驱动实现的功能是 完成java对象到json，xml，text，二进制数据格式的转换。
 *
 *              <mvc:annotation-driven>在加入到springmvc配置文件后，会自动创建 HttpMessageConverter 接口的7个实现类对象，
 *              包括 MappingJackson2HttpMessageConverter （使用jackson工具库中的ObjectMapper实现java对象转为json对象字符串的
 *
 *
 *
 *
 *              HttpMessageConverter 接口，消息转换器。
 *              功能：定义了java转为json，xml等数据格式的方法。这个接口有很多的实现类。
 *              这些实现类完成 java对象到json，java对象到xml，java对象到二进制数据的转换
 *
 *          2. HttpMessageConverter
 *
 *          下面两个方法是控制器类把结果输出给浏览器时使用的：
 *              1）. canWrite 判断能否转换成相应的 mediaType 类型的数据
 *              boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
 *
 *              2）. write方法 把处理器返回值的对象，调用jackson中的ObjectMapper转为json字符串。
 *              void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
 * 			throws IOException, HttpMessageNotWritableException;
 *
 *
 * 	        3. @ResponseBody注解
 * 	        放在处理器的上面，通过 HttpServletResponse 输出数据，响应ajax请求
 *
 * 	            相当于：
 * 	                   PrintWriter pw = response.getWriter();
 *                     pw.print(json);
 *                     pw.flush();
 *                     pw.close();
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

    @RequestMapping( value = "/object.do", method= RequestMethod.GET)
    public ModelAndView doObject(
            Student student
    ){
        ModelAndView mv = new ModelAndView();

        //添加数据，框架在请求的最后把数据放入到request作用域
        //request.setAttribute("msg","welcome to springmvc web apps!")
        mv.addObject("myName",student.getName());
        mv.addObject("myAge",student.getAge());


        //指定视图的完整路径
        //框架对视图执行forward操作， request.getRequestDispatcher("/show.jsp").forward(..)
        mv.setViewName("/show.jsp");
        return mv;

    }

    //处理器方法返回void，响应ajax请求。
    //手工实现ajax，json数据：代码有重复的 1. java对象转json对象，2.通过HttpServletResponse 输出json数据。
    @RequestMapping( value = "/receiveAjax.do")
    public void doAjax(
            HttpServletResponse response,
            String name,
            Integer age
    ) throws IOException {
        //处理ajax请求，使用json做数据的格式
        Student student = new Student();
        //service调用完成了，使用Student对象表示处理结果
        student.setName(name);
        student.setAge(age);

        String json ="";
        if(student != null){
            ObjectMapper om = new ObjectMapper();
            json = om.writeValueAsString(student);
            System.out.println("Student对象转换的json字符串是："+json);

        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();

    }


    /**
     * 处理器方法返回Student对象，通过框架转为json对象，响应ajax请求。
     * @ResponseBody:
     *  作用：把处理器方法返回的对象转为json后，通过 HttpServletResponse 输出给浏览器。
     *  位置：方法的定义上面。 和其他注解没有先后顺序
     *
     * 返回对象框架的处理流程：
     *  1. 框架会把返回的Student类型，调用框架中的ArrayList<HttpMessageConverter>中每个类的canWrite()方法
     *      检查哪个HttpMessageConverter接口的实现类能处理Student类型的数据 -- MappingJackson2HttpMessageConverter -> true
     *
     *  2. 框架会调用实现类的write()方法， MappingJackon2HttpMessageConverter的write()方法
     *      把 Student类的对象转为json，调用Jackson的ObjectMapper实现转为json。
     *
     *  3. 框架会调用 @ResponseBody 把 2 的结果数据输出到浏览器，ajax请求处理完成。
     *
     *
     * @param response
     * @return
     */
    @RequestMapping("/returnStrudentJson.do")
    @ResponseBody
    public Student returnStuentJson(HttpServletResponse response){

        Student st = new Student();
        st.setName("李四");
        st.setAge(22);
        return  st;

    }

    /**
     * 处理器方法返回String，String可以表示数据，不是视图。
     *
     * 区分返回值String是数据，还是视图，看有没有 @ResponseBody 注解
     * 如果有 @ResponseBody 注解，返回String就是数据，反之就是视图。
     *
     *
     */
    @RequestMapping(value = "/doSomeString.do", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String doSomeString(){
        return ("\"xyz123/sijiwabc==\"");

    }



}
