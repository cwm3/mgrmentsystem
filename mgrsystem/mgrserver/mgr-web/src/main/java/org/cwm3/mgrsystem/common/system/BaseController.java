package org.cwm3.mgrsystem.common.system;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.cwm3.mgrsystem.common.PageData;
import org.cwm3.mgrsystem.common.pager.PageBean;
import org.cwm3.mgrsystem.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/6/29 11:45
 */
public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);


    private static final long serialVersionUID = 6357869213649815390L;
//    private Subject getSubject() {
//        return SecurityUtils.getSubject();
//    }
//
//    protected User getCurrentUser() {
//        return (User) getSubject().getPrincipal();
//    }
//
//    protected Session getSession() {
//        return getSubject().getSession();
//    }

        /**
         * 得到PageData
         */
        public PageData getPageData() {
            return new PageData(this.getRequest());
        }

        /**
         * 得到ModelAndView
         */
        public ModelAndView getModelAndView() {
            return new ModelAndView();
        }

        /**
         * 得到request对象
         */
        public HttpServletRequest getRequest() {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            return request;
        }

        /**
         * 得到32位的uuid
         *
         * @return
         */
        public String get32UUID(){

            return UuidUtil.get32UUID();
        }

         /**
         * 得到分页列表的信息
         */
         public PageBean getPage(){
			return new PageBean();
         }

    public void startPage() {
//        int pageNum = false;
//        int pageSize = true;
        String currentIndexStr = this.getRequest().getParameter("currentIndex");
        String showCountStr = "";
        int pageNum;
        int pageSize;
        if (StringUtils.isBlank(currentIndexStr)) {
            currentIndexStr = this.getRequest().getParameter("startIndex");
            showCountStr = this.getRequest().getParameter("pageSize");
            pageNum = Integer.parseInt(currentIndexStr);
            pageSize = Integer.parseInt(showCountStr);
        } else {
            showCountStr = this.getRequest().getParameter("showCount");
            int currentIndex = Integer.parseInt(currentIndexStr);
            int showCount = Integer.parseInt(showCountStr);
            pageNum = currentIndex == 0 ? 1 : currentIndex / showCount + 1;
            pageSize = showCount == 0 ? 10 : showCount;
        }

        PageHelper.startPage(pageNum, pageSize);
    }


    public static void logBefore(Logger logger, String interfaceName) {
            logger.info("");
            logger.info("<-----------------start--------------------->");
            logger.info(interfaceName);
        }

        public static void logAfter(Logger logger) {
            logger.info("<-----------------end--------------------->");
            logger.info("");
        }


}