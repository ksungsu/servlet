package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    //    private Map<String, ControllerV3> controllerMap = new HashMap<>();
    //      Controller가 정해지지 않았으므로 Object로 설정
    private final Map<String, Object> handlerMappingMap = new HashMap<>();

   // 어댑터가 여러개 있고 그중에 하나를 찾아 써야하기 때문에 선언
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap(); //핸들러 매핑 초기화
        initHandlerAdapters();  // 어댑터 초기화
    }

    // handler 맵핑
    private void initHandlerMappingMap(){
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

    }

    // 핸들러 추가
    private void initHandlerAdapters(){
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    //map정보를 보고 해당하는 handler(컨트롤러)를 찾는 메서드

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //핸들러 요청
        Object handler = getHandler(request);
        if (handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //리스트에서 핸들러를 처리할 수 있는 핸들러 어댑터 조회
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //적합한 핸들러어댑터를 찾은뒤 mv반환
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();

        //논리 뷰 이름 조회해서 Resolver를 통해 물리 뷰로 변환
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handle adapter를 찾을 수 없습니다. handler = " + handler);
    }

    //핸들러 조회 및 요청
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    //논리 뷰 이름 -> 물리 뷰 이름으로 전환
    private MyView viewResolver(String viewName) {
      return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
