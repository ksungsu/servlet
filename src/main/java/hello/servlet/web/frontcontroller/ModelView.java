package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ModelView {
    private String viewName;    //뷰의 논리 이름
    //        request.setAttribute("member", member);
    private Map<String, Object> model = new HashMap<>();    // 뷰 렌더링 객체 생성, 데이터 저장

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
