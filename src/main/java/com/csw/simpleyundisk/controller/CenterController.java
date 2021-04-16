package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.UserService;
import com.csw.simpleyundisk.util.MD5Utils;
import com.csw.simpleyundisk.util.Number6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("center")
public class CenterController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("changeUser")
    public String changeUser(HttpSession session, User user) {

        String codeSave = stringRedisTemplate.opsForValue().get(user.getPhone());
        try {
            if (Objects.equals(codeSave, "")) {
                session.setAttribute("message", "验证码过期，请检查之后重新输入");
                return "redirect:/jsp/front/main/detail/center.jsp";
            } else if (user.getName().equals("") ||
                    user.getEmail().equals("") ||
                    user.getPwd().equals("") ||
                    user.getPhone().equals("") ||
                    user.getCode().equals("") ||
                    user.getHidePwd() == null) {
                session.setAttribute("message", "有空值，请检查之后重新输入");
                return "redirect:/jsp/front/main/detail/center.jsp";
            } else if (!Objects.equals(codeSave, user.getCode())) {
                session.setAttribute("message", "手机验证码输入不正确");
                return "redirect:/jsp/front/main/detail/center.jsp";
            }
            User user0 = (User) session.getAttribute("user");
            //user0]" + user0);
            String salt = MD5Utils.getSalt();
            user0.setSalt(salt);
            String truepwd = MD5Utils.getPassword(user.getPwd()) + salt;
            user0.setPwd(truepwd);
            //
            String saltyc = MD5Utils.getSalt();
            user0.setHideSalt(saltyc);
            String truepwdyc = MD5Utils.getPassword(user.getHidePwd()) + saltyc;
            user0.setHidePwd(truepwdyc);
            //
            user0.setName(user.getName());
            user0.setEmail(user.getEmail());
            user0.setPhone(user.getPhone());
            user0.setCode(user.getCode());
            //user0赋值之后]" + user0);
            userService.updateUser(user0);
            session.setAttribute("message", "更新成功,请重新登录！！！");
            return "redirect:/user/logOut";
        } catch (Exception e) {
            session.setAttribute("message", "你还没有发送验证码");
            return "redirect:/jsp/front/main/detail/center.jsp";
        }
    }

    @RequestMapping("getMessage")
    @ResponseBody
    public String getMessage(HttpSession session) {
        return (String) session.getAttribute("message");
    }


    @RequestMapping("cleMessage")
    public String cleMessage(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/jsp/front/main/detail/center.jsp";
    }

    @RequestMapping("changeSendm")
    @ResponseBody
    public Map<String,Object> changeSendm(String phone) {
        //进入sendm方法
        //phone]" + phone);
        Map<String,Object> map = new HashMap<>();
        String yzm = Number6.getNum();
        //验证码】" + yzm);
        try {
            //MessageSend.send(phone, yzm);
            //把验证码存入redis,设置五分钟超时失效
            stringRedisTemplate.opsForValue().set(phone, yzm, 5, TimeUnit.MINUTES);
            map.put("status", 200);
            map.put("message", "成功");
        } catch (Exception e) {
            map.put("status", 500);
            map.put("message", "失败");
            e.printStackTrace();
        }
        return map;
    }
}
