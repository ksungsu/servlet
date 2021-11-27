package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 구현체 인터페이스 생성, 서블릿 형식과 최대한 비슷하게 설정해서(다형성 + 일관성 충족)
 */

public interface ControllerV1 {

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
