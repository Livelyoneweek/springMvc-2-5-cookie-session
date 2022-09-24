package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    //ctrl + o 오버라이드 단축키
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // afterCompletion uuid 공유를 위해 위해 이런방식을 채택
        request.setAttribute(LOG_ID,uuid);

        //@RequestMapping: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler; //호출할 컨트롤러 메소드의 모든 정보가 포함되어 있다.
        }

        log.info("LoginInterceptor Request [{}][{}][{}]", uuid, requestURI, handler);
        return true; //true로 해야지 컨트롤러 호출이 진행됌
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("LoginInterceptor postHandle [{}]",modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute(LOG_ID);

        log.info("Response [{}][{}][{}]", uuid, requestURI, handler);

        if (ex != null) {
            log.info("afterCompletion error!!", ex); //오류는 {} 없어도 됌!!
        }
    }
}
