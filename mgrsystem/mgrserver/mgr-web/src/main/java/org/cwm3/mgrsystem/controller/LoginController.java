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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

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


        public static void main(String[] args) throws ExecutionException, InterruptedException {
            CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<Object>() {
                @Override
                public Object get() {
                    System.out.println(Thread.currentThread().getName() + "\t completableFuture");
                    int i = 10 / 0;
                    return 1024;
                }
            }).whenComplete(new BiConsumer<Object, Throwable>() {
                @Override
                public void accept(Object o, Throwable throwable) {
                    System.out.println("-------o=" + o.toString());
                    System.out.println("-------throwable=" + throwable);
                }
            }).exceptionally(new Function<Throwable, Object>() {
                @Override
                public Object apply(Throwable throwable) {
                    System.out.println("throwable=" + throwable);
                    return 6666;
                }
            });
            System.out.println(future.get());
        }


}
