package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("session Id={}", session.getId()); // http 세션이 만들어준 세션id, Jsessionid임
        log.info("session getMaxInactiveInterval={}", session.getMaxInactiveInterval()); //세션 유효시간
        log.info("session getCreationTime={}", new Date(session.getCreationTime())); //세션 생성일자
        log.info("session getLastAccessedTime={}", new Date(session.getLastAccessedTime())); //세션 마지막 접근 일자
        log.info("session isNew={}", session.isNew()); //새로 생성한거냐?

        return "세션 출력";
    }
}
