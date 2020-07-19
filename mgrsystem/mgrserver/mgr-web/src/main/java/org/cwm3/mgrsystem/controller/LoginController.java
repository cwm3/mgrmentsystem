package org.cwm3.mgrsystem.controller;

import org.cwm3.mgrsystem.common.annotation.Log;
import org.cwm3.mgrsystem.config.VerificationCode;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**

 * @作者 cwm3
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    @Log(value="获取在线用户信息",table="hr")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录!");
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}
