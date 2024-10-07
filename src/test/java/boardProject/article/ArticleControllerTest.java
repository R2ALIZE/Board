package boardProject.article;

import boardProject.article.controller.ArticleController;
import boardProject.article.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class) // JUnit - Mockito 연동
public class ArticleControllerTest {


    @InjectMocks
    private ArticleController articleController;
    @Mock
    private ArticleService articleService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }



    @Test
    @DisplayName("Article Get 요청")
    void getArticleTest () throws Exception {

        //given

        //when
        //then

    }
}
