package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author DW
 * @Date 2021/7/12 15:16
 * @Version 1.0
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService empService;


    /**
     * 导入jackson包。
     *
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        // 这不是一个分页查询
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = empService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 查询员工数据（分页查询）
     *
     * @return
     */
    //@RequestMapping("/emps")
    public String getEmps(Model model, @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                          @RequestParam(value = "name") String name) {
        try {
            System.out.println("姓名为" + name);
            // 这不是一个分页查询；
            // 引入PageHelper分页插件
            // 在查询之前只需要调用，传入页码，以及每页的大小
            List<Employee> empsList = empService.getAll();
            // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
            // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
            PageInfo page = new PageInfo(empsList, 5);
            model.addAttribute("pageInfo", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "list";
    }
}
