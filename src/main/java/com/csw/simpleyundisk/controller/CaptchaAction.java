package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.util.SecurityCode;
import com.csw.simpleyundisk.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Controller
@RequestMapping("captcha")
public class CaptchaAction {
    @RequestMapping("captcha")
    public String execute(HttpServletResponse response, HttpSession session) throws Exception {
        String securityCode = SecurityCode.getSecurityCode();
        session.setAttribute("securityCode", securityCode);
        BufferedImage image = SecurityImage.createImage(securityCode);
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
        return null;
       /*  <%----%>
    <img src="${path}/captcha/captcha" id="captchaImg"/>
    <div class="label-text">验证码：</div>
    <input type="text" name="captchaCode"><br/>
    <a href="javascript:void(0)"
        onclick="changeCaptcha()">看不清，换一张!~</a>&nbsp;<span
                style="color:red">${errorMsg }</span><br/>
    <script type="text/javascript">
                function changeCaptcha() {
            // 根据ID获取到验证码图片对象
            var captchaImg = document.getElementById('captchaImg');
            captchaImg.src = '${path}/captcha/captcha?'
                    + Math.random();
        }
    </script>
    <input type="submit" value="提交">
    <%--*/
    }
}