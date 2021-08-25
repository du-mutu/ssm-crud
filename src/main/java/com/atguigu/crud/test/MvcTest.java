package com.atguigu.crud.test;

import com.atguigu.crud.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 *  * 使用Spring测试模块提供的测试请求功能，测试curd请求的正确性
 *  * Spring4测试的时候，需要servlet3.0的支持
 * @Author DW
 * @Date 2021/7/12 15:35
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
        "classpath:WEB-INF/dispatcherServlet-servlet.xml" })
public class MvcTest {
    // 传入Springmvc的ioc
    @Autowired
    WebApplicationContext context;
    // 虚拟mvc请求，获取到处理结果。
    MockMvc mockMvc;

    @Before
    public void initMokcMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        System.out.println("进来初始化流程"+mockMvc.toString());
    }


    @Test
    public void testpage(){
        try {
            //模拟请求拿到返回值
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").
                    param("pn", "5").param("name", "dw")).
                    andReturn();
            //请求成功以后，请求域中会有pageInfo；我们可以取出pageInfo进行验证
            MockHttpServletRequest request = result.getRequest();
            PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
            System.out.println("当前页码："+pi.getPageNum());
            System.out.println("总页码："+pi.getPages());
            System.out.println("总记录数："+pi.getTotal());
            System.out.println("在页面需要连续显示的页码");
            System.out.println("master添加23333");
            System.out.println("push test");
            int[] nums = pi.getNavigatepageNums();
            for (int i : nums) {
                System.out.print(" "+i);
            }

            //获取员工数据
            List<Employee> list = pi.getList();
            for (Employee employee : list) {
                System.out.println("ID："+employee.getEmpId()+"==>Name:"+employee.getEmpName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
