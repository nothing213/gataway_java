package com.listen;

import com.controller.ModelerController;
import lombok.extern.log4j.Log4j;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.FormServiceImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
public class Listener  implements TaskListener{

   @Resource
    private FormService formService;

    ModelerController modelerController = new ModelerController();



    public   void notify(DelegateTask delegateTask) {
//        System.out.println(delegateTask.getName() + "的监听器");

        String event = delegateTask.getEventName();

//        switch (event)
//        {
//            case "start":
//                System.out.println(delegateTask.getName() +" start....");
//            case "end" :
//                System.out.println(delegateTask.getName() +" end....");
//            case "delete" :
//                System.out.println(delegateTask.getName() +" delete....");
//            case "create" :
//
//                System.out.println(delegateTask.getName() +" create....");
//
//            case "complete" :
//            {
//                boolean flag = false;
//
//
//
////                TaskFormData userTaskFormData = formService.getTaskFormData(delegateTask.getId());
//
////                List<FormProperty> formProperties = userTaskFormData.getFormProperties();
////                int formSize = formProperties.size();
////                if (formSize == 0) {
////                    flag = modelerController.CompeleteApi(delegateTask.getName(), "");
////                }else {
////                    for (FormProperty formProperty : formProperties) {
////                        if (formProperty.getId() != null) {
////                            flag =modelerController.CompeleteApi(delegateTask.getName(), formProperty.getId());
////
////                        } else {
////                            System.out.println("bu参数：" + formProperty.getId());
////                            flag = modelerController.CompeleteApi(delegateTask.getName(), "");
////                        }
////                    }
////                }
//                if(flag)
//                {
//                    System.out.println(delegateTask.getName() +" complete...." );
//                }else{
//                    System.out.println(delegateTask.getName()+" "+ "not complete"+"抛出异常，停止运行");
//                }
//            }
//
//
//
//        }

    }
}
