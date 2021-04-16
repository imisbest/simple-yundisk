package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.dao.UserDao;
import com.csw.simpleyundisk.entity.Black;
import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.TiaoZhuan;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.BlackService;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.UserService;
import com.csw.simpleyundisk.util.MD5Utils;
import com.csw.simpleyundisk.util.Number6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;
    @Autowired
    private BlackService blackService;
    @Autowired
    private DirService dirService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("sendm")
    @ResponseBody
    public Map<String,Object> sendMessage(String phone, HttpSession session) {
        //进入发送sendm方法了");
        //phone]" + phone);
        Map<String,Object> map = new HashMap<>();
        String yzm = Number6.getNum();
        //验证码】" + yzm);
        User user1 = userService.queryUserByPhone(phone);
        if (user1 == null) {
            try {
                //MessageSend.send(phone, yzm);
                //把验证码存入redis,设置五分钟超时失效
                stringRedisTemplate.opsForValue().set(phone, yzm, 5, TimeUnit.MINUTES);
                map.put("status", 200);
                map.put("message", "成功");
                session.setAttribute("message", "发送验证码成功");
            } catch (Exception e) {
                map.put("status", 500);
                map.put("message", "失败");
                session.setAttribute("message", "发送验证码失败");
                e.printStackTrace();
            }
        } else {
            //如果用户的名字不为空，但是手机号存在的花，说明已经存在此用户
            map.put("status", 500);
            map.put("message", "此手机号已经注册");
            session.setAttribute("message", "此手机号已经注册");
        }
        return map;
    }


    @RequestMapping("regist")
    public String regist(User user, String repwd, String captchaCode, HttpSession session) {
        //进入注册regist方法2");
        //user]" + user);
        User usersearch = userService.queryUserByPhone(user.getPhone());
        //userSearch]" + usersearch);

        String codeYzm = (String) session.getAttribute("securityCode");
        try {
            String codeSave = stringRedisTemplate.opsForValue().get(user.getPhone());
            if (usersearch != null) {
                session.setAttribute("message", "手机号被注册过，请登录");
                return "redirect:/jsp/front/regist.jsp";
            } else if (Objects.equals(codeSave, "")) {
                session.setAttribute("message", "手机号输入错误或者验证码过期，请检查之后重新输入");
                return "redirect:/jsp/front/regist.jsp";
            } else if (user.getName().equals("") || user.getEmail().equals("") || user.getPwd().equals("") ||
                    repwd.equals("") || user.getPhone().equals("") || user.getCode().equals("")) {
                session.setAttribute("message", "有空值，请检查之后重新输入");
                return "redirect:/jsp/front/regist.jsp";
            } else if (!Objects.equals(codeSave, user.getCode())) {
                session.setAttribute("message", "手机验证码输入不正确");
                return "redirect:/jsp/front/regist.jsp";
            } else if (!codeYzm.equalsIgnoreCase(captchaCode)) {
                session.setAttribute("message", "校验码输入不正确");
                return "redirect:/jsp/front/regist.jsp";
            } else if (!user.getPwd().equals(repwd)) {
                session.setAttribute("message", "两次密码输入的不正确");
                return "redirect:/jsp/front/regist.jsp";
            }
            user.setId(UUID.randomUUID().toString());
            String salt = MD5Utils.getSalt();
            user.setSalt(salt);
            String truepwd = MD5Utils.getPassword(user.getPwd()) + salt;
            user.setPwd(truepwd);
            user.setStatus("正常");
            user.setCreateDate(new Date());
            user.setLoginTime(null);
            user.setHideSalt(null);
            user.setHidePwd(null);
            //user赋值之后]" + user);
            userService.insertIntoUser(user);
            session.setAttribute("message", "注册成功");
            //往黑名单里添加次数
            Black black = new Black();
            black.setUid(user.getId());
            black.setCount(3);
            blackService.insertInto(black);

            //创建用户根目录
            String path = session.getServletContext().getRealPath("/userSave");

            Dir dir = new Dir();
            dir.setId(UUID.randomUUID().toString());
            dir.setName(user.getId());
            dir.setPath(path);
            dir.setCount(0);
            dir.setCreateTime(new Date());
            dir.setStatus(1);
            dir.setIsShare(0);
            dir.setIcon("文件夹");
            dir.setUser(user);
            //创建用户根目录dir]" + dir);
            dirService.addRootDir(dir);
            //本地创建
            File folder = new File(path + "\\" + user.getId());
            if (!folder.exists()) {
                folder.mkdirs();
            }
            session.setAttribute("message", "用户注册成功");
            return "redirect:/jsp/front/login.jsp";
        } catch (Exception e) {
            session.setAttribute("message", "有错误,请认真检查在提交");
        }

        return "redirect:/jsp/front/login.jsp";
    }


    @RequestMapping("login")
    @ResponseBody
    public String login(User user, HttpSession session, String captchaCode) {
        //user]" + user);
        User usersearch = userService.queryUserByPhone(user.getPhone());
        //usersearch ]" + usersearch);
        String codeYzm = (String) session.getAttribute("securityCode");
        //codeYzm]" + codeYzm);

        if (usersearch == null) {
            session.setAttribute("message", "手机号错误");
            return "手机号错误";
        } else if (!codeYzm.equalsIgnoreCase(captchaCode)) {
            session.setAttribute("message", "验证码错误");
            return "验证码输入错误，请检查后重新输入";
        }
        String truepwd = MD5Utils.getPassword(user.getPwd()) + usersearch.getSalt();
        //truepwd]" + truepwd);
        if (!usersearch.getPwd().equals(truepwd)) {
            session.setAttribute("message", "密码输入错误");
            return "密码输入错误";
        } else if (usersearch.getStatus().equals("冻结")) {
            session.setAttribute("message", "用户已经被冻结，请先解冻");
            return "用户已经被冻结，请解冻之后再次登录";
        } else {
            //usersearch】" + usersearch);
            Dir dir = dirService.selectByName(usersearch.getId());
            //dir】" + dir);
            session.setAttribute("did", dir.getId());
            session.setAttribute("user", usersearch);

             // 跳转list

            List<TiaoZhuan> tzList = new ArrayList<>();
            session.setAttribute("tzList", tzList);
            return null;
        }
    }


    @RequestMapping("logOut")
    public String logOut(HttpSession session) {
        //进到退出登录方法了");
        session.invalidate();
        return "redirect:/jsp/front/main/detail/mydoc_fu.jsp";
    }


    @RequestMapping("checksendm")
    @ResponseBody
    public Map<String,Object> checksendm(String phone, HttpSession session) {
        //进入sendm方法");
        //phone]" + phone);
        Map<String,Object> map = new HashMap<>();
        String yzm = Number6.getNum();
        //验证码】" + yzm);
        User user1 = userService.queryUserByPhone(phone);
        if (user1 != null) {
            try {
                //MessageSend.send(phone, yzm);
                //把验证码存入redis,设置五分钟超时失效
                stringRedisTemplate.opsForValue().set(phone, yzm, 5, TimeUnit.MINUTES);
                map.put("status", 200);
                map.put("message", "成功");
                session.setAttribute("message", "发送验证码成功");
            } catch (Exception e) {
                map.put("status", 500);
                map.put("message", "失败");
                e.printStackTrace();
            }
        } else {
            //如果用户的名字不为空，但是手机号存在的花，说明已经存在此用户
            map.put("status", 500);
            map.put("message", "此手机用户不存在");
            session.setAttribute("message", "此用户不存在");
        }
        return map;
    }


    @RequestMapping("findpwd")
    public String findpwd(User user, HttpSession session, String repwd, String captchaCode) {
        //进入找回密码方法");
        //user]" + user);
        String codeSave = stringRedisTemplate.opsForValue().get(user.getPhone());
        //codesave]" + codeSave);
        String codeYzm = (String) session.getAttribute("securityCode");
        //codeYzm]" + codeYzm);
        User userSearch = userService.queryUserByPhone(user.getPhone());
        //userSearch]" + userSearch);
        if (codeSave == null) {//说明手机号每填错
            session.setAttribute("message", "手机验证码过期");
            return "redirect:/jsp/front/findpwd.jsp";
        } else if (user.getPwd().equals("") ||
                repwd.equals("") || user.getPhone().equals("") || user.getCode().equals("") || captchaCode.equals("")) {
            session.setAttribute("message", "有空值，请检查之后重新输入");
            return "redirect:/jsp/front/findpwd.jsp";
        } else if (userSearch == null) {
            session.setAttribute("message", "不存在这个手机号，请检查之后再重新输入");
            return "redirect:/jsp/front/findpwd.jsp";
        } else if (!codeSave.equals(user.getCode())) {
            session.setAttribute("message", "手机验证码输入不正确");
            return "redirect:/jsp/front/findpwd.jsp";
        } else if (!user.getPwd().equals(repwd)) {
            session.setAttribute("message", "两次密码输入的不正确");
            return "redirect:/jsp/front/findpwd.jsp";
        } else if (!codeYzm.equalsIgnoreCase(captchaCode)) {
            session.setAttribute("message", "校验码输入不正确");
            return "redirect:/jsp/front/findpwd.jsp";
        }
        String truepwd = MD5Utils.getPassword(user.getPwd()) + userSearch.getSalt();
        //truepwd]" + truepwd);
        userSearch.setPwd(truepwd);
        userService.updatePwd(userSearch);
        session.setAttribute("message", "成功找回密码");
        return "redirect:/jsp/front/login.jsp";
    }


    @RequestMapping("unlock")
    public String unlock(User user, String checkbox, HttpSession session, String captchaCode) {
        //user]" + user);
        //checkbox]" + checkbox);
        String codeSave = stringRedisTemplate.opsForValue().get(user.getPhone());
        String codeYzm = (String) session.getAttribute("securityCode");
        //验证手机号
        User user1 = userDao.queryUserByPhone(user.getPhone());
        if (user1 == null) {
            session.setAttribute("message", "不存在这个手机号，请检查之后再重新输入");
            return "redirect:/jsp/front/unlock.jsp";
        }
        String truepwd = MD5Utils.getPassword(user.getPwd()) + user1.getSalt();
        user.setPwd(truepwd);
        //验证用户所有信息
        User usersearch = userService.findUnlock(user);
        if (codeSave == null) {//说明手机号每填错
            session.setAttribute("message", "手机验证码过期");
            return "redirect:/jsp/front/unlock.jsp";
        } else if (user.getPwd().equals("") || user.getPhone().equals("") ||
                user.getCode().equals("") || captchaCode.equals("") ||
                user.getEmail().equals("") || user.getName().equals("") || checkbox == null) {
            session.setAttribute("message", "有空值，请检查之后重新输入");
            return "redirect:/jsp/front/unlock.jsp";
        } else if (!codeSave.equals(user.getCode())) {
            session.setAttribute("message", "手机验证码输入不正确");
            return "redirect:/jsp/front/unlock.jsp";
        } else if (usersearch == null) {
            session.setAttribute("message", "用户信息填写错误");
            return "redirect:/jsp/front/unlock.jsp";
        } else if (!codeYzm.equalsIgnoreCase(captchaCode)) {
            session.setAttribute("message", "校验码输入不正确");
            return "redirect:/jsp/front/unlock.jsp";
        }
        Black black = blackService.queryByUid(usersearch.getId());
        //black]" + black);
        if (black == null) {
            session.setAttribute("message", "你所解冻的用户已被彻底清除");
            return "redirect:/jsp/front/unlock.jsp";
        } else if (black.getCount() == 0) {
            blackService.deleteUser(black.getUid());
            userService.deleteUser(black.getUid());
            session.setAttribute("message", "你的账户已经无法解冻");
            return "redirect:/jsp/front/unlock.jsp";
        }
        int count = (int) (black.getCount() - 1);
        black.setCount(count);
        blackService.updateCount(black);
        session.setAttribute("message", "你的账户已经成功解冻");
        //改变用户冻结的状态
        usersearch.setStatus("正常");
        userService.changeStatus(usersearch);
        return "redirect:/jsp/front/login.jsp";
    }

    @RequestMapping("getUser")
    @ResponseBody
    public String getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return null;
        } else {
            return user.getName();
        }
    }

    @RequestMapping("getMessage")
    @ResponseBody
    public String getMessage(HttpSession session) {
        return (String) session.getAttribute("message");
    }


    @RequestMapping("cleMessageFind")
    public String cleMessageFind(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/jsp/front/findpwd.jsp";
    }

    @RequestMapping("cleMessageRegist")
    public String cleMessageRegist(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/jsp/front/regist.jsp";
    }

    @RequestMapping("cleMessageLogin")
    public String cleMessageLogin(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/jsp/front/login.jsp";
    }

    @RequestMapping("cleMessageUnLock")
    public String cleMessageUnLock(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/jsp/front/unlock.jsp";
    }
}
