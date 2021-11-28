package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ControllerV3를 지원하는 어댑터 구현
 */
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {


    @Override
    public boolean supports(Object handler) {
         return (handler instanceof ControllerV3);
    }

    //handler를 ControllerV3 형식으로 리턴
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        // handler를 ControllerV3로 캐스팅
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);
        return mv;
    }


    private Map<String, String> createParamMap(HttpServletRequest request){
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
